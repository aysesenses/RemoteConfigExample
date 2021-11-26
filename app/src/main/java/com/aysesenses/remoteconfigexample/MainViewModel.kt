package com.aysesenses.remoteconfigexample

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainViewModel : ViewModel() {

    private val TAG = "RemoteConfigUtils"

    private val BUTTON_TEXT = "button_text"
    private val BUTTON_COLOR = "button_color"

    private val DEFAULTS: HashMap<String, Any> =
        hashMapOf(
            BUTTON_TEXT to "Hello Word!",
            BUTTON_COLOR to "#FF03DAC5"
        )

    private var remoteConfig: FirebaseRemoteConfig

    init {
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
                        Log.d(TAG, "Fetch Failed")
                    }
                }

            }

        return remoteConfig
    }

    fun getButtonText(): String = remoteConfig.getString(BUTTON_TEXT)

    fun getButtonColor(): String = remoteConfig.getString(BUTTON_COLOR)

}

