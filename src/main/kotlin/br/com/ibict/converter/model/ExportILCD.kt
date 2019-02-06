package br.com.ibict.converter.model

import org.openlca.core.database.IDatabase
import org.openlca.core.database.ProcessDao
import org.openlca.io.ilcd.ILCDExport
import org.openlca.io.ilcd.output.ExportConfig
import java.io.File

internal class ExportILCD : Export {

    override val format = Format.ILCD

    override fun doIt(db: IDatabase, output: File): File {
        val file = output
        val conf = ExportConfig(db, file)
        val exp = ILCDExport(conf)
        ProcessDao(db).all.forEach { exp.export(it) }
        exp.close()
        return file
    }
}
