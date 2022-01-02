package com.rodrigmatrix.packagetracker.buildSrc

object Libs {

    private const val composeVersion = "1.1.0-beta01"

    object Kotlin {
        private const val version = "1.5.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Firebase {
        const val remoteConfig = "com.google.firebase:firebase-config-ktx"
        const val cloudMessaging = "com.google.firebase:firebase-messaging-ktx"
        const val inAppMessaging = "com.google.firebase:firebase-inappmessaging-display"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val bom = "com.google.firebase:firebase-bom:29.0.3"
    }

    object Accompanist {
        const val version = "0.16.0"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:$version"
    }

    object Coroutines {
        private const val version = "1.5.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val workManager = "androidx.work:work-runtime-ktx:2.7.0"

        object Compose {

            const val foundation = "androidx.compose.foundation:foundation:$composeVersion"
            const val layout = "androidx.compose.foundation:foundation-layout:$composeVersion"
            const val ui = "androidx.compose.ui:ui:$composeVersion"
            const val uiUtil = "androidx.compose.ui:ui-util:$composeVersion"
            const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
            const val material3 = "androidx.compose.material3:material3:1.0.0-alpha01"
            const val material = "androidx.compose.material:material:$composeVersion"
            const val animation = "androidx.compose.animation:animation:$composeVersion"
            const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$composeVersion"
            const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.17.0"
            const val themeAdapter = "com.google.android.material:compose-theme-adapter:1.0.3"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        }

        object Lifecycle {
            const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
        }

        object Navigation {
            const val navigationCompose = "androidx.navigation:navigation-compose:2.4.0-alpha06"
            const val composeNavAccompanist = "com.google.accompanist:accompanist-navigation-animation:0.16.1"
        }

        object ConstraintLayout {
            const val constraintLayoutCompose =
                "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"
        }

        object Room {
            private const val version = "2.3.0"

            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }
    }

    object Material {
        const val material = "com.google.android.material:material:1.6.0-alpha01"
    }

    const val lottie = "com.airbnb.android:lottie-compose:4.1.0"

    object Koin {
        private const val version = "3.1.2"

        const val android = "io.insert-koin:koin-android:$version"
        const val koinWorkManager = "io.insert-koin:koin-android:$version"
        const val androidCompat = "io.insert-koin:koin-android-compat:$version"
        const val compose = "io.insert-koin:koin-androidx-compose:$version"
        const val test = "io.insert-koin:koin-test-junit4:$version"
    }

    object Network {
        private const val retrofitVersion = "2.8.1"
        private const val okHttpVersion = "4.9.1"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    }

    object Serialization {
        private const val gsonVersion = "2.8.6"

        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Test {
        private const val version = "1.4.0"
        private const val junitVersion = "4.13"
        private const val mockkVersion = "1.11.0"

        const val junit = "junit:junit:$junitVersion"
        const val core = "androidx.arch.core:core-testing:2.1.0"
        const val runner = "androidx.test:runner:$version"
        const val rules = "androidx.test:rules:$version"
        const val androidJunit = "androidx.test.ext:junit:1.1.3"

        const val mockk = "io.mockk:mockk:$mockkVersion"

        object Ext {
            private const val version = "1.1.2"
            const val junit = "androidx.test.ext:junit-ktx:$version"
        }
        const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        const val composeTest = "androidx.compose.ui:ui-test-junit4:$composeVersion"
        const val debugComposeTest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
    }

}