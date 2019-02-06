package br.com.ibict.converter.model

import org.openlca.core.database.IDatabase
import org.openlca.core.model.Process
import java.io.File

/**
 * Imports the data as defined in a conversion setup into the given database.
 */
internal interface Import {

    fun doIt(setup: ConversionSetup, db: IDatabase, input: File)

}
