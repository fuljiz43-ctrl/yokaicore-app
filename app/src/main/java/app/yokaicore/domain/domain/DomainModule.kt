package app.yokaicore.domain

import app.yokaicore.domain.chapter.interactor.GetAvailableScanlators
import app.yokaicore.domain.chapter.interactor.SetReadStatus
import app.yokaicore.domain.chapter.interactor.SyncChaptersWithSource
import app.yokaicore.domain.download.interactor.DeleteDownload
import app.yokaicore.domain.extension.interactor.CreateExtensionRepo
import app.yokaicore.domain.extension.interactor.DeleteExtensionRepo
import app.yokaicore.domain.extension.interactor.GetExtensionLanguages
import app.yokaicore.domain.extension.interactor.GetExtensionRepos
import app.yokaicore.domain.extension.interactor.GetExtensionSources
import app.yokaicore.domain.extension.interactor.GetExtensionsByType
import app.yokaicore.domain.extension.interactor.TrustExtension
import app.yokaicore.domain.manga.interactor.GetExcludedScanlators
import app.yokaicore.domain.manga.interactor.SetExcludedScanlators
import app.yokaicore.domain.manga.interactor.SetMangaViewerFlags
import app.yokaicore.domain.manga.interactor.UpdateManga
import app.yokaicore.domain.source.interactor.GetEnabledSources
import app.yokaicore.domain.source.interactor.GetLanguagesWithSources
import app.yokaicore.domain.source.interactor.GetSourcesWithFavoriteCount
import app.yokaicore.domain.source.interactor.SetMigrateSorting
import app.yokaicore.domain.source.interactor.ToggleLanguage
import app.yokaicore.domain.source.interactor.ToggleSource
import app.yokaicore.domain.source.interactor.ToggleSourcePin
import app.yokaicore.domain.track.interactor.AddTracks
import app.yokaicore.domain.track.interactor.RefreshTracks
import app.yokaicore.domain.track.interactor.SyncChapterProgressWithTrack
import app.yokaicore.domain.track.interactor.TrackChapter
import yokaicore.data.category.CategoryRepositoryImpl
import yokaicore.data.chapter.ChapterRepositoryImpl
import yokaicore.data.history.HistoryRepositoryImpl
import yokaicore.data.manga.MangaRepositoryImpl
import yokaicore.data.release.ReleaseServiceImpl
import yokaicore.data.source.SourceRepositoryImpl
import yokaicore.data.source.StubSourceRepositoryImpl
import yokaicore.data.track.TrackRepositoryImpl
import yokaicore.data.updates.UpdatesRepositoryImpl
import yokaicore.domain.category.interactor.CreateCategoryWithName
import yokaicore.domain.category.interactor.DeleteCategory
import yokaicore.domain.category.interactor.GetCategories
import yokaicore.domain.category.interactor.RenameCategory
import yokaicore.domain.category.interactor.ReorderCategory
import yokaicore.domain.category.interactor.ResetCategoryFlags
import yokaicore.domain.category.interactor.SetDisplayMode
import yokaicore.domain.category.interactor.SetMangaCategories
import yokaicore.domain.category.interactor.SetSortModeForCategory
import yokaicore.domain.category.interactor.UpdateCategory
import yokaicore.domain.category.repository.CategoryRepository
import yokaicore.domain.chapter.interactor.GetChapter
import yokaicore.domain.chapter.interactor.GetChapterByUrlAndMangaId
import yokaicore.domain.chapter.interactor.GetChaptersByMangaId
import yokaicore.domain.chapter.interactor.SetMangaDefaultChapterFlags
import yokaicore.domain.chapter.interactor.ShouldUpdateDbChapter
import yokaicore.domain.chapter.interactor.UpdateChapter
import yokaicore.domain.chapter.repository.ChapterRepository
import yokaicore.domain.history.interactor.GetHistory
import yokaicore.domain.history.interactor.GetNextChapters
import yokaicore.domain.history.interactor.GetTotalReadDuration
import yokaicore.domain.history.interactor.RemoveHistory
import yokaicore.domain.history.interactor.UpsertHistory
import yokaicore.domain.history.repository.HistoryRepository
import yokaicore.domain.manga.interactor.FetchInterval
import yokaicore.domain.manga.interactor.GetDuplicateLibraryManga
import yokaicore.domain.manga.interactor.GetFavorites
import yokaicore.domain.manga.interactor.GetLibraryManga
import yokaicore.domain.manga.interactor.GetManga
import yokaicore.domain.manga.interactor.GetMangaByUrlAndSourceId
import yokaicore.domain.manga.interactor.GetMangaWithChapters
import yokaicore.domain.manga.interactor.NetworkToLocalManga
import yokaicore.domain.manga.interactor.ResetViewerFlags
import yokaicore.domain.manga.interactor.SetMangaChapterFlags
import yokaicore.domain.manga.repository.MangaRepository
import yokaicore.domain.release.interactor.GetApplicationRelease
import yokaicore.domain.release.service.ReleaseService
import yokaicore.domain.source.interactor.GetRemoteManga
import yokaicore.domain.source.interactor.GetSourcesWithNonLibraryManga
import yokaicore.domain.source.repository.SourceRepository
import yokaicore.domain.source.repository.StubSourceRepository
import yokaicore.domain.track.interactor.DeleteTrack
import yokaicore.domain.track.interactor.GetTracks
import yokaicore.domain.track.interactor.GetTracksPerManga
import yokaicore.domain.track.interactor.InsertTrack
import yokaicore.domain.track.repository.TrackRepository
import yokaicore.domain.updates.interactor.GetUpdates
import yokaicore.domain.updates.repository.UpdatesRepository
import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addFactory
import uy.kohesive.injekt.api.addSingletonFactory
import uy.kohesive.injekt.api.get

class DomainModule : InjektModule {

    override fun InjektRegistrar.registerInjectables() {
        addSingletonFactory<CategoryRepository> { CategoryRepositoryImpl(get()) }
        addFactory { GetCategories(get()) }
        addFactory { ResetCategoryFlags(get(), get()) }
        addFactory { SetDisplayMode(get()) }
        addFactory { SetSortModeForCategory(get(), get()) }
        addFactory { CreateCategoryWithName(get(), get()) }
        addFactory { RenameCategory(get()) }
        addFactory { ReorderCategory(get()) }
        addFactory { UpdateCategory(get()) }
        addFactory { DeleteCategory(get()) }

        addSingletonFactory<MangaRepository> { MangaRepositoryImpl(get()) }
        addFactory { GetDuplicateLibraryManga(get()) }
        addFactory { GetFavorites(get()) }
        addFactory { GetLibraryManga(get()) }
        addFactory { GetMangaWithChapters(get(), get()) }
        addFactory { GetMangaByUrlAndSourceId(get()) }
        addFactory { GetManga(get()) }
        addFactory { GetNextChapters(get(), get(), get()) }
        addFactory { ResetViewerFlags(get()) }
        addFactory { SetMangaChapterFlags(get()) }
        addFactory { FetchInterval(get()) }
        addFactory { SetMangaDefaultChapterFlags(get(), get(), get()) }
        addFactory { SetMangaViewerFlags(get()) }
        addFactory { NetworkToLocalManga(get()) }
        addFactory { UpdateManga(get(), get()) }
        addFactory { SetMangaCategories(get()) }
        addFactory { GetExcludedScanlators(get()) }
        addFactory { SetExcludedScanlators(get()) }

        addSingletonFactory<ReleaseService> { ReleaseServiceImpl(get(), get()) }
        addFactory { GetApplicationRelease(get(), get()) }

        addSingletonFactory<TrackRepository> { TrackRepositoryImpl(get()) }
        addFactory { TrackChapter(get(), get(), get(), get()) }
        addFactory { AddTracks(get(), get(), get(), get()) }
        addFactory { RefreshTracks(get(), get(), get(), get()) }
        addFactory { DeleteTrack(get()) }
        addFactory { GetTracksPerManga(get()) }
        addFactory { GetTracks(get()) }
        addFactory { InsertTrack(get()) }
        addFactory { SyncChapterProgressWithTrack(get(), get(), get()) }

        addSingletonFactory<ChapterRepository> { ChapterRepositoryImpl(get()) }
        addFactory { GetChapter(get()) }
        addFactory { GetChaptersByMangaId(get()) }
        addFactory { GetChapterByUrlAndMangaId(get()) }
        addFactory { UpdateChapter(get()) }
        addFactory { SetReadStatus(get(), get(), get(), get()) }
        addFactory { ShouldUpdateDbChapter() }
        addFactory { SyncChaptersWithSource(get(), get(), get(), get(), get(), get(), get(), get()) }
        addFactory { GetAvailableScanlators(get()) }

        addSingletonFactory<HistoryRepository> { HistoryRepositoryImpl(get()) }
        addFactory { GetHistory(get()) }
        addFactory { UpsertHistory(get()) }
        addFactory { RemoveHistory(get()) }
        addFactory { GetTotalReadDuration(get()) }

        addFactory { DeleteDownload(get(), get()) }

        addFactory { GetExtensionsByType(get(), get()) }
        addFactory { GetExtensionSources(get()) }
        addFactory { GetExtensionLanguages(get(), get()) }

        addSingletonFactory<UpdatesRepository> { UpdatesRepositoryImpl(get()) }
        addFactory { GetUpdates(get()) }

        addSingletonFactory<SourceRepository> { SourceRepositoryImpl(get(), get()) }
        addSingletonFactory<StubSourceRepository> { StubSourceRepositoryImpl(get()) }
        addFactory { GetEnabledSources(get(), get()) }
        addFactory { GetLanguagesWithSources(get(), get()) }
        addFactory { GetRemoteManga(get()) }
        addFactory { GetSourcesWithFavoriteCount(get(), get()) }
        addFactory { GetSourcesWithNonLibraryManga(get()) }
        addFactory { SetMigrateSorting(get()) }
        addFactory { ToggleLanguage(get()) }
        addFactory { ToggleSource(get()) }
        addFactory { ToggleSourcePin(get()) }
        addFactory { TrustExtension(get()) }

        addFactory { CreateExtensionRepo(get()) }
        addFactory { DeleteExtensionRepo(get()) }
        addFactory { GetExtensionRepos(get()) }
    }
}
