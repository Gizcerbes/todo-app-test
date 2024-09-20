import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
//    alias(libs.plugins.jetbrainsCompose)
//    alias(libs.plugins.compose.compiler)
}

kotlin{
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.sqlite)
           // implementation("androidx.room:room-ktx:2.7.0-alpha07")

            implementation(project(":core"))

        }
    }
}

dependencies {
    implementation(libs.androidx.sqlite.ktx)
    // Android
    add("kspAndroid", libs.androidx.room.compiler)
    // JVM (Desktop)
    add("kspJvm", libs.androidx.room.compiler)
    // Linux
//    add("kspLinuxX64", libs.androidx.room.compiler)
//    add("kspLinuxArm64", libs.androidx.room.compiler)
    // Mac
//    add("kspMacosX64", libs.androidx.room.compiler)
//    add("kspMacosArm64", libs.androidx.room.compiler)
    // iOS
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}




room {
    schemaDirectory("$projectDir/schemas")
}


android {
    namespace = "com.uogames.salesautomators.test.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    buildTypes {
        debug {  }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}