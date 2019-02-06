package br.com.ibict.converter

import br.com.ibict.converter.Context
import org.openlca.core.database.IDatabase
import org.openlca.core.database.derby.DerbyDatabase
import org.openlca.io.maps.Maps
import org.openlca.jsonld.ZipStore
import org.openlca.jsonld.input.JsonImport
import java.io.File

internal class RefSystem private constructor(
        val name: String,
        private val dumpFolder: String) {

    fun newDB(): DerbyDatabase {
        val db = DerbyDatabase.restoreInMemory(dumpFolder)
        db.fileStorageLocation = Context.cache!!.tempDir()
        return db
    }

    companion object {


        fun initialize(folder: File): RefSystem {
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val dumpDir = File(folder, "dump")
            if (!dumpDir.exists()) {
                initDump(folder)
            }
            return RefSystem(folder.name, dumpDir.absolutePath)
        }

        private fun initDump(dir: File) {
            val memDB = DerbyDatabase.createInMemory()
            val dataFile = File(dir, "data.zip")
            if (dataFile.exists()) {
                importJson(dataFile, memDB)
            }
            val mappingDir = File(dir, "mappings")
            if (mappingDir.exists() && mappingDir.isDirectory) {
                importMappings(mappingDir, memDB)
            }
            val dumpDir = File(dir, "dump")
            memDB.dump(dumpDir.absolutePath)
            memDB.close()
        }

        private fun importJson(dataFile: File, db: IDatabase) {
            try {
                val store = ZipStore.open(dataFile)
                val imp = JsonImport(store, db)
                imp.run()
            } catch (e: Exception) {
                throw Exception("Failed to import reference data from $dataFile", e)
            }
        }

        private fun importMappings(mappingDir: File, db: IDatabase) {
            mappingDir.listFiles().forEach { f ->
                if (!f.isFile)
                    return@forEach
                val name = f.name
                try {
                    f.inputStream().use { stream ->
                        Maps.store(f.absolutePath, stream, db)
                    }
                } catch (e: Exception) {
                    throw Exception("failed to read mapping file $f", e)
                }
            }
        }
    }
}