# 645-2 Santour

This project has been made by a group of the HES-SO Valais Wallis Student in the context of an android development course.
This project is separated into two parts: a web interface and an android application.
You can find the web interface project here: https://github.com/Centoroma/SanTourWeb

## Getting Started

In order to run this project, you will need to upload this project on your android studio, 

configure your firebase database and allow the modification of the database by everyone, 

configure the firebase Storage and allow its modification for everyone

and simply run the programm using your smartphone or an android emulator.

### Prerequisites

The prerequisites of this project are: 
```
Android Studio 3.0.1 or higher
An android SDK 18 or higher (target build:  android SDK 26)
GPS emulator (if you want to use the android emulator)
```

## Project Structure

We build this project to follow a standard N-Tier architecture:
```

app/
├── manifests
│    └── AndroidManifest.xml
├── java
│   ├── BLL
│   │   ├── CoordinateManager
│   │   ├── CurrentRecordingTrack
│   │   ├── FirebaseClass
│   │   ├── PODManager
│   │   ├── POIManager
│   │   └── TrackManager
│   ├── ch.hes.santour
│   │   ├── AboutFragment
│   │   ├── CreatePodFragment
│   │   ├── CreatePoiFragment
│   │   ├── DetailsPodFragment
│   │   ├── HomeFragment
│   │   ├── ListPodAdapter
│   │   ├── ListPodDifficulties
│   │   ├── ListPoiAdapter
│   │   ├── loginFragment
│   │   ├── MainActivity
│   │   ├── PodDifficultyViewHolder
│   │   ├── PodViewHolder
│   │   ├── PoiPodListFragment
│   │   ├── SettingsFragment
│   │   ├── UpdateDetailsPodFragment
│   │   ├── UpdatePodFragment
│   │   └── UpdatePoiFragment
│   └── Models
│   │   ├── Coordinate
│   │   ├── Difficulty
│   │   ├── POD
│   │   ├── POI
│   │   ├── Role
│   │   ├── Track
│   │   └── User
└── res
    ├── drawable
    │   └── ...
    ├── layout
    │   ├── activity_main.xml
    │   ├── details_row.xml
    │   ├── fragment_about.xml
    │   ├── fragment_create_pod.xml
    │   ├── fragment_create_poi.xml
    │   ├── fragment_details_pod.xml
    │   ├── fragment_home.xml
    │   ├── fragment_login.xml
    │   ├── fragment_poi_pod_list.xml
    │   ├── fragment_settings.xml
    │   ├── fragment_update_details_pod.xml
    │   ├── fragment_update_pod.xml
    │   ├── fragment_update_poi.xml
    │   └── pod_row.xml
    ├── menu
    │   └── ...
    ├── mipmap
    │   └── ...
    └── values
        ├── strings.xml
            │   ├── strings.xml (en)
            │   ├── strings.xml (de)
            │   └── strings.xml (fr)
        └── ...
```

## Navigation

The navigation of our project work like this:
```
MainActivity
├── fragment_create_track
│   ├── fragment_create_pod
│   │   └── fragment_details_pod ─── details_row
│   └── fragment_create_pod
├── fragment_poi_pod_list ─── pod_row
│   ├── fragment_update_pod
│   │   └── fragment_update_details_pod ─── details_row
│   └── fragment_update_poi
├── fragment_about
└── fragment_settings
```

We have implemented only one activity in order to manage the recording of our tracks even when the application is not active

## Technical choices

When we arrive on our application, you first need to start creating a track, this will enable the recording on firebase, we have also enable the offline registration.

In order to be certain to record only one track at a time, we create a class CurrentRecordingTrack that have a track in static, with that we are certain to have only one track at a time.

To store pictures inside our database we choose to use the storage of firebase. The path to the image is image/[Id_Track]/POD(POI)/picture[numbeer of the POD/POI in the track].jpg

## Authors

* **Audrey Michel** - *User interface* - [audreycelia](https://github.com/audreycelia)
* **Colin Chappot** - *Geolocalisation and Map* - [ColinChappot](https://github.com/ColinChappot)
* **Kévin Carneiro** - *Web Interface* - [kevdlc](https://github.com/kevdlc)
* **Lucien Zuber** - *Database and architecture* - [FinalCondom](https://github.com/FinalCondom)
* **Luca Centofanti** - *Scrum Master* - [Centoroma](https://github.com/Centoroma)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details