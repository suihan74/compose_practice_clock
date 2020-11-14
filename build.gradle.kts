import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.4.0"
    id("org.jetbrains.compose") version "0.1.0-build113"
}

repositories {
    jcenter()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            packageName = "SimpleClock"
            version = "0.1"
            description = "Compose Example App - Simple Clock"
            copyright = "© 2020 suihan. All rights reserved."

            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Exe,
                TargetFormat.Deb
            )
        }
    }
}
