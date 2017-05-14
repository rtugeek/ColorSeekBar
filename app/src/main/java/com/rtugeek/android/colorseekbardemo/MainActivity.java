package com.rtugeek.android.colorseekbardemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rtugeek.android.colorseekbar.ColorSeekBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ColorSeekBar mColorSeekBar;
    private SharedPreferences sp;
    private SharedPreferences.Editor spe;
    private static final String TAG = "ColorSeekBar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getPreferences(MODE_PRIVATE);
        spe = sp.edit();

        mColorSeekBar = (ColorSeekBar) findViewById(R.id.colorSlider);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final CheckBox showAlphaCheckBox = (CheckBox) findViewById(R.id.checkBox);
        final SeekBar barHeightSeekBar = (SeekBar) findViewById(R.id.seekBar);
        final SeekBar thumbHeightSeekBar = (SeekBar) findViewById(R.id.seekBar2);

//        mColorSeekBar.setAlphaBarPosition(10);
//        mColorSeekBar.setBarMargin(10);
//        mColorSeekBar.setBarHeight(5);
//        mColorSeekBar.setColor(0xffffff);
//        mColorSeekBar.setColorBarPosition(30);
        mColorSeekBar.setMaxPosition(100);
        mColorSeekBar.setShowAlphaBar(true);
        mColorSeekBar.setThumbHeight(30);
        mColorSeekBar.setColorSeeds(R.array.material_colors);

        mColorSeekBar.setOnInitDoneListener(new ColorSeekBar.OnInitDoneListener() {
            @Override
            public void done() {
                Log.i(TAG,"done!");
            }
        });

        mColorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                textView.setTextColor(mColorSeekBar.getColor());
                Log.i(TAG, "===colorPosition:" + colorBarPosition
                        + "-alphaPosition:" + alphaBarPosition
                        + "-ColorIndexPosition:" + mColorSeekBar.getColorIndexPosition(color)
                        + "-color:" + color + "===");
            }
        });


        showAlphaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mColorSeekBar.setShowAlphaBar(isChecked);
            }
        });



        barHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mColorSeekBar.setBarHeight((float) progress);
                ((TextView) findViewById(R.id.textView2)).setText("barHeight:" + progress + "dp");
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
                mColorSeekBar.setThumbHeight((float) progress);
                ((TextView) findViewById(R.id.textView3)).setText("thumbHeight:" + progress + "dp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mColorSeekBar.setColor(sp.getInt("color", 0));
        showAlphaCheckBox.setChecked(sp.getBoolean("showAlpha", false));

    }


    @Override
    protected void onStop() {
        super.onStop();
        spe.putInt("color", mColorSeekBar.getColor());
        spe.putBoolean("showAlpha", mColorSeekBar.isShowAlphaBar());
        spe.commit();
    }
}
