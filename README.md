# Lost & Found App
A mobile Android application that allows users to log Lost or Found items. The app uses SQLite for it's database to storage adert information locally and access images on their local devices

# Features
- Create, view and remove adverts, including capturing the time the post is created and picking an image from local device
- See list of adverts in dashboard
- Store adverts locally using SQLite
- Clean & responsive UI
- Search functionality

# Tech Stack
- Language: Java
- Software: Android Studio
- Database: SQLite
- UI: XML Layouts

# Architecture
The app follows an Model-View-ViewModel architecture:

- Model: SQLite Database
- ViewModel: Handles UI-related data and survives configuration changes
- View: Activities observing LiveData

# Database
The app uses SQLite for local data persistence.

## Entity
- Item
  - id (Primary Key)
  - title
  - name
  - location
  - description
  - post type (lost/found)
  - image
  - timestamp

### Example Query
- @Query("SELECT * FROM items ORDER BY date DESC"

### Screens:
- Main Activity (Create/View adverts buttons)
- CreateAdvert
- LostFoundItems (Dashboard)
- AdvertDetailActivity (Expanded advert view)

## Installation
1. Clone the repository:
   git clone https://github.com/vhudson04/LostFoundApp

2. Open the project in Android Studio

3. Build and run the app on an emulator or physical device
- MainActivity (Home)
