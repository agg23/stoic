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

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.serialization.json)
    compileOnly(files("$androidHome/platforms/android-$androidCompileSdk/android.jar"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

artifacts {
    add("archives", tasks["sourcesJar"])
}
