package rew.lightgames.sudoku2

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class SudokuApplication : Application(), LifecycleObserver {

    companion object {
        private const val PREF_NAME = "my_preferences"
        private const val PREF_MUSIC = "music"
        private const val PREF_SOUND_EFFECTS = "sound_effects"
        private const val PREF_HAS_BEEN_OPENED_BEFORE = "hasBeenOpenedBefore"
    }

    private val sharedPreferences by lazy { getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }

    private val appLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            handleMusicService()
        }

        override fun onStop(owner: LifecycleOwner) {
            stopService(Intent(this@SudokuApplication, MusicService::class.java))
        }
    }

    private val preferenceListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == PREF_MUSIC) {
            handleMusicService()
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupDefaultPreferences()
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceListener)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }

    override fun onTerminate() {
        super.onTerminate()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceListener)
    }

    private fun setupDefaultPreferences() {
        val hasBeenOpenedBefore = sharedPreferences.getBoolean(PREF_HAS_BEEN_OPENED_BEFORE, false)

        if (!hasBeenOpenedBefore) {
            sharedPreferences.edit().apply {
                putBoolean(PREF_MUSIC, true)
                putBoolean(PREF_SOUND_EFFECTS, true)
                putBoolean(PREF_HAS_BEEN_OPENED_BEFORE, true)
                apply()
            }
        }
    }

    private fun handleMusicService() {
        val musicOn = sharedPreferences.getBoolean(PREF_MUSIC, true)
        val intent = Intent(this@SudokuApplication, MusicService::class.java)

        if (musicOn) {
            startService(intent)
        } else {
            stopService(intent)
        }
    }
}
