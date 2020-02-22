package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor rotationVectorSensor;
    ObjectAnimator clockAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockAnimation();

        SensorEventListener rvListener;

        sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);


        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //associate a listener with the rotation vector
        rvListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix, sensorEvent.values);

                // Remap coordinate system
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_X,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);

                // Convert to orientations IN RANDIANS!!
                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);

                // IN DEGREES!
                for(int i = 0; i < 3; i++) {
                    orientations[i] = (float)(Math.toDegrees(orientations[i]));
                }

                //implement animation of clock hand rotation and star rotation
                if(orientations[2] > 20) {
                    View view = getWindow().getDecorView();
                    view.setBackgroundColor(Color.CYAN);
                } else if(orientations[2] < -20) {
                    clockAnimation();
                } else if(Math.abs(orientations[2]) < 15) {
                    View view = getWindow().getDecorView();
                    view.setBackgroundColor(Color.BLACK);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        //register it
        sensorManager.registerListener(rvListener,
                rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void clockAnimation(){
        View star = findViewById(R.id.light);

        //pathInterpolator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            path.arcTo(-1000f, -100f, 1000f, 1500f, 270f, -180f, true);
            clockAnimator = ObjectAnimator.ofFloat(star, View.X, View.Y, path);
            clockAnimator.setDuration(10000);
            clockAnimator.start();
        }else{

        }
    }
}
