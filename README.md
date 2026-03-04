# MyJeans Android WebView App

A simple Android WebView application that loads http://myjeans-sy.com/ with push notification support.

## Features

- **WebView**: Loads the MyJeans website
- **External Links**: Allows all external links to open within the app
- **Push Notifications**: Firebase Cloud Messaging integration ready
- **Back Navigation**: Back button navigates WebView history

## Building the APK

### Prerequisites

1. Install Android Studio (https://developer.android.com/studio)
2. Install JDK 17 or higher

### Build Steps

1. Open Android Studio
2. Select "Open an existing project"
3. Navigate to and select the `MyJeansApp` folder
4. Let Android Studio sync and download dependencies
5. Go to `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
6. The APK will be generated in: `app/build/outputs/apk/debug/app-debug.apk`

### Alternative: Command Line Build

```bash
# Navigate to project directory
cd MyJeansApp

# Make gradlew executable (on Mac/Linux)
# chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Or on Windows
gradlew.bat assembleDebug
```

## Testing on BlueStacks

1. Build the APK using the steps above
2. Open BlueStacks App Player
3. Drag and drop the APK file onto BlueStacks, or:
   - Click "Install APK" in BlueStacks
   - Browse to `app/build/outputs/apk/debug/app-debug.apk`
4. The app will install and appear in the app drawer

## Push Notifications Setup (Optional)

To enable push notifications:

1. Create a Firebase project at https://console.firebase.google.com/
2. Add your Android app with package name: `com.myjeans.app`
3. Download `google-services.json` and place it in `app/` folder
4. Rebuild the APK

## Project Structure

```
MyJeansApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/myjeans/app/
│   │   │   ├── MainActivity.java           # Main WebView activity
│   │   │   └── MyFirebaseMessagingService.java  # Push notification handler
│   │   ├── res/
│   │   │   ├── layout/activity_main.xml   # Main layout
│   │   │   └── values/                    # Colors, strings, themes
│   │   └── AndroidManifest.xml            # App manifest
│   └── build.gradle                       # App-level Gradle config
├── build.gradle                           # Project-level Gradle config
└── settings.gradle
```
