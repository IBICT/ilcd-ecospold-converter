package br.com.ibict.converter.model

import org.openlca.core.database.IDatabase
import java.io.File

/**
 * An export writes the processes of a databases into a target format. As
 * we work with an in-memory database for each conversion, we just export
 * all processes from this database.
 */
internal interface Export {

    val format: Format

    fun doIt(db: IDatabase, output: File): File
}
