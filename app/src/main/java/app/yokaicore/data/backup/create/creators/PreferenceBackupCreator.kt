package app.yokaicore.data.backup.create.creators

import app.yokaicore.data.backup.models.BackupPreference
import app.yokaicore.data.backup.models.BackupSourcePreferences
import app.yokaicore.data.backup.models.BooleanPreferenceValue
import app.yokaicore.data.backup.models.FloatPreferenceValue
import app.yokaicore.data.backup.models.IntPreferenceValue
import app.yokaicore.data.backup.models.LongPreferenceValue
import app.yokaicore.data.backup.models.StringPreferenceValue
import app.yokaicore.data.backup.models.StringSetPreferenceValue
import app.yokaicore.source.ConfigurableSource
import app.yokaicore.source.preferenceKey
import app.yokaicore.source.sourcePreferences
import yokaicore.core.preference.Preference
import yokaicore.core.preference.PreferenceStore
import yokaicore.domain.source.service.SourceManager
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class PreferenceBackupCreator(
    private val sourceManager: SourceManager = Injekt.get(),
    private val preferenceStore: PreferenceStore = Injekt.get(),
) {

    fun backupAppPreferences(includePrivatePreferences: Boolean): List<BackupPreference> {
        return preferenceStore.getAll().toBackupPreferences()
            .withPrivatePreferences(includePrivatePreferences)
    }

    fun backupSourcePreferences(includePrivatePreferences: Boolean): List<BackupSourcePreferences> {
        return sourceManager.getCatalogueSources()
            .filterIsInstance<ConfigurableSource>()
            .map {
                BackupSourcePreferences(
                    it.preferenceKey(),
                    it.sourcePreferences().all.toBackupPreferences()
                        .withPrivatePreferences(includePrivatePreferences),
                )
            }
            .filter { it.prefs.isNotEmpty() }
    }

    @Suppress("UNCHECKED_CAST")
    private fun Map<String, *>.toBackupPreferences(): List<BackupPreference> {
        return this
            .filterKeys { !Preference.isAppState(it) }
            .mapNotNull { (key, value) ->
                when (value) {
                    is Int -> BackupPreference(key, IntPreferenceValue(value))
                    is Long -> BackupPreference(key, LongPreferenceValue(value))
                    is Float -> BackupPreference(key, FloatPreferenceValue(value))
                    is String -> BackupPreference(key, StringPreferenceValue(value))
                    is Boolean -> BackupPreference(key, BooleanPreferenceValue(value))
                    is Set<*> -> (value as? Set<String>)?.let {
                        BackupPreference(key, StringSetPreferenceValue(it))
                    }
                    else -> null
                }
            }
    }

    private fun List<BackupPreference>.withPrivatePreferences(include: Boolean) =
        if (include) {
            this
        } else {
            this.filter { !Preference.isPrivate(it.key) }
        }
}
