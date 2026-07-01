package dev.shaper.akdymall.processor

import com.google.devtools.ksp.symbol.KSValueParameter

fun KSValueParameter.hasAnn(name: String) =
    annotations.any { it.shortName.asString() == name }

fun KSValueParameter.annArg(annName: String, argName: String): Any? =
    annotations.firstOrNull { it.shortName.asString() == annName }
        ?.arguments?.firstOrNull { it.name?.asString() == argName }?.value