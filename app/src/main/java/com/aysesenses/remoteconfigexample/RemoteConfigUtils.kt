package com.aysesenses.remoteconfigexample

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigUtils {

    private const val TAG = "RemoteConfigUtils"

    private const val BUTTON_TEXT = "button_text"
    private const val BUTTON_COLOR = "button_color"

   private lateinit var remoteConfig: FirebaseRemoteConfig

    private val DEFAULTS: HashMap<String, Any> =
        hashMapOf(
            BUTTON_TEXT to "Hello Word!",
            BUTTON_COLOR to "#FF03DAC5"
        )


    fun init() {
        remoteConfig = getFirebaseRemoteConfig()
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(DEFAULTS)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> {
                        val updated = task.result
                        Log.d(TAG, "Config params: $updated")

                        val colorName: String = remoteConfig.getString(BUTTON_COLOR)
                        Log.d(TAG, "colorName: $colorName")
                    }
                    else -> {
                        Log.d(TAG, "Fetch failed")
                    }
                }

            }

        return remoteConfig
    }

    fun getNextButtonText(): String = remoteConfig.getString(BUTTON_TEXT)

    fun getNextButtonColor(): String = remoteConfig.getString(BUTTON_COLOR)

}
