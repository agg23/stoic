import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.squareup.stoic.android.sdk"
  compileSdk = (extra["stoic.android_compile_sdk"] as String).toInt()

  defaultConfig {
    minSdk = (extra["stoic.android_min_sdk"] as String).toInt()
    targetSdk = (extra["stoic.android_target_sdk"] as String).toInt()

    testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    val jvmTarget = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    sourceCompatibility = jvmTarget
    targetCompatibility = jvmTarget
  }
  kotlinOptions {
    jvmTarget = libs.versions.jvmTarget.get()
  }

  packaging {
    resources {
      excludes += "META-INF/DEPENDENCIES"
    }
  }

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  implementation(project(":common"))
  implementation(project(":android:plugin-sdk"))
  implementation(project(":android:server:core"))
  implementation("com.android.support:appcompat-v7:28.0.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("com.android.support.test:runner:1.0.2")
  androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}
