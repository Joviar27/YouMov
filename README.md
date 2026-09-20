## YouMov: Movie Information & Database Application
A comprehensive movie discovery platform that enables users to explore, find, and access detailed information across an extensive database of films.

## Architecture
This project follows the **Model-View-ViewModel (MVVM)** architectural pattern combined with a **Repository pattern**. This setup decouples UI code from
business and data logic, with the objective to minimize tightly coupled components and modules

* **Presentation Layer (MVVM & Jetpack Compose):** 
  * Uses **Jetpack Compose** for a modern, declarative UI.
  * Uses **Coil** for asynchronous image loading and caching (movie posters, backdrop banners).
  * **ViewModels** handle UI state management, expose data to screen components via state streams, and process user interactions while remaining independent of the UI framework.

* **Data Layer & Repository Pattern:** 
  * A central **Repository** manages data operations, acting as a single source of truth that abstracts where data comes from (remote API or local storage).
  * **Retrofit:** Handles network networking and serialization to perform asynchronous REST API calls to the TMDB endpoints.
  * **TMDB API:** Serves as the remote data source to fetch real-time movie details, ratings, and media.
  * **Room Database:** Serves as the local data source to persistently store and manage favorited movies for offline access.

* **Dependency Injection (Hilt):** 
  * Powered by **Dagger Hilt** to automatically inject dependencies (API services, Room database instances, and Repositories) across components, keeping code modular and loosely coupled.
 
## Features
* **Categorized Movie Browsing:** Discover films across three categories: **Popular**, **Top Rated**, and **Now Playing** in theaters.
* **Movie Details:** View detailed information for any selected film, including overview, ratings, and a community reviews list.
* **Favorites Library:** Easily add or remove movies from a local favorites library for quick access and reference.

![Image](https://github.com/user-attachments/assets/2974cebd-1f95-4e04-b969-32b559243c7c)
