package yokaicore.domain.storage.service

import yokaicore.core.preference.Preference
import yokaicore.core.preference.PreferenceStore
import yokaicore.core.storage.FolderProvider

class StoragePreferences(
    private val folderProvider: FolderProvider,
    private val preferenceStore: PreferenceStore,
) {

    fun baseStorageDirectory() = preferenceStore.getString(Preference.appStateKey("storage_dir"), folderProvider.path())
}
