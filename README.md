# Nativeblocks Android Template

A template Android project for integrating with Nativeblocks Studio.

## Prerequisites

- Android Studio
- Android SDK (API level 26 or higher)
- Java 11 or higher

## Setup

### 1. Configuration

Update the `nativeblocks.properties` file in the root of your project and add the following details:

```properties
apiUrl=API_URL
apiKey=API_KEY
authToken=DEVELOPER_TOKEN
organizationId=ORG_ID
```

These properties are specific to each Nativeblocks Studio account. You can find them in the **Link
Device** section of Nativeblocks Studio.

### 2. Build and Run

1. Open the project in Android Studio
2. Sync the project with Gradle files
3. Build and run the application

## Gradle Commands

### Sync Command

Upload the generated JSON files to Nativeblocks Studio:

```bash
# General format
./gradlew nativeblocksSync + Flavor (Debug/Release)

# Example for debug variant
./gradlew :app:nativeblocksSyncDebug
```

### Other Gradle Commands

```bash
# Build the project
./gradlew build

# Clean the project
./gradlew clean
```

## Dependencies

This template includes:

- **Nativeblocks Android SDK** - Core integration
- **Nativeblocks Foundation** - Foundation components
- **Nativeblocks WandKit** - Debugging tool
- **Nativeblocks Compiler** - Annotation processing

## Getting Started

1. Clone this template
2. Configure your `nativeblocks.properties` file
3. Update the `applicationId` in `app/build.gradle.kts` if needed
4. Build and run the project
5. Use the sync command to upload your configurations to Nativeblocks Studio

## Support

For more information and support, visit [Nativeblocks Documentation](https://nativeblocks.io).