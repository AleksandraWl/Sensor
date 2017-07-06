package com.example.przemcio.czujniki;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    SensorManager manager;
    TextView tv;
    private ImageView image;
    private Sensor ruch;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        tv.setText("");

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        for (Sensor s : manager.getSensorList(Sensor.TYPE_ALL)) {
            tv.setText(tv.getText() + s.getName() + "\n");
        }

        image = (ImageView) findViewById(R.id.imageView);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ruch = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), 0, null);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, ruch, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Sensor.TYPE_ACCELEROMETER == event.sensor.getType()) {/////////////
            float degree = Math.round(event.values[0]) * 10;
            //tx.setText(""+degree);


            RotateAnimation ra = new RotateAnimation(
                    currentDegree,
                    degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);
// how long the animation will take place
            ra.setDuration(210);
// set the animation after the end of the reservation status
            ra.setFillAfter(true);

// Start the animation
            image.startAnimation(ra);
            currentDegree = degree;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onStop() {

        super.onStop();
    }

}
