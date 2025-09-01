import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.nativeblocks)
}

val nativeblocksProps = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "nativeblocks.properties")))
}

val appId = "io.nativeblocks.template.android"

android {
    namespace = appId
    compileSdk = 35

    defaultConfig {
        applicationId = appId
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NATIVEBLOCKS_API_URL", "\"${nativeblocksProps["apiUrl"] as String}\"")
        buildConfigField("String", "NATIVEBLOCKS_API_KEY", "\"${nativeblocksProps["apiKey"] as String}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

nativeblocks {
    endpoint = nativeblocksProps["pluginUrl"] as String
    authToken = nativeblocksProps["authToken"] as String
    organizationId = nativeblocksProps["organizationId"] as String
    basePackageName = appId
    moduleName = "MyApp"
}

ksp {
    arg("basePackageName", appId)
    arg("moduleName", "MyApp")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.navigation)

    implementation(libs.nativeblocks.android)
    implementation(libs.nativeblocks.foundation.android)
    implementation(libs.nativeblocks.wandkit.android)
    implementation(libs.nativeblocks.compiler.android)
    ksp(libs.nativeblocks.compiler.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}