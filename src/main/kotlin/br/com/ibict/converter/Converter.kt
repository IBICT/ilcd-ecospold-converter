package br.com.ibict.converter

import br.com.ibict.converter.RefSystem
import br.com.ibict.converter.Context
import br.com.ibict.converter.model.ConversionSetup
import br.com.ibict.converter.model.Export
import br.com.ibict.converter.model.ExportILCD
import br.com.ibict.converter.model.Format
import br.com.ibict.converter.model.Import
import br.com.ibict.converter.model.ImportEcoSpold2
import br.com.ibict.converter.model.Cache
import java.io.File
import org.openlca.core.database.IDatabase
import org.openlca.util.Strings

class Converter(workspacePath: String = "workspace") {

    init {
        Context.initWorkspace(workspacePath)
    }


    fun convert(spold: File, ilcd: File): File {
        val info = ConversionSetup("es2", "EcoSpold 2", "ILCD")
        val imp = getImport(info)
        if (imp == null) {
            val msg = "unsupported source format: ${info.sourceFormat}"
            throw Exception(msg)
        }
        val exp = getExport(info)
        if (exp == null) {
            val msg = "unsupported target format: ${info.targetFormat}"
            throw Exception(msg)
        }
        return doIt(info, imp, exp, spold, ilcd)
    }

    private fun doIt(setup: ConversionSetup, imp: Import, exp: Export, spold: File, ilcd: File): File {
        var db: IDatabase? = null
        try {
            val refSystem = Context.getRefSystem(setup.refSystem)
            db = refSystem.newDB()
            imp.doIt(setup, db, spold)
            val zipFile = exp.doIt(db,ilcd)
            return zipFile
        } catch (e: Exception) {
            e.printStackTrace()
            val msg = "Conversion failed: ${e.message}"
            throw Exception(msg)
        } finally {
            db?.let {
                db!!.close()
                db!!.fileStorageLocation.deleteRecursively()
            }
        }
    }

    private fun getImport(setup: ConversionSetup): Import? {
        val format = Format.get(setup.sourceFormat)
        return when(format) {
            Format.ECOSPOLD_2 -> ImportEcoSpold2()
            else -> null
        }
    }

    private fun getExport(setup: ConversionSetup): Export? {
        val format = Format.get(setup.targetFormat)
        return when(format) {
            Format.ILCD -> ExportILCD()
            else -> null
        }
    }

}
