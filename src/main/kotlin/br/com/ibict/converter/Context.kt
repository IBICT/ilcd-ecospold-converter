package br.com.ibict.converter

import br.com.ibict.converter.RefSystem
import br.com.ibict.converter.model.ConversionSetup
import br.com.ibict.converter.model.Cache
import java.io.File
import org.openlca.util.Strings

internal object Context {
    var cache: Cache? = null
    var refSystems = mutableListOf<RefSystem>()
    var defaultRefSystem: RefSystem? = null
    var initialized = false

    fun getRefSystem(name: String): RefSystem {
        if(!initialized) throw Exception("Converter: context not initialized");
        refSystems.forEach { rs ->
            if (Strings.nullOrEqual(name, rs.name))
                return rs
        }
        return defaultRefSystem!!
    }


    fun initWorkspace(path: String) {
        if(initialized) return;
        initialized = true;
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val refDir = File(dir, "refsystems")
        if (!refDir.exists()) {
            refDir.mkdirs()
        }
        refDir.listFiles().forEach { f ->
            val rs =RefSystem.initialize(f)
            refSystems.add(rs)
            if (Strings.nullOrEqual(rs.name, "default")) {
                defaultRefSystem = rs
            }
        }
        if (defaultRefSystem == null) {
            val defRefDir = File(refDir, "default")
            defaultRefSystem = RefSystem.initialize(defRefDir)
            refSystems.add(defaultRefSystem!!)
        }
        val cacheDir = File(dir, "cache")
        cache = Cache(cacheDir)
    }
}