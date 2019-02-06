package br.com.ibict.converter.model

import org.openlca.core.database.IDatabase
import org.openlca.io.ecospold2.input.EcoSpold2Import
import org.openlca.io.ecospold2.input.ImportConfig
import java.net.URL
import java.io.File

internal class ImportEcoSpold2 : Import {


    override fun doIt(setup: ConversionSetup, db: IDatabase, input: File) {
        val config = ImportConfig(db)
        config.flowMap = setup.flowMap()
        val imp = EcoSpold2Import(config)
        imp.setFiles(arrayOf(input))
        imp.run()
    }
}
