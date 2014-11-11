package com.kapware.spinkitdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.kapware.spinkit.SpinKitView;

public class SpinKitDemo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_kit_demo);


        final SpinKitView spinKitView1 = (SpinKitView) findViewById(R.id.spinkit1);
        final SpinKitView spinKitView2 = (SpinKitView) findViewById(R.id.spinkit2);

        spinKitView1.spin();
        spinKitView2.spin();

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                spinKitView1.setProgress((float)progress / seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
