# Neurobeat

## Project Overview

Neurobeat is an Android mobile application built with Kotlin and Jetpack Compose. It is a single-module project using the standard Android Gradle Plugin (AGP) build system with Kotlin DSL.

- **Package**: `dev.jefrien.neurobeat`
- **Application ID**: `dev.jefrien.neurobeat`
- **Min SDK**: 24 (Android 7.0)
- **Target/Compile SDK**: 36 (Android 16 Baklava)
- **Java Compatibility**: VERSION_11
- **Current Version**: 1.0 (versionCode 1)

## Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | Kotlin | 2.2.10 |
| Build System | Gradle (Kotlin DSL) | — |
| Android Gradle Plugin | AGP | 9.1.1 |
| UI Framework | Jetpack Compose (BOM) | 2026.02.01 |
| Design System | Material3 | — |
| Unit Testing | JUnit 4 | 4.13.2 |
| Instrumented Testing | AndroidJUnit4 + Espresso | 3.5.1 |

### Key Dependencies

- `androidx.core:core-ktx`
- `androidx.lifecycle:lifecycle-runtime-ktx`
- `androidx.activity:activity-compose`
- `androidx.compose.ui:ui`
- `androidx.compose.ui:ui-graphics`
- `androidx.compose.ui:ui-tooling-preview`
- `androidx.compose.material3:material3`

Debug-only dependencies include Compose UI tooling and the test manifest.

## Project Structure

```
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/dev/jefrien/neurobeat/
│   │   │   │   ├── MainActivity.kt          # Entry-point Activity
│   │   │   │   └── ui/theme/
│   │   │   │       ├── Color.kt             # Theme color definitions
│   │   │   │       ├── Theme.kt             # Light/Dark theme setup
│   │   │   │       └── Type.kt              # Typography definitions
│   │   │   ├── res/                         # Android resources
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                            # Unit tests (JVM)
│   │   └── androidTest/                     # Instrumented tests (device/emulator)
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   └── libs.versions.toml                   # Centralized version catalog
├── build.gradle.kts                         # Root build script
├── settings.gradle.kts                      # Project settings & repositories
└── gradle.properties                        # Gradle & Kotlin configuration
```

## Build Commands

All commands should be run from the project root.

```bash
# Build the debug APK
./gradlew :app:assembleDebug

# Build the release APK
./gradlew :app:assembleRelease

# Install debug build on a connected device
./gradlew :app:installDebug

# Clean build outputs
./gradlew clean
```

## Test Commands

```bash
# Run unit tests (JVM, no device required)
./gradlew :app:testDebugUnitTest

# Run instrumented tests (requires connected Android device or emulator)
./gradlew :app:connectedAndroidTest
```

## Code Style Guidelines

- **Kotlin code style**: `official` (configured in `gradle.properties` via `kotlin.code.style=official`).
- Follow standard Kotlin and Android naming conventions.
- Compose UI code uses `@Preview` annotations for composable previews.
- Theme-related code lives under `dev.jefrien.neurobeat.ui.theme`.

## Testing Strategy

- **Unit tests** reside in `app/src/test/java/...` and run on the JVM using JUnit 4.
- **Instrumented tests** reside in `app/src/androidTest/java/...` and run on an Android device/emulator using `AndroidJUnit4` and Espresso.
- The project currently contains only placeholder example tests (`ExampleUnitTest`, `ExampleInstrumentedTest`). Add real tests as features are implemented.

## Security & Build Considerations

- **ProGuard/R8 code shrinking is disabled** for release builds (`isMinifyEnabled = false`).
- The release build type still references the default ProGuard rules file (`proguard-android-optimize.txt`) plus `proguard-rules.pro`, but shrinking has no effect until enabled.
- No API keys, secrets, or network configurations are present in the current codebase.
- Backup and data extraction rules are configured via `res/xml/backup_rules.xml` and `res/xml/data_extraction_rules.xml`.

## Repository Configuration

- Repositories are locked to `google()` and `mavenCentral()`.
- `RepositoriesMode.FAIL_ON_PROJECT_REPOS` is set, preventing modules from declaring their own repositories.
- The `foojay-resolver-convention` plugin (v1.0.0) handles toolchain resolution.
