![ColorSeekbar](https://github.com/Tobaloidee/ColorSeekBar/blob/master/logo/colorseekbar-02.png)

### ScreenShot:

 ![](https://github.com/rtugeek/ColorSeekBar/blob/master/screenshot/screenshot.gif)

### Attrs
|attr|format|default|
|---|:---|:---:|
|colorSeeds|references||
|colorBarPosition|integer|0|
|alphaBarPosition|integer|0|
|maxPosition|integer|100|
|bgColor|color|TRANSPARENT|
|barHeight|dimension|2dp|
|barMargin|dimension|5dp|
|thumbHeight|dimension|30dp|
|showAlphaBar|boolean|false|
|isVertical|boolean|false|
|disabledColor|color|Color.GRAY|
|showThumb|boolean|true|
|barRadius|dimension|0px|



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
implementation 'com.github.rtugeek:colorseekbar:1.7.5'
```

##  Usage

XML
```xml
<com.rtugeek.android.colorseekbar.ColorSeekBar
  android:id="@+id/colorSlider"
  android:layout_width="match_parent"
  app:colorSeeds="@array/material_colors"
  android:layout_height="wrap_content" />
```

JAVA
```java
colorSeekBar.setMaxPosition(100);
colorSeekBar.setColorSeeds(R.array.material_colors); // material_colors is defalut included in res/color,just use it.
colorSeekBar.setColorBarPosition(10); //0 - maxValue
colorSeekBar.setAlphaBarPosition(10); //0 - 255
colorSeekBar.setShowAlphaBar(true);
colorSeekBar.setBarHeight(5); //5dpi
colorSeekBar.setThumbHeight(30); //30dpi
colorSeekBar.setBarMargin(10); //set the margin between colorBar and alphaBar 10dpi
```

Listener
```java
colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
        @Override
        public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
            textView.setTextColor(color);
            //colorSeekBar.getAlphaValue();
        }
});
```

## Vertical Bar
```xml
<com.rtugeek.android.colorseekbar.ColorSeekBar
  android:id="@+id/colorSlider"
  android:layout_width="match_parent"
  app:colorSeeds="@array/material_colors"
  app:isVertical="true"
  android:layout_height="wrap_content" />
```
 ![](https://github.com/rtugeek/ColorSeekBar/blob/master/screenshot/vertical.png)


## getColor() issue
Render flow:  
1.Activity->onCreate();  
2.Activity->onResume();  
3.ColorSeekBar->onMeasure();  
4.ColorSeekBar->onSizeChanged();  
5.ColorSeekBar->init();  
6.ColorSeekBar->onMeasure();  
7.ColorSeekBar->onDraw();  

getColor()/getColors()/getColorIndexPosition() do not work correct until onDraw() method invoked.
So, If you want to get color or something else form ColorSeekBar on Activity.onCreate() function, just do:
```java
    mColorSeekBar.setOnInitDoneListener(new ColorSeekBar.OnInitDoneListener() {
        @Override
        public void done() {
            mColorSeekBar.getColorIndexPosition(mColor);
            //mColorSeekBar.getColors();
            //mColorSeekBar.getColor();
        }
    });
```

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
