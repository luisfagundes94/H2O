# H2O
Drinking Water Android App

### Features:
- Simple and clean design with animations
- A complete water history
- A notification reminder to drink water every x hours

## Screenshots

### Dark Mode
<p float="left">
  <img src="https://github.com/luisfagundes94/H2O/blob/main/screenshots/screenshot1.png" width="200" /> 
  <img src="https://github.com/luisfagundes94/h2o/blob/main/screenshots/screenshot2.png" width="200" />
  <img src="https://github.com/luisfagundes94/h2o/blob/main/screenshots/screenshot3.png" width="200" />
</p>

### Light Mode
<p float="left">
  <img src="https://github.com/luisfagundes94/H2O/blob/main/screenshots/screenshot4.png" width="200" /> 
  <img src="https://github.com/luisfagundes94/h2o/blob/main/screenshots/screenshot5.png" width="200" />
  <img src="https://github.com/luisfagundes94/h2o/blob/main/screenshots/screenshot6.png" width="200" />
</p>

## Architecture
A well-planned architecture is crucial for an app's scalability and managing its complexity. While smaller apps might not need this, it's very helpful for larger projects with longer development times and bigger teams.

Robert C. Martin introduced the concept of Clean Architecture in 2012 via the Clean Code Blog, and it abides by the SOLID principle.

<img src="https://miro.medium.com/v2/resize:fit:772/1*wOmAHDN_zKZJns9YDjtrMw.jpeg" width="500" />

This app uses [_**MVVM (Model-View-ViewModel)**_]([https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d](https://developer.android.com/topic/architecture)) architecture design.
 
<img src="https://github.com/MuhammadKhoshnaw/BasicMVIApp/blob/master/.github/res/ComponentDiagram.svg" width=500 />

# UI
The app was designed using Material 3 guidelines. Learn more about the design process and obtain the design files in the [Material 3 Case Study](https://m3.material.io/).

The Screens and UI elements are built entirely using Jetpack Compose.

The app has two themes:

- Dynamic color - uses colors based on the user's current color theme (if supported)
- Default theme - uses predefined colors when dynamic color is not supported

Each theme also supports dark mode.

# Tech Stack
This project uses many of the popular libraries, plugins and tools of the android ecosystem.

### Compose
- [Material Design 3](https://developer.android.com/jetpack/androidx/releases/compose-material3) - Build Jetpack Compose UIs with ready to use Material Design Components.
- [UI](https://developer.android.com/jetpack/androidx/releases/compose-ui) - Fundamental components of compose UI needed to interact with the device, including layout, drawing, and input.
- [Lifecycle-ViewModel](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
- [HiltViewModel](https://dagger.dev/hilt/view-model.html) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
- [Coil](https://coil-kt.github.io/coil/compose/) - An image loading library for Android backed by Kotlin Coroutines
- [Navigation](https://developer.android.com/develop/ui/compose/navigation) - The Navigation component provides support for Jetpack Compose applications. You can navigate between composables while taking advantage of the Navigation component's infrastructure and features.

### Accompanist
- [SwipeRefresh](https://google.github.io/accompanist/swiperefresh/) - A library which provides a layout which provides the swipe-to-refresh UX pattern, similar to Android's SwipeRefreshLayout.

### Jetpack
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
- [Android KTX](https://developer.android.com/kotlin/ktx.html) - Provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
- [AndroidX](https://developer.android.com/jetpack/androidx) - Major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
- [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
- [Room](https://developer.android.com/training/data-storage/room) - Provides an abstraction layer over SQLite used for offline data caching.

### Dependency Injection
- [Dagger Hilt](https://dagger.dev/hilt/) - Dependency Injection library.

### Core
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines.
- [Flow](https://developer.android.com/kotlin/flow) - Flows are built on top of coroutines and can provide multiple values. A flow is conceptually a stream of data that can be computed asynchronously.

### Testing
- [JUnit4](https://junit.org/junit4/) - JUnit is a simple framework to write repeatable tests.
- [Mockk](https://mockk.io/) - A modern Mockk library for UnitTest.
- [Turbine](https://github.com/cashapp/turbine) - Small testing library for kotlinx.coroutines Flow
- [Coroutine-Test](https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test) - Provides testing utilities for effectively testing coroutines.
