package app.yokaicore.util.system

import app.yokaicore.BuildConfig

val isDevFlavor: Boolean
    get() = BuildConfig.FLAVOR == "dev"

val isPreviewBuildType: Boolean
    get() = BuildConfig.BUILD_TYPE == "preview"

val isReleaseBuildType: Boolean
    get() = BuildConfig.BUILD_TYPE == "release"
