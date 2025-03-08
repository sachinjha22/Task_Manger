TASK MANGER PROJECT STRUCTURE
------------------------------------

com.example.taskmanager
│── data                     ////////////////////// save all local data and models here
│   ├── db
│   │   ├── taskdb
│   │   │   ├── DateConverters.kt        ////////////////   type converter help to convert date to long and long to date in db
│   │   │   ├── TaskDao.kt              ////////////////    help to get data from db and update , delet, insert
│   │   │   ├── TaskDatabase.kt        ///////////////     task db
│   ├── model
│   │   ├── filter 
│   │   │   ├── SortOption.kt         ///////////////    shorting option for task
│   │   │   ├── TaskStatus.kt        ///////////////     filter by status for task
│   │   ├── AppTheme.kt               ///////////////    help user to change dynamic primary color
│   │   ├── Task.kt                 ///////////////    task entity
│   ├── local
│   │   ├── DataStoreManager.kt    ///////////////    local sharepref for saving data
│   ├── repository
│   │   ├── TaskRepository.kt     ///////////////   repository for handling db related data and making connection with dao
│
│── di
│   ├── AppModule.kt            ///////////////   hilt dependency injection
│── service
│   ├── TaskReminderWorker.kt  ///////////////   service class for sending notification every day 
│
│── ui
│   ├── activity
│   │   ├── DBActivity.kt     ///////////////   Dashboard Activity 
│   ├── component
│   │   ├── TaskItem.kt      /////////     converting data entery of task into view
│   │   ├── TextFieldComponent.kt    /////////     common editText
│   ├── navigation
│   │   ├── TaskNavHost.kt          /////////     navigation graph for screen
│   ├── screen
│   │   ├── add
│   │   │   ├── AddTaskScreen.kt     /////////     add new task screen
│   │   │   ├── DueDatePicker.kt     /////////     date picker helper
│   │   │   ├── PriorityDropdown.kt  /////////     Drop down helper for priority
│   │   ├── detail
│   │   │   ├── TaskDetailsScreen.kt  /////////     task detail screen
│   │   ├── home
│   │   │   ├── EmptyStateUI.kt    /////////     no task screen
│   │   │   ├── FilterDropdown.kt   /////////     filter helper
│   │   │   ├── HomeScreenNew.kt     /////////     task item view screen
│   │   │   ├── ShimmerScreen.kt      /////////     shimmer until date fetching from dp
│   │   │   ├── SortDropdown.kt      /////////     sort task helper
│   │   │   ├── TaskListScreen.kt   /////////     list view showing Screen
│   │   │   ├── TaskProgressIndicator.kt     /////////     Progress bar
│   │   ├── setting
│   │   │   ├── SettingsScreen.kt    /////////     setting screen
│   ├── theme
│   │   ├── Color.kt   /////////     all color here
│   │   ├── Theme.kt   /////////     theme helper
│   │   ├── Type.kt     /////////     typography
│
│── utils
│   ├── DataStore.kt    /// DataStore helper for storing data in sharedPref
│   ├── Extension.kt   /// common app extension 
│   ├── Resource.kt   /// helping in to mange state until ate getting from db 
│   ├── TMApplication.kt   /// Application class
│
│── viewmodel             /// view MOdel
│   ├── TaskViewModel.kt
│   ├── SettingsViewModel.kt

