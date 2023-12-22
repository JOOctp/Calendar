# Custom Calendar View

1. Step one add it in your root build.gradle at the end of repositories:
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
  }
}
```
2. Step two Add the dependency:
```
dependencies {
  implementation 'com.github.JOOctp:Calendar:1.0.0'
}
```
