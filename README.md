This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…


## Video Demo
[![Everything Is AWESOME](https://github.com/user-attachments/assets/36fc1ba7-ebf4-4dfa-9207-31765cb812b4)](https://youtu.be/pE2hb2xFS6g)

## Application Functionality

* Add Tasks

* Display task list

* Manage tasks

## Task managment 
* Mark task as "In Progress", "Completed", or "Cancelled"

*  Delete task

*  View detailed information about the task
  

#### It use local Room Database (Local Storage) and save tasks.

#### Validate input data

####  Ability to sort tasks by date added or expected time

#### Ability to filter tasks by status



