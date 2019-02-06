package br.com.ibict.converter.model

import java.io.File
import org.openlca.io.maps.FlowMap
import org.openlca.io.maps.FlowMapEntry

internal class ConversionSetup(ref: String, source : String, target : String, flow: List<FlowMapEntry>? = null) {
    var refSystem = ref
    var sourceFormat = source
    var targetFormat = target
    var flowMapping: List<FlowMapEntry>? = flow

    fun flowMap(): FlowMap? {
        if (flowMapping == null)
            return null
        return FlowMap(flowMapping)
    }
}