package dev.shaper.akdymall.processor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ExposedMappingProcessorProvider : SymbolProcessorProvider {
    override fun create(env: SymbolProcessorEnvironment) =
        ExposedMappingProcessor(env.codeGenerator, env.logger)
}