package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout bgTela;

    private ImageView imSimbols;
    private TextView tvValorX;
    private TextView tvValorLux;

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private Sensor mLux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgTela = (ConstraintLayout) findViewById(R.id.Tela);

        imSimbols = (ImageView) findViewById(R.id.imageView);
        tvValorX = (TextView) findViewById( R.id.textViewProximity );
        tvValorLux = (TextView) findViewById( R.id.textViewLux );

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            mLux = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        mSensorManager.registerListener(new SensorProximity(), mProximity, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(new SensorLux(), mLux, SensorManager.SENSOR_DELAY_UI);

    }

    class SensorProximity implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float distance = event.values[0];
            tvValorX.setText(String.valueOf(distance));

            if (distance < 6){
                imSimbols.setImageResource(R.drawable.mute);
            }
            else{
                imSimbols.setImageResource(R.drawable.unmute);
            }
        }

    }

    class SensorLux implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float lux = event.values[0];
            tvValorLux.setText(String.valueOf(lux));

            if (lux > 10000) {
                bgTela.setBackgroundColor(Color.WHITE);
            } else {
                bgTela.setBackgroundColor(Color.BLACK);
            }
        }

    }
}