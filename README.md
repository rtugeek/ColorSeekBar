![ColorSeekbar](https://github.com/Tobaloidee/ColorSeekBar/blob/master/logo/colorseekbar-02.png)

### ScreenShot:

<img src="https://github.com/rtugeek/ColorSeekBar/blob/master/screenshot/color.jpg" width="300" />
<img src="https://github.com/rtugeek/ColorSeekBar/blob/master/screenshot/alpha.jpg" width="300" />

### ColorSeekbar Attrs

```xml

<declare-styleable name="ColorSeekBar">
    <attr name="colorSeekBarColorSeeds" format="reference" />
    <attr name="colorSeekBarBarHeight" format="dimension" />
    <attr name="colorSeekBarProgress" format="integer" />
    <attr name="colorSeekBarRadius" format="dimension" />
    <attr name="colorSeekBarMaxProgress" format="integer" />
    <attr name="colorSeekBarVertical" format="boolean" />
    <attr name="colorSeekBarShowThumb" format="boolean" />
    <attr name="colorSeekBarBorderColor" format="color" />
    <attr name="colorSeekBarBorderSize" format="dimension" />
</declare-styleable>
```

### AlphaSeekbar Attrs

```xml

<declare-styleable name="AlphaSeekBar">
    <attr name="alphaSeekBarHeight" format="dimension" />
    <attr name="alphaSeekBarRadius" format="dimension" />
    <attr name="alphaSeekBarShowGrid" format="boolean" />
    <attr name="alphaSeekBarShowThumb" format="boolean" />
    <attr name="alphaSeekBarSizeGrid" format="dimension" />
    <attr name="alphaSeekBarProgress" format="integer" />
    <attr name="alphaSeekBarVertical" format="boolean" />
    <attr name="alphaSeekBarMaxProgress" format="integer" />
    <attr name="alphaSeekBarBorderColor" format="color" />
    <attr name="alphaSeekBarBorderSize" format="dimension" />
</declare-styleable>
```

### Gradle:

<a href="https://jitpack.io/#rtugeek/colorseekbar">![Release](https://jitpack.io/v/rtugeek/colorseekbar.svg)</a>
<a href="https://android-arsenal.com/api?level=14">![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)</a>
<a href="https://android-arsenal.com/details/1/3118">![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ColorSeekBar-green.svg?style=true)</a>

Step 1. Add the JitPack repository in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Step 2. Add the dependency

```
implementation 'com.github.rtugeek:colorseekbar:2.0.1'
```

## Usage

XML

```xml
<com.rtugeek.android.colorseekbar.ColorSeekBar 
    android:id="@+id/colorSeekBar"
    android:layout_width="match_parent"
    app:colorSeeds="@array/material_colors"
    android:layout_height="wrap_content" />

<com.rtugeek.android.colorseekbar.AlphaSeekBar 
    android:id="@+id/alphaSeekBar"
    android:layout_width="match_parent" 
    app:colorSeeds="@array/material_colors"
    android:layout_height="wrap_content" />
```

Kotlin

```kotlin
colorSeekBar.maxProgress = 1000
colorSeekBar.progress = 50
colorSeekBar.borderColor = Color.BLACK
colorSeekBar.borderRadius = 10
colorSeekBar.borderSize = 10
colorSeekBar.thumbDrawer = DefaultThumbDrawer(30,Color.WHITE,Color.BLUE)
colorSeekBar.isVertical = false
colorSeekBar.barHeight = 10
//lock thumb
colorSeekBar.isEnabled =false


alphaSeekBar.maxProgress = 1000
alphaSeekBar.borderColor = Color.BLACK
alphaSeekBar.borderRadius = 10
alphaSeekBar.borderSize = 10
alphaSeekBar.thumbDrawer = DefaultThumbDrawer(30,Color.WHITE,Color.BLUE)
alphaSeekBar.isVertical = false
alphaSeekBar.barHeight = 10
```

Listener

```java
colorSeekBar.setOnColorChangeListener { progress, color ->
    Log.i("TAG", "===progress:$progress-color:$color===")
}

alphaSeekBar.setOnAlphaChangeListener { progress, alpha ->
    Log.i("AlphaSeekBarFragment", "===progress:$progress-alpha:$alpha===")
}
```

Thumb Drawer
- com.rtugeek.android.colorseekbar.thumb.DefaultThumbDrawer
- com.rtugeek.android.colorseekbar.thumb.DrawableThumbDrawer - Load thumb from drawable resource
- com.rtugeek.android.colorseekbar.thumb.ThumbDrawer - Implement this interface if you want to customize thumb

## Vertical Bar

```xml

<com.rtugeek.android.colorseekbar.ColorSeekBar 
    android:id="@+id/colorSlider"
    android:layout_width="match_parent" 
    app:colorSeeds="@array/material_colors"
    app:colorSeekBarVertical="true" 
    android:layout_height="wrap_content" />
```

<img src="https://github.com/rtugeek/ColorSeekBar/blob/master/screenshot/vertical.jpg" width="300" />

**Spread the word**

<a href="https://twitter.com/intent/tweet?text=Check%20out%20the%20ColorSeekBar%20library%20on%20Github:%20https://github.com/rtugeek/ColorSeekBar/" target="_blank" title="share to twitter" style="width:100%"><img src="https://github.com/PhilJay/MPAndroidChart/blob/master/design/twitter_icon.png" title="Share on Twitter" width="35" height=35 />
<a href="https://plus.google.com/share?url=https://github.com/rtugeek/ColorSeekBar/" target="_blank" title="share to Google+" style="width:100%"><img src="https://github.com/PhilJay/MPAndroidChart/blob/master/design/googleplus_icon.png" title="Share on Google+" width="35" height=35 />
<a href="https://www.facebook.com/sharer/sharer.php?u=https://github.com/rtugeek/ColorSeekBar/" target="_blank" title="share to facebook" style="width:100%"><img src="https://github.com/PhilJay/MPAndroidChart/blob/master/design/facebook_icon.png" title="Share on Facebook" width="35" height=35 />

## License

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

    Copyright (C) 2004 Leon Fu <rtugeek@gmail.com>

    Everyone is permitted to copy and distribute verbatim or modified
    copies of this license document, and changing it is allowed as long
    as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

     0. You just DO WHAT THE FUCK YOU WANT TO.
