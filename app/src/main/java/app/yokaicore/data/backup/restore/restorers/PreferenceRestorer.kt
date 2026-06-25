package app.yokaicore.data.backup.restore.restorers

import android.content.Context
import app.yokaicore.data.backup.create.BackupCreateJob
import app.yokaicore.data.backup.models.BackupPreference
import app.yokaicore.data.backup.models.BackupSourcePreferences
import app.yokaicore.data.backup.models.BooleanPreferenceValue
import app.yokaicore.data.backup.models.FloatPreferenceValue
import app.yokaicore.data.backup.models.IntPreferenceValue
import app.yokaicore.data.backup.models.LongPreferenceValue
import app.yokaicore.data.backup.models.StringPreferenceValue
import app.yokaicore.data.backup.models.StringSetPreferenceValue
import app.yokaicore.data.library.LibraryUpdateJob
import app.yokaicore.source.sourcePreferences
import yokaicore.core.preference.AndroidPreferenceStore
import yokaicore.core.preference.PreferenceStore
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class PreferenceRestorer(
    private val context: Context,
    private val preferenceStore: PreferenceStore = Injekt.get(),
) {

    fun restoreAppPreferences(preferences: List<BackupPreference>) {
        restorePreferences(preferences, preferenceStore)

        LibraryUpdateJob.setupTask(context)
        BackupCreateJob.setupTask(context)
    }

    fun restoreSourcePreferences(preferences: List<BackupSourcePreferences>) {
        preferences.forEach {
            val sourcePrefs = AndroidPreferenceStore(context, sourcePreferences(it.sourceKey))
            restorePreferences(it.prefs, sourcePrefs)
        }
    }

    private fun restorePreferences(
        toRestore: List<BackupPreference>,
        preferenceStore: PreferenceStore,
    ) {
        val prefs = preferenceStore.getAll()
        toRestore.forEach { (key, value) ->
            when (value) {
                is IntPreferenceValue -> {
                    if (prefs[key] is Int?) {
                        preferenceStore.getInt(key).set(value.value)
                    }
                }
                is LongPreferenceValue -> {
                    if (prefs[key] is Long?) {
                        preferenceStore.getLong(key).set(value.value)
                    }
                }
                is FloatPreferenceValue -> {
                    if (prefs[key] is Float?) {
                        preferenceStore.getFloat(key).set(value.value)
                    }
                }
                is StringPreferenceValue -> {
                    if (prefs[key] is String?) {
                        preferenceStore.getString(key).set(value.value)
                    }
                }
                is BooleanPreferenceValue -> {
                    if (prefs[key] is Boolean?) {
                        preferenceStore.getBoolean(key).set(value.value)
                    }
                }
                is StringSetPreferenceValue -> {
                    if (prefs[key] is Set<*>?) {
                        preferenceStore.getStringSet(key).set(value.value)
                    }
                }
            }
        }
    }
}
