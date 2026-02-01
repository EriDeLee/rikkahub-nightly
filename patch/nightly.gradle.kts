// patch/nightly.gradle.kts

val nightlyVersionCode = (System.currentTimeMillis() / 60000L).toInt()
val nightlyVersionName = "nightly-${nightlyVersionCode}"

extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
    defaultConfig {
        versionCode = nightlyVersionCode
        versionName = nightlyVersionName
    }

    buildTypes.configureEach {
        if (name == "release") {
            applicationIdSuffix = ".nightly"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

extensions.configure<com.android.build.api.variant.AndroidComponentsExtension<*, *>> {
    onVariants(selector().withBuildType("release")) { variant ->
        variant.outputs.forEach { output ->
            (output as? com.android.build.gradle.internal.api.ApkVariantOutputImpl)?.outputFileName = "rikkahub-nightly-${nightlyVersionCode}.apk"
        }
    }
}
