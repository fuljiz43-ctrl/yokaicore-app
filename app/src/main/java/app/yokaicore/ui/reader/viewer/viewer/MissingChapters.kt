package app.yokaicore.ui.reader.viewer

import app.yokaicore.data.database.models.toDomainChapter
import app.yokaicore.ui.reader.model.ReaderChapter
import yokaicore.domain.chapter.service.calculateChapterGap as domainCalculateChapterGap

fun calculateChapterGap(higherReaderChapter: ReaderChapter?, lowerReaderChapter: ReaderChapter?): Int {
    return domainCalculateChapterGap(
        higherReaderChapter?.chapter?.toDomainChapter(),
        lowerReaderChapter?.chapter?.toDomainChapter(),
    )
}
