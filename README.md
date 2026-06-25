# YokaiCore

<p align="center">
  <img src=".github/readme-images/app-icon.png" alt="YokaiCore" width="200"/>
</p>

**YokaiCore** adalah free & open source manga reader untuk Android 6.0+.
Fork dari Tachiyomi v0.15.3 dengan branding baru.

## Download

Lihat [Releases](https://github.com/fuljiz43-ctrl/yokaicore-app/releases) untuk APK terbaru.

## Extension Repo

Tambahkan repo extension YokaiCore di Settings → Browse → Extension repos:
```
https://raw.githubusercontent.com/fuljiz43-ctrl/yokaicore-extensions/repo/index.min.json
```

## Build

### Prerequisites
- Android Studio Hedgehog atau lebih baru
- JDK 17
- Android SDK

### GitHub Actions (Auto Build)
Push tag ke repo → APK otomatis ter-build.

**GitHub Secrets yang dibutuhkan:**
| Secret | Value |
|--------|-------|
| `SIGNING_STORE_PATH` | path ke `yokaicore.jks` |
| `ALIAS` | `yokaicore` |
| `KEY_STORE_PASSWORD` | password keystore |
| `KEY_PASSWORD` | password key |

## Lisensi
Apache 2.0 — lihat [LICENSE](LICENSE)
