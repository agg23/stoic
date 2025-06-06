import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    mavenCentral()
}

val androidHome = providers.environmentVariable("ANDROID_HOME").orNull
    ?: throw GradleException("ANDROID_HOME is not set")
val androidCompileSdk = extra["stoic.android_compile_sdk"] as String

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":android:plugin-sdk"))
    implementation(project(":common"))
    implementation(libs.kotlinx.serialization.json)
    compileOnly(files("$androidHome/platforms/android-$androidCompileSdk/android.jar"))
}
