package com.rtugeek.android.colorseekbardemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView)findViewById(R.id.textView);
        final ColorSeekBar colorSeekBar = (ColorSeekBar) findViewById(R.id.colorSlider);
        final CheckBox showAlphaCheckBox = (CheckBox) findViewById(R.id.checkBox);
        final SeekBar barHeightSeekBar = (SeekBar) findViewById(R.id.seekBar);
        final SeekBar thumbHeightSeekBar = (SeekBar) findViewById(R.id.seekBar2);

//        colorSeekBar.setMaxValue(100);
//        colorSeekBar.setColorBarValue(10);
//        colorSeekBar.setAlphaBarValue(10);
//        colorSeekBar.setShowAlphaBar(true);
//        colorSeekBar.setBarHeight(5);
//        colorSeekBar.setThumbHeight(30);
//        colorSeekBar.setBarMargin(10);

        colorSeekBar.setColors(R.array.material_colors);
        colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarValue, int alphaBarValue, int color) {
                textView.setTextColor(color);

                Log.i("ColorSeekBar","colorPosition:"+ colorBarValue +"-alphaPosition:"+ alphaBarValue);
                Log.i("ColorSeekBar","color:" + color);
            }
        });

        showAlphaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                colorSeekBar.setShowAlphaBar(isChecked);
            }
        });

        barHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colorSeekBar.setBarHeight((float) progress);
                ((TextView)findViewById(R.id.textView2)).setText("barHeight:"+ progress + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        thumbHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colorSeekBar.setThumbHeight((float) progress);
                ((TextView)findViewById(R.id.textView3)).setText("thumbHeight:"+ progress + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        textView.setTextColor(colorSeekBar.getColor());
    }
}
