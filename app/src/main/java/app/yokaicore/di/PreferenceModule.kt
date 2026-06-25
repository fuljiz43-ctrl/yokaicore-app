package app.yokaicore.di

import android.app.Application
import app.yokaicore.domain.base.BasePreferences
import app.yokaicore.domain.source.service.SourcePreferences
import app.yokaicore.domain.track.service.TrackPreferences
import app.yokaicore.domain.ui.UiPreferences
import app.yokaicore.core.security.SecurityPreferences
import app.yokaicore.network.NetworkPreferences
import app.yokaicore.ui.reader.setting.ReaderPreferences
import app.yokaicore.util.system.isDevFlavor
import yokaicore.core.preference.AndroidPreferenceStore
import yokaicore.core.preference.PreferenceStore
import yokaicore.core.storage.AndroidStorageFolderProvider
import yokaicore.domain.backup.service.BackupPreferences
import yokaicore.domain.download.service.DownloadPreferences
import yokaicore.domain.library.service.LibraryPreferences
import yokaicore.domain.storage.service.StoragePreferences
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addSingletonFactory
import uy.kohesive.injekt.api.get

class PreferenceModule(val app: Application) : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory<PreferenceStore> {
            AndroidPreferenceStore(app)
        }
        addSingletonFactory {
            NetworkPreferences(
                preferenceStore = get(),
                verboseLogging = isDevFlavor,
            )
        }
        addSingletonFactory {
            SourcePreferences(get())
        }
        addSingletonFactory {
            SecurityPreferences(get())
        }
        addSingletonFactory {
            LibraryPreferences(get())
        }
        addSingletonFactory {
            ReaderPreferences(get())
        }
        addSingletonFactory {
            TrackPreferences(get())
        }
        addSingletonFactory {
            DownloadPreferences(get())
        }
        addSingletonFactory {
            BackupPreferences(get())
        }
        addSingletonFactory {
            StoragePreferences(
                folderProvider = get<AndroidStorageFolderProvider>(),
                preferenceStore = get(),
            )
        }
        addSingletonFactory {
            UiPreferences(get())
        }
        addSingletonFactory {
            BasePreferences(app, get())
        }
    }
}
