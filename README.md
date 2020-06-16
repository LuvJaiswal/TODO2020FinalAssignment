# TODO2020FinalAssignment

This is the project based on the developing own version of "TODO" adroid application using MVVM architecture components. 

<img src = "https://thumbs.gfycat.com/BogusGregariousBasil-max-1mb.gif"  width="800" height="500">


## Features

  - Add, Delete, update and Read functionality(CRUD operation) for Todo task.
  - Undo delete of task added with snackbar implementation.
  - Can delete All task at once.
  - Swipe functionality for delete of task.
  - Gmail for feedback 
  - Google Calendar for task reminder.
  - updated task time and date record shown.
  - Search functionality for number of tasks added.
  - Login functionality using SQLITE DATABASE.
  
  | REGISTER       | LOGIN             | Login Validation  |
| ------------- |:-------------:| -----:|
|   <img src = "https://recordit.co/TvhSN1om5D.gif" width="400" height="400">  |  <img src = "https://recordit.co/kjmPenbctx.gif" width="400" height="400"> | <img src = "https://recordit.co/0pz7l2QQV2.gif" width="400" height="400"> |

| Add Task      | Update Task          | Delete Task  |
| ------------- |:-------------:| -----:|
| <img src = "https://recordit.co/xGjKBs3rn6.gif" width="400" height="400">      | <img src = "https://recordit.co/CsboLjwiJp.gif" width="400" height="400"> | <img src = "https://recordit.co/UWhFf3ImMu.gif" width="400" height="400"> |

| Undo Delete     | Register Validation | Testing  |
| ------------- |:-------------:| -----:|
| <img src = "https://recordit.co/uORcmk8H2I.gif" width="400" height="400">    | <img src = "https://recordit.co/b2JOxbTTRX.gif" width="400" height="400"> | $1600 |

| Task Validation      | DeleteAll           | Search added task  |
| ------------- |:-------------:| -----:|
| <img src = "https://recordit.co/nQMrmqoCIs.gif" width="400" height="400"> | <img src = "https://recordit.co/mu0O8P07cT.gif" width="400" height="400"> | <img src = "https://recordit.co/9JvSSc6W4K.gif" width="400" height="400"> |

| Speech To Text      | Gmail          | Google Calendar  |
| ------------- |:-------------:| -----:|
| col 3 is      | <img src = "https://recordit.co/bhJlzlieo9.gif" width="400" height="400"> | <img src = "https://recordit.co/cfeejYm4fh.gif" width="400" height="400">|

 
## Downloads

 | Platform        | Architecture           | Version  |
| ------------- |:-------------:| -----:|
| Android     | X64 |  1.0|
 
 ### Installation

Todo app need to [android studio](https://developer.android.com/studio) to setup in android and clone [project](https://github.com/LuvJaiswal/TODO2020FinalAssignment)

Install
Clone the todo project and setup the in android studio

```sh
$ git clone https://github.com/LuvJaiswal/TODO2020FinalAssignment.git
```

# Implementation steps

We will follow these steps to implement Android Architecture Components in our app:

    1. Add Room and LifeCycle Dependencies
    2. Setup Room
    3. Learn about Live Data
    4. Creating a Repository Class/Presentation Layer
    5. Implement ViewModel
    6. Add Adapter and RecyclerView
    7. Populate the Database
    8. Connect UI and Data
    9. Create AddNoteActivity

# Dependencies

We need to add Room and Lifecycle components. Room is basically a database object mapping library used to access the database. Lifecycle has some good set of classes like ViewModel and LiveData which we will use to manage the lifecycle of our app.
First add version numbers and then add these libraries to build.gradle 

```
    def lifecycle_version = "1.1.1"
    def room_version = "1.1.1"
    
    
    //life cycle components
    implementation "android.arch.lifecycle:extensions:1.1.0"
    annotationProcessor "android.arch.lifecycle:compiler:1.1.0"
    testImplementation "android.arch.core:core-testing:1.1.0"

    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'

    //for room
    implementation 'android.arch.persistence.room:runtime:1.0.0-alpha1'
    annotationProcessor 'android.arch.persistence.room:compiler:1.0.0-alpha1'
    
    }
 ```
   
# Documentation

<b>Android Architecture components</b> are a collection of libraries that help you design robust, testable, and maintainable apps with more power over lifecycle management and data persistence.

# What is MVVM?

MVVM is one of the architectural patterns which enhances the separation of concerns, it allows separating the user interface logic from the business (or the back-end) logic. Its target is to achieve the following principle “Keeping UI code simple and free of app logic in order to make it easier to manage”.

There are 3 parts to the Model-View-ViewModel architecture:-

   1. <b><u>Model</u></b> is the data layer of your app. It abstracts the data source.
   2. <b><u>View</u></b> contains the UI of your app. Most often it’s implemented as an Activity or Fragment. View informs ViewModel of user interactions and displays results received from the ViewModel. View should be lightweight and contain zero to very little business logic.
   3. <b><u>ViewModel</u></b> serves as a bridge between your View and Model. It works with the Model to get and save the data. The View observes and reacts to the data changes exposed by the ViewModel.

Here is a typical high-level MVVM app architecture:

![](https://miro.medium.com/max/1400/1*-yY0l4XD3kLcZz0rO1sfRA.png)

