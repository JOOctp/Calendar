# Custom Calendar View

[![](https://jitpack.io/v/JOOctp/Calendar.svg)](https://jitpack.io/#JOOctp/Calendar)

<img width="309" alt="Screenshot 2023-12-23 at 15 28 34" src="https://github.com/JOOctp/Calendar/assets/42791249/bd51e652-4ef0-4e23-90f5-ae887dc870e2">
<img width="308" alt="Screenshot 2023-12-23 at 15 46 14" src="https://github.com/JOOctp/Calendar/assets/42791249/43258e26-59cc-4594-9f45-ccfd29628dd2">
<img width="308" alt="Screenshot 2023-12-23 at 15 38 30" src="https://github.com/JOOctp/Calendar/assets/42791249/00ece3b6-a340-4ebd-9888-236b7a5f7af1">
<img width="305" alt="Screenshot 2023-12-23 at 16 06 54" src="https://github.com/JOOctp/Calendar/assets/42791249/8c35b12e-f702-49ec-bf71-d3a01bfb41f0">



1. Step one add it in your root build.gradle at the end of repositories:
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" )  }
    }
}
```

2. Step two Add the dependency:
```
dependencies {
  implementation("com.github.JOOctp:Calendar:1.0.3")
}
```

3. Set your locale using xml or programmatically
```
<com.jop.calendar.CalendarView
    android:id="@+id/calendar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:locale="id-ID"/>
```
```
binding.apply {
    calendar.setLocale(Locale("id", "ID"))
}
```

4. You can customize the appearance of the buttons, from the background, stroke, icon color, and button shape using xml or programmatically.
```
<com.jop.calendar.CalendarView
    android:id="@+id/calendar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:locale="id-ID"
    app:buttonStrokeColor="@color/green"
    app:buttonBackgroundColor="@color/greenAccent"
    app:buttonIconColor="@color/green"
    app:isCircleButton="false"/>
```
```
binding.apply {
    calendar.setButtonStrokeColor(ColorStateList.valueOf(resources.getColor(R.color.green)))
    calendar.setButtonBackgroundColor(ColorStateList.valueOf(resources.getColor(R.color.greenAccent)))
    calendar.setButtonIconColor(ColorStateList.valueOf(resources.getColor(R.color.green)))
    calendar.setIsCircleButton(false)
}
```

6. You can set the weekend color using xml or programmatically.
```
<com.jop.calendar.CalendarView
    android:id="@+id/calendar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:locale="id-ID"
    app:textWeekEndColor="@color/grey"/>
```

7. Callback function when clicked on "selected date"
```
 binding.apply {
    calendar.setCalenderViewListener(object : CalendarView.CalendarViewListener{
        override fun onSelectedDate(date: Date) {
            TODO("Not yet implemented")
        }
    })
}
```
