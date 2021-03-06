import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

val keyProperties = rootProject.file("key.properties")
val props = Properties()
if (keyProperties.exists()) {
    props.load(keyProperties.inputStream())
} else {
    props["TEST_PHPSESSID"] = "none"
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.seiko.lightnovel"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "TEST_PHPSESSID", "\"" + props["TEST_PHPSESSID"] + "\"")
        }
        getByName("debug") {
            buildConfigField("String", "TEST_PHPSESSID", "\"" + props["TEST_PHPSESSID"] + "\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Material3
    implementation("com.google.android.material:material:1.6.0")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-common:2.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")

    // Navigator
    implementation("androidx.navigation:navigation-common-ktx:2.4.2")
    implementation("androidx.navigation:navigation-runtime-ktx:2.4.2")

    // Di
    implementation("io.insert-koin:koin-android:3.1.5")

    // Http
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jsoup:jsoup:1.14.3")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")

    // Pref
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Image
    implementation("io.coil-kt:coil:2.0.0-rc03")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    testImplementation(kotlin("test"))
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

// filter some useless library
configurations.all {
    exclude(group = "androidx.lifecycle", module = "lifecycle-livedata")
    exclude(group = "androidx.lifecycle", module = "lifecycle-livedata-ktx")
    exclude(group = "androidx.lifecycle", module = "lifecycle-livedata-core")
    exclude(group = "androidx.lifecycle", module = "lifecycle-livedata-core-ktx")
    exclude(group = "androidx.fragment", module = "fragment")
    exclude(group = "androidx.viewpager2", module = "viewpager2")
    exclude(group = "androidx.localbroadcastmanager", module = "localbroadcastmanager")
}
