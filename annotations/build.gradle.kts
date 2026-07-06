plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(21)   // ← 세 모듈 전부 같은 숫자로
}