# OpenJFX Two Media Views Sample

A simple example project to demonstrate playing a video with the `MediaPlayer`
simultaneously within two `MediaView`s.


## Prerequisites

You should have installed a JDK version 21 or higher.


## Checkout and Run

Clone the repository into a local directory: 

```
git clone https://github.com/n-gabe/jfx-two-media-view-sample.git
```

Build and run with Gradle:

```
./gradlew run
```


## Important Notice

Currently one of the `MediaView`s cancels to working after pressing the stop
button the first time. This is due to a bug in den OpenJFX project
[JDK-8146918](https://bugs.openjdk.org/browse/JDK-8146918).
