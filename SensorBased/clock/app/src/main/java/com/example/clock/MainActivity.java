package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor rotationVectorSensor;
    boolean longHandSelected;
    boolean shortHandSelected;
    ObjectAnimator animator;
    long animatorTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*View light = findViewById(R.id.light);

        Animation circularLight = new CircularAnimation(light, 50);
        circularLight.setDuration(10000);
        light.startAnimation(circularLight);*/

        clockAnimationInitial();

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
                    clockAnimationPlay();
                } else if(orientations[2] > -20) {

                } else if(Math.abs(orientations[2]) < 15) {
                    View view = getWindow().getDecorView();
                    view.setBackgroundColor(Color.BLACK);
                    animator.cancel();
                } else if(Math.abs(orientations[2]) < 10){
                    animator.cancel();
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

    public void clockAnimationInitial(){
        View star = findViewById(R.id.light);

        //pathInterpolator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            path.arcTo(-500f, -500f, 500f, 500f, 0f, 359f, true); //with first four parameters you determine four edge of a rectangle by pixel , and fifth parameter is the path'es start point from circle 360 degree and sixth parameter is end point of path in that circle
            animator = ObjectAnimator.ofFloat(star, View.X, View.Y, path); //at first parameter (view) put the target view
            animator.setDuration(5000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
        }else{

        }
    }

    public void clockAnimationStop(){
        if(animator != null){
            animatorTime = animator.getCurrentPlayTime();
            animator.cancel();
        }
    }

    public void clockAnimationPlay(){
        if(animator != null){
            animator.start();
            animator.setCurrentPlayTime(animatorTime);
        }
    }

    public void clockAnimationReversePlay(){
        if(animator != null){
            animator.reverse();
            animator.start();
            animator.setCurrentPlayTime(animatorTime);
        }
    }


}
