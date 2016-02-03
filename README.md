#ColorSeekBar


### ScreenShot:

 ![](https://github.com/rtugeek/ColorSeekBar/blob/master/screenshot/screenshot.gif)

### Attrs 
|attr|format|defalut|
|---|:---|:---:|
|colors|references||
|colorBarValue|integer|0|
|alphaBarValue|integer|o|
|maxValue|integer|100|
|bgColor|color|TRANSPARENT|
|barHeight|dimension|2dp|
|barMargin|dimension|5dp|
|thumbHeight|dimension|30dp|
|showAlphaBar|boolean|false|


 
### Gradle:

[ ![Download](https://api.bintray.com/packages/rtugeek/maven/ColorSeekBar/images/download.svg) ](https://bintray.com/rtugeek/maven/ColorSeekBar/_latestVersion)

```
  complie 'com.rtugeek.android:colorseekbar:1.0.0'
```
 
##  Usage 

XML
```xml
  <com.rtugeek.android.colorseekbar.ColorSeekBar
      android:id="@+id/colorSlider"
      android:layout_width="match_parent"
      app:colors="@array/material_colors"
      android:layout_height="wrap_content" />
```

JAVA
```java
  colorSeekBar.setMaxValue(100);
  colorSeekBar.setColors(R.array.material_colors); // material_colors is defalut included in res.color,just use it.
  colorSeekBar.setColorBarValue(10); //0 - maxValue
  colorSeekBar.setAlphaBarValue(10); //0-255
  colorSeekBar.setShowAlphaBar(true); 
  colorSeekBar.setBarHeight(5); //5dpi 
  colorSeekBar.setThumbHeight(30); //30dpi
  colorSeekBar.setBarMargin(10); //set the margin between colorBar and alphaBar 10dpi
```

Listener
```java

  colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarValue, int alphaBarValue, int color) {
                textView.setTextColor(color);
            }
  });
```


## License

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004
   
    Copyright (C) 2004 Sam Fu <rtugeek@gmail.com>
   
    Everyone is permitted to copy and distribute verbatim or modified
    copies of this license document, and changing it is allowed as long
    as the name is changed.
   
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
   
     0. You just DO WHAT THE FUCK YOU WANT TO.
