# Kotlin Multiplatform (KMP) — Architecture & Navigation Deep Dive
### Study Guide & Interview Prep

---

## Table of Contents

1. [What is Kotlin Multiplatform (KMP)?](#1-what-is-kotlin-multiplatform-kmp)
2. [Project Structure Overview](#2-project-structure-overview)
3. [Expect & Actual — The KMP Superpower](#3-expect--actual--the-kmp-superpower)
4. [Compose Multiplatform](#4-compose-multiplatform)
5. [Navigation Architecture](#5-navigation-architecture)
   - [NavController](#navcontroller)
   - [NavHost](#navhost)
   - [NavGraph](#navgraph)
   - [Routes (Sealed Classes)](#routes-sealed-classes)
   - [Two-Level Navigation in This Project](#two-level-navigation-in-this-project)
6. [Scaffold & Material 3 Layout](#6-scaffold--material-3-layout)
7. [Bottom Navigation Bar](#7-bottom-navigation-bar)
8. [State Management in Compose](#8-state-management-in-compose)
9. [Theming System](#9-theming-system)
10. [Platform Entry Points](#10-platform-entry-points)
11. [Build System — Gradle & Version Catalog](#11-build-system--gradle--version-catalog)
12. [Interview Q&A Cheatsheet](#12-interview-qa-cheatsheet)

---

## 1. What is Kotlin Multiplatform (KMP)?

**Definition:**
Kotlin Multiplatform (KMP) is a Kotlin feature that lets you share business logic code across multiple platforms (Android, iOS, Desktop, Web) while keeping platform-specific implementations where needed.

**Key Idea:**
```
[Shared Code — commonMain]
       |
   ┌───┴───────┐──────────────┐──────────────┐
Android     iOS (Swift)    JVM (Desktop)   WASM (Web)
```

**What you share:**
- Business logic (ViewModels, Repositories, Use Cases)
- UI (if using Compose Multiplatform)
- Data models, utilities
- Navigation

**What stays platform-specific:**
- Camera, Bluetooth, sensors
- Native UI (if not using Compose MP)
- Platform-specific SDKs

**KMP vs Flutter vs React Native:**

| | KMP | Flutter | React Native |
|---|---|---|---|
| Language | Kotlin | Dart | JavaScript |
| UI Framework | Native / Compose MP | Skia Canvas | Native bridges |
| Code sharing | Logic + UI | Full stack | Logic + UI |
| Native Feel | Yes (uses actual native views) | Not always | Yes |

---

## 2. Project Structure Overview

```
KMP_App/
├── composeApp/                    ← Main shared module
│   └── src/
│       ├── commonMain/            ← Shared code (all platforms)
│       │   ├── kotlin/com/kroy/kmp_project/
│       │   │   ├── App.kt                        ← App entry composable
│       │   │   ├── Platform.kt                   ← expect declaration
│       │   │   ├── Greeting.kt
│       │   │   ├── theme/
│       │   │   │   ├── Color.kt
│       │   │   │   └── Theme.kt
│       │   │   ├── ui/
│       │   │   │   ├── MainScreen.kt             ← Scaffold + BottomNav
│       │   │   │   ├── navigation/
│       │   │   │   │   ├── Routes.kt             ← Sealed class routes
│       │   │   │   │   ├── BottomNavigationitem.kt
│       │   │   │   │   ├── NewsBottomNaviBar.kt
│       │   │   │   │   └── graphs/
│       │   │   │   │       ├── RootNavGraph.kt   ← Root NavHost
│       │   │   │   │       └── MainNavGraph.kt   ← Bottom-tab NavHost
│       │   │   │   ├── headline/HeadLineScreen.kt
│       │   │   │   ├── search/SearchScreen.kt
│       │   │   │   ├── bookmark/BookmarkScreen.kt
│       │   │   │   └── setting/SettingScreen.kt
│       │   │   └── utils/
│       │   │       ├── CommonExpectedActualimpl.kt  ← expect utilities
│       │   │       └── Constants.kt
│       │   └── composeResources/
│       │       ├── drawable/      ← SVG icons (shared)
│       │       └── values/strings.xml
│       ├── androidMain/           ← Android-specific actual implementations
│       ├── iosMain/               ← iOS-specific actual implementations
│       ├── jvmMain/               ← Desktop-specific actual implementations
│       ├── webMain/               ← WASM/Web-specific actual implementations
│       └── nativeMain/            ← Native placeholder
├── iosApp/                        ← Xcode iOS wrapper
└── gradle/
    └── libs.versions.toml         ← Version catalog
```

**Rule of thumb:** Anything in `commonMain` runs on ALL platforms. Anything in `androidMain`, `iosMain`, etc. runs only on that platform.

---

## 3. Expect & Actual — The KMP Superpower

### What is Expect/Actual?

Expect/Actual is the KMP mechanism for writing platform-specific code while maintaining a unified interface in shared code.

- `expect` → "I promise this will exist" (written in `commonMain`)
- `actual` → "Here's my platform's implementation" (written in each platform's source set)

The Kotlin compiler enforces that every `expect` declaration has a matching `actual` in every target platform.

---

### Example 1: `Platform.kt` (Platform Name)

**`commonMain/Platform.kt`** — the contract:
```kotlin
interface Platform {
    val name: String
}
expect fun getPlatform(): Platform
```

**`androidMain/Platform.android.kt`** — Android actual:
```kotlin
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}
actual fun getPlatform(): Platform = AndroidPlatform()
```

**`iosMain/Platform.ios.kt`** — iOS actual:
```kotlin
class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
actual fun getPlatform(): Platform = IOSPlatform()
```

**`jvmMain/Platform.jvm.kt`** — Desktop (JVM) actual:
```kotlin
class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}
actual fun getPlatform(): Platform = JVMPlatform()
```

---

### Example 2: `CommonExpectedActualimpl.kt` (Utility Functions)

**`commonMain/utils/CommonExpectedActualimpl.kt`** — expects:
```kotlin
expect fun getType(): Type
expect fun getRandomId(): String
```

**`androidMain`** — actual uses Java UUID:
```kotlin
actual fun getRandomId(): String = java.util.UUID.randomUUID().toString()
```

**`iosMain`** — actual uses Foundation UUID:
```kotlin
actual fun getRandomId(): String = platform.Foundation.NSUUID.UUID().UUIDString()
```

**`webMain`** — actual returns hardcoded ID (WASM limitation):
```kotlin
actual fun getRandomId(): String = "WEBID"
```

---

### Why Expect/Actual Matters (Interview Answer)

> "Expect/Actual lets me write one API contract in shared code and provide platform-optimized implementations per target. For example, generating a UUID on Android uses `java.util.UUID` while on iOS it uses `NSUUID` — the shared code never needs to know the difference."

---

### Expect vs Interface — When to Use Which?

| | `expect`/`actual` | Interface |
|---|---|---|
| When needed | Platform-level differences (APIs, system calls) | Business logic variations |
| Resolved at | Compile time | Runtime |
| Can have `actual class` | Yes | No (implementations are separate classes) |
| Typical use | UUID, Platform name, file access | Repository pattern, abstractions |

---

## 4. Compose Multiplatform

**Definition:**
Compose Multiplatform (CMP) extends Jetpack Compose's declarative UI to iOS, Desktop, and Web — sharing the same UI code written once in `commonMain`.

**Entry Point Pattern:**

Each platform has a thin wrapper that calls the shared `App()` composable:

```
┌─────────────────────────────────────┐
│         commonMain/App.kt           │
│   @Composable fun App() {           │
│       NewsAppTheme {                │
│           RootNavGraph()            │
│       }                             │
│   }                                 │
└────────────┬────────────────────────┘
             │  called by
    ┌────────┼────────────┬────────────────┐
    ▼        ▼            ▼                ▼
Android     iOS          JVM (Desktop)   WASM
MainActivity MainViewController  main()  main()
setContent{ } ComposeUIViewController application{ }
```

**Android Entry:**
```kotlin
// androidMain/MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}
```

**iOS Entry:**
```kotlin
// iosMain/MainViewController.kt
fun MainViewController() = ComposeUIViewController { App() }
```

**Desktop (JVM) Entry:**
```kotlin
// jvmMain/main.kt
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KMP_Project") {
        App()
    }
}
```

---

## 5. Navigation Architecture

### NavController

**Definition:**
`NavController` is the central API for navigation. It tracks the back stack and manages composable destinations. Think of it as the "brain" that knows where you are and where you came from.

**How to create it:**
```kotlin
val navController = rememberNavController()
```

`rememberNavController()` creates a `NavHostController` and ties its lifecycle to the composition. It remembers its state across recompositions.

**Key NavController operations:**

| Operation | Code | What it does |
|---|---|---|
| Navigate forward | `navController.navigate("route")` | Pushes a new destination |
| Go back | `navController.popBackStack()` | Pops the top of back stack |
| Navigate & clear stack | `navController.navigate("route") { popUpTo("start") { inclusive = true } }` | Clears back stack up to a point |
| Navigate without duplicate | `navController.navigate("route") { launchSingleTop = true }` | No duplicate destinations |

---

### NavHost

**Definition:**
`NavHost` is a composable that acts as a container for your navigation graph. It displays the current destination based on the `NavController`'s state.

```kotlin
NavHost(
    navController = navController,
    startDestination = "home",   // first screen shown
    route = "root_graph"         // optional graph-level route for nesting
) {
    composable("home") { HomeScreen() }
    composable("detail") { DetailScreen() }
}
```

**NavHost is NOT just a container** — it also:
- Manages back stack automatically
- Applies enter/exit transitions
- Can nest other NavHosts (nested navigation)

---

### NavGraph

**Definition:**
A NavGraph is a set of destinations and the connections between them. In code, it's the lambda block inside `NavHost { ... }`. Graphs can be nested — a sub-graph is called a **nested navigation graph**.

**Why use graphs?**
- Encapsulate related screens
- Separate concerns (auth flow vs main flow vs settings flow)
- Support deep linking per graph
- Enable conditional navigation (show onboarding only on first launch)

**In this project — two graphs:**

```
[RootNavGraph]  ← top-level graph ("root_screen_graph")
│
├── [MainScreenGraph]  ← nested graph ("main_screen_graph")
│   ├── HeadlineScreen
│   ├── SearchScreen
│   └── BookmarkScreen
│
└── SettingScreen
```

---

### Routes (Sealed Classes)

**Definition:**
Routes are string identifiers for each destination. Using sealed classes instead of raw strings prevents typos and enables compile-time safety.

**`Routes.kt`:**
```kotlin
object Graph {
    const val RootScreenGraph = "root_screen_graph"
    const val MainScreenGraph = "main_screen_graph"
    const val SearchScreenGraph = "search_screen_graph"
}

sealed class MainRouteScreen(var route: String) {
    object Headlines : MainRouteScreen("headlines")
    object Search    : MainRouteScreen("search")
    object Bookmark  : MainRouteScreen("bookmark")
}

sealed class SettingRouteScreen(var route: String) {
    object Setting : SettingRouteScreen("setting")
}
```

**Why sealed class over `object` with constants?**

| | Sealed Class | String Constants |
|---|---|---|
| Typo protection | Yes (compiler error) | No (runtime crash) |
| Exhaustive `when` | Yes | No |
| Extensible | Yes (add screens) | Somewhat |
| Grouping | Natural | Manual |

---

### Two-Level Navigation in This Project

This project uses **nested navigation** — a common real-world pattern.

**Level 1 — Root Navigation (RootNavGraph.kt):**

Controls top-level routing: `MainScreen` ↔ `SettingScreen`

```kotlin
@Composable
fun RootNavGraph() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        route = Graph.RootScreenGraph,
        startDestination = Graph.MainScreenGraph  // Start inside MainScreenGraph
    ) {
        composable(route = Graph.MainScreenGraph) {
            MainScreen(rootNavController)    // Passes root controller down
        }
        composable(route = SettingRouteScreen.Setting.route) {
            SettingScreen(rootNavController)
        }
    }
}
```

**Level 2 — Main (Bottom Tab) Navigation (MainNavGraph.kt):**

Controls which bottom-tab screen is visible.

```kotlin
@Composable
fun MainNavGraph(
    rootNavController: NavHostController,  // received from RootNavGraph
    homeNavController: NavHostController,  // created in MainScreen
    paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        navController = homeNavController,
        route = Graph.MainScreenGraph,
        startDestination = MainRouteScreen.Headlines.route
    ) {
        composable(route = MainRouteScreen.Headlines.route) { HeadLineScreen() }
        composable(route = MainRouteScreen.Search.route)    { SearchScreen() }
        composable(route = MainRouteScreen.Bookmark.route)  { BookmarkScreen() }
    }
}
```

**Navigation Flow Diagram:**

```
App()
 └── RootNavGraph()
      ├── rootNavController (NavHostController)
      │
      └── NavHost [RootScreenGraph]
           │
           ├── composable("main_screen_graph") ──► MainScreen(rootNavController)
           │                                             │
           │                                    homeNavController = rememberNavController()
           │                                             │
           │                                    Scaffold {
           │                                        TopBar
           │                                        BottomBar (NewsBottomNaviBar)
           │                                        Content: MainNavGraph(homeNavController)
           │                                             │
           │                                    NavHost [MainScreenGraph]
           │                                        ├── HeadLineScreen
           │                                        ├── SearchScreen
           │                                        └── BookmarkScreen
           │                                    }
           │
           └── composable("setting") ──────────► SettingScreen(rootNavController)
```

**Key Design Decision:**
- `rootNavController` is passed to `MainScreen` so the settings icon in the TopBar can navigate to `SettingScreen` (which is outside `MainNavGraph`).
- `homeNavController` is created inside `MainScreen` and passed to `MainNavGraph` and `NewsBottomNaviBar` — it only knows about bottom-tab screens.

---

## 6. Scaffold & Material 3 Layout

**Definition:**
`Scaffold` is a Material 3 composable that implements the basic material design visual layout structure. It provides slots for TopBar, BottomBar, FAB, Snackbar, and the main content area.

```kotlin
Scaffold(
    topBar = { /* TopAppBar */ },
    bottomBar = { /* NavigationBar */ },
    content = { paddingValues ->
        // paddingValues ensures content doesn't go under the bars
        MainNavGraph(paddingValues = paddingValues, ...)
    }
)
```

**Why pass `paddingValues` to content?**

The Scaffold calculates the height of the top and bottom bars and gives it to the content via `PaddingValues`. The content applies this padding via `Modifier.padding(paddingValues)` to avoid being hidden behind the bars.

**In MainScreen.kt:**
```kotlin
Scaffold(
    topBar = {
        TopAppBar(
            title = { Text(stringResource(topBarTitle)) },
            actions = {
                IconButton(onClick = {
                    rootNavController.navigate(SettingRouteScreen.Setting.route)
                }) {
                    Icon(painterResource(Res.drawable.ic_settings), contentDescription = null)
                }
            }
        )
    },
    bottomBar = {
        NewsBottomNaviBar(
            homeNavController = homeNavController,
            items = bottomeNavigationitemList
        )
    }
) { paddingValues ->
    MainNavGraph(
        rootNavController = rootNavController,
        homeNavController = homeNavController,
        paddingValues = paddingValues
    )
}
```

---

## 7. Bottom Navigation Bar

**`BottomNavigationitem.kt` — data model:**
```kotlin
data class BottomNavigationitem(
    val icon: DrawableResource,
    val title: StringResource,
    val route: String
)
```

**`NewsBottomNaviBar.kt`:**

```kotlin
@Composable
fun NewsBottomNaviBar(
    homeNavController: NavHostController,
    items: List<BottomNavigationitem>
) {
    val backStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    homeNavController.navigate(item.route) {
                        popUpTo(homeNavController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painterResource(item.icon), contentDescription = null) },
                label = { Text(stringResource(item.title)) }
            )
        }
    }
}
```

**Key navigation options in `navigate { ... }` block:**

| Option | Purpose |
|---|---|
| `popUpTo(startId)` | Pop back stack up to start so you don't accumulate tabs |
| `saveState = true` | Save state of popped destinations |
| `launchSingleTop = true` | Don't create duplicate instances of same destination |
| `restoreState = true` | Restore saved state when re-selecting same tab |

---

## 8. State Management in Compose

### Core State APIs Used

**`mutableStateOf()`** — observable state holder:
```kotlin
var count by mutableStateOf(0)
```

**`remember { }`** — survives recomposition, lost on config change:
```kotlin
val value = remember { mutableStateOf("initial") }
```

**`rememberSaveable { }`** — survives recomposition AND config changes (saves to Bundle):
```kotlin
val currentRoute by rememberSaveable { mutableStateOf(navBackStackEntry?.destination?.route) }
```

**`derivedStateOf { }`** — computed state, only recomputes when inputs change:
```kotlin
val topBarTitle by remember(currentRoute) {
    derivedStateOf {
        if (currentRoute != null) {
            items[items.indexOfFirst { it.route == currentRoute }].title
        } else {
            items[0].title
        }
    }
}
```

**`currentBackStackEntryAsState()`** — observes NavController back stack as Compose State:
```kotlin
val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
val currentRoute = navBackStackEntry?.destination?.route
```

### State Flow in MainScreen

```
homeNavController.currentBackStackEntryAsState()
         │
         ▼
  navBackStackEntry (State<NavBackStackEntry?>)
         │
         ▼
  currentRoute (rememberSaveable — String?)
         │
         ▼
  topBarTitle (derivedStateOf — StringResource)
         │
         ▼
  TopAppBar title text
```

---

## 9. Theming System

**`Color.kt`** — color palette:
```kotlin
// Light theme colors
val Purple40      = Color(0xFF6650a4)
val PurpleGrey40  = Color(0xFF625b71)
val Pink40        = Color(0xFF7D5260)

// Dark theme colors
val Purple80      = Color(0xFFD0BCFF)
val PurpleGrey80  = Color(0xFFCCC2DC)
val Pink80        = Color(0xFFEFB8C8)

// Shimmer (for loading skeletons)
val shimmer = Color(0xFFC3C3C3)
val shimmerColors = listOf(
    shimmer.copy(alpha = 0.3f),
    shimmer.copy(alpha = 0.5f),
    shimmer.copy(alpha = 1.0f),
    shimmer.copy(alpha = 0.5f),
    shimmer.copy(alpha = 0.3f),
)
```

**`Theme.kt`** — `MaterialTheme` wrapper:
```kotlin
@Composable
fun NewsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}
```

Called once at the top of the app in `App.kt`:
```kotlin
@Composable
fun App() {
    NewsAppTheme(darkTheme = false) {
        RootNavGraph()
    }
}
```

**Key Concepts:**

| Term | Definition |
|---|---|
| `MaterialTheme` | Provides `colorScheme`, `typography`, `shapes` to all child composables via `CompositionLocal` |
| `darkColorScheme()` | Pre-built Material 3 dark color palette builder |
| `lightColorScheme()` | Pre-built Material 3 light color palette builder |
| `isSystemInDarkTheme()` | Reads OS-level dark mode setting |
| `dynamicColor` | Android 12+ Material You — uses wallpaper colors (not used here since CMP has no platform check) |

---

## 10. Platform Entry Points

Every platform bootstraps the same `App()` composable differently:

| Platform | File | Mechanism |
|---|---|---|
| Android | `MainActivity.kt` | `ComponentActivity.setContent { App() }` |
| iOS | `MainViewController.kt` | `ComposeUIViewController { App() }` |
| JVM/Desktop | `main.kt` | `application { Window { App() } }` |
| WASM/Web | `main.wasmJs.kt` | `CanvasBasedWindow { App() }` |

**iOS Framework:**
The Kotlin code is compiled into a **static XCFramework** (`ComposeApp.xcframework`) that is embedded in the Xcode project. The Swift entry point calls the Kotlin `MainViewController()` function.

---

## 11. Build System — Gradle & Version Catalog

### Version Catalog (`gradle/libs.versions.toml`)

A centralized place to declare all library versions:

```toml
[versions]
kotlin                = "2.3.0"
composeMultiplatform  = "1.10.0"
navigation-compose    = "2.9.0-beta02"
android-compileSdk    = "36"
android-minSdk        = "24"

[libraries]
compose-material3          = { module = "org.jetbrains.compose.material3:material3" }
navigation-compose         = { module = "org.jetbrains.androidx.navigation:navigation-compose" }
androidx-lifecycle-viewmodelCompose = { ... }

[plugins]
composeMultiplatform = { id = "org.jetbrains.compose", version.ref = "composeMultiplatform" }
kotlinMultiplatform  = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
```

**Why version catalog?** One place to bump versions, no scattered string literals, IDE autocomplete with `libs.*`.

### composeApp/build.gradle.kts — KMP Targets

```kotlin
kotlin {
    androidTarget { ... }
    jvm("desktop")
    wasmJs { browser { ... } }
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.material3)
                implementation(libs.navigation.compose)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
            }
        }
    }
}
```

---

## 12. Interview Q&A Cheatsheet

**Q: What is KMP and how is it different from Kotlin for Android?**
> KMP is a Kotlin feature that shares code across Android, iOS, Desktop, and Web. Standard Kotlin for Android only targets Android/JVM. KMP adds source sets like `iosMain`, `commonMain`, etc., and the `expect`/`actual` mechanism for platform-specific code.

---

**Q: What is the difference between `expect` and `actual`?**
> `expect` is a declaration in `commonMain` that acts like an interface — it says "this function/class will exist." `actual` provides the platform-specific implementation in `androidMain`, `iosMain`, etc. The compiler enforces that every `expect` has a matching `actual` in every target.

---

**Q: What is `NavController`?**
> `NavController` is the navigation manager in Jetpack Compose Navigation. It maintains the back stack and exposes `navigate()`, `popBackStack()`, and back stack state observation. Created with `rememberNavController()`.

---

**Q: What is `NavHost`?**
> `NavHost` is a composable container that renders the current navigation destination based on the `NavController`'s state. It takes a `startDestination` and a navigation graph builder lambda.

---

**Q: What is a NavGraph?**
> A NavGraph is the collection of destinations and transitions defined inside a `NavHost { }` block. Graphs can be nested — a screen that itself contains a `NavHost` creates a nested graph. This is the pattern used in this project (RootNavGraph contains MainNavGraph).

---

**Q: What is nested navigation and why use it?**
> Nested navigation means having a `NavHost` inside a destination of another `NavHost`. It allows you to scope navigation — the bottom-tab screens share one back stack managed by `homeNavController`, completely separate from the root-level navigation managed by `rootNavController`. This keeps Settings outside the bottom-nav scope.

---

**Q: Why pass `rootNavController` to `MainScreen`?**
> `MainScreen` contains the settings icon in its TopBar. When clicked, it navigates to `SettingScreen`, which is a destination in the root graph — not in the bottom-tab graph. So `rootNavController` must be passed down to trigger that navigation.

---

**Q: What is `rememberSaveable` vs `remember`?**
> `remember` keeps state alive across recompositions but loses it on configuration changes (screen rotation). `rememberSaveable` also saves state to a Bundle so it survives config changes and process death.

---

**Q: What is `derivedStateOf`?**
> `derivedStateOf` creates a State object whose value is derived from other State objects. It only recomputes (and triggers recomposition) when the derived result actually changes — not every time any input State changes. Used here to compute `topBarTitle` from `currentRoute`.

---

**Q: What is Scaffold in Material 3?**
> `Scaffold` provides a layout template with named slots: `topBar`, `bottomBar`, `floatingActionButton`, `snackbarHost`, and `content`. It handles `PaddingValues` — the content slot receives padding equal to the height of the bars so content doesn't get hidden underneath them.

---

**Q: What is `currentBackStackEntryAsState()`?**
> It's an extension function on `NavController` that returns the current back stack entry as Compose `State`. Because it's a `State`, any composable reading it will recompose when the back stack changes — this is how the bottom nav bar knows which item to highlight.

---

**Q: What is `launchSingleTop = true` in navigation?**
> It prevents creating multiple copies of the same destination on the back stack. If you tap the same bottom-nav item you're already on, it won't add a duplicate — it just stays on the current screen.

---

**Q: What is a source set in KMP?**
> A source set is a grouping of code and resources for a specific compilation target. `commonMain` is compiled for all targets. `androidMain` is only compiled for Android. Source sets can depend on each other — `androidMain` depends on `commonMain`, meaning Android code can use anything declared in `commonMain`.

---

*Last updated: 2026-06-14 | Project: KMP_App News App*