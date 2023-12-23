# Custom Calendar View

[![](https://jitpack.io/v/JOOctp/Calendar.svg)](https://jitpack.io/#JOOctp/Calendar)

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
