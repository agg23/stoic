import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    mavenCentral()
}

val generatedStoicVersionDir = rootProject.extra["stoicGeneratedSourceDir"] as Provider<*>
val generateStoicVersionTask = rootProject.extra["generateStoicVersion"] as TaskProvider<*>
sourceSets["main"].java.srcDir(generatedStoicVersionDir)
tasks.named("compileKotlin").configure { dependsOn(generateStoicVersionTask) }

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.serialization.json)
    implementation(project(":common"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes("Main-Class" to "com.squareup.stoic.host.main.HostMainKt")
    }
    // To include all dependencies in the JAR file, uncomment the following lines:
    from({
        configurations.runtimeClasspath.get().filter { it.exists() }.map { if (it.isDirectory) it else zipTree(it) }
    })
}
