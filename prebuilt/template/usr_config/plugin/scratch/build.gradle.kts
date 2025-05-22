import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"  // Serialization plugin
}

repositories {
    mavenCentral()
}

val androidSdkPath: String? = System.getenv("ANDROID_HOME")
dependencies {
    if (androidSdkPath == null) {
        throw GradleException("ANDROID_HOME environment variable is not set.")
    }
    compileOnly(files("$androidSdkPath/platforms/android-34/android.jar"))
    compileOnly(files("${rootProject.projectDir}/lib/stoic-android-plugin-sdk.jar"))

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}

val jvmVersion = "11"

java {
    sourceCompatibility = JavaVersion.toVersion(jvmVersion)
    targetCompatibility = JavaVersion.toVersion(jvmVersion)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = jvmVersion
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
