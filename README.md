# MicroDO

## What is a MicroDO

As per definition, a MicroDO is  

> A ``MicroDo`` is a software component that eases the implementation of user interactions on Android devices. 
> With the combination of multiple ``MicroDos`` complex user interactions and user interfaces can be represented.

What this means is, that MicroDOs are designed to support the implementation of user interaction within Android Apps.
The goal of the implementation was to support the implementation of Video Editing applications. For this the following
components were implemented to ease the use of specific features for Android. Refer to Readme files within the packages
to find specific usage instructions there.
* [ListToList](lib/src/main/java/at/naske/microdo/lib/listtolist/Readme.md)
* [Parking](lib/src/main/java/at/naske/microdo/lib/parking/Readme.md)
* [CornerGestures](lib/src/main/java/at/naske/microdo/lib/cornergestures/Readme.md)
* [Tagging](lib/src/main/java/at/naske/microdo/lib/tagging/Readme.md)
* [Swipeselect](lib/src/main/java/at/naske/microdo/lib/swipeselect/Readme.md)
* [Alternating List](lib/src/main/java/at/naske/microdo/lib/alternatinglist/Readme.md) 
* [Multimove](lib/src/main/java/at/naske/microdo/lib/multimove/Readme.md) 

## How to include the library within your Code

The library is published on jCenter. Add this to your `app:build.gradle` to add the MicroDO software 
library to your project:

```
implementation 'at.naske.microdo:lib:0.0.2'
```
Maven:
```xml
<dependency>
  <groupId>at.naske.microdo</groupId>
  <artifactId>lib</artifactId>
  <version>0.0.1</version>
</dependency>
```

Ivy:
```xml
<dependency org='at.naske.microdo' name='lib' rev='0.0.1'>

</dependency>
```

To include jCenter to pull projects from you have to include it in your main project `build.gradle` like
this on the root level:

```
allprojects {
    repositories {
        jcenter()
    }
}
```