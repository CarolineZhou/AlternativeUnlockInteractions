package com.example.tamagotchi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    ImageView apple, drink, pancake, ice;
    ImageView imgDestination;
    Boolean inShower = false;
    Boolean inKitchen = false;
    Boolean correctFood = false;
    Boolean correctSoap = false;
    int amountOfFood = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apple = findViewById(R.id.apple);
        drink = findViewById(R.id.drink);
        pancake = findViewById(R.id.pancake);
        ice = findViewById(R.id.ice);

        apple.setOnTouchListener(this);
        drink.setOnTouchListener(this);
        pancake.setOnTouchListener(this);
        ice.setOnTouchListener(this);

        imgDestination = findViewById(R.id.character);
        imgDestination.setOnDragListener(this);
    }

    //When user press shower options
    public void showShowerOptions(View view)
    {
        inShower = true;
        inKitchen = false;
        ImageView op1 = (ImageView) findViewById(R.id.apple);
        op1.setImageResource(R.drawable.soap1);
        ImageView op2 = (ImageView) findViewById(R.id.drink);
        op2.setImageResource(R.drawable.soap2);
        ImageView op3 = (ImageView) findViewById(R.id.pancake);
        op3.setImageResource(R.drawable.soap3);
        ImageView op4 = (ImageView) findViewById(R.id.ice);
        op4.setImageResource(R.drawable.soap4);

    }

    public void showFoodOptions(View view)
    {
        inKitchen = true;
        inShower = false;
        ImageView op1 = (ImageView) findViewById(R.id.apple);
        op1.setImageResource(R.drawable.apple);
        ImageView op2 = (ImageView) findViewById(R.id.drink);
        op2.setImageResource(R.drawable.drink);
        ImageView op3 = (ImageView) findViewById(R.id.pancake);
        op3.setImageResource(R.drawable.pancake);
        ImageView op4 = (ImageView) findViewById(R.id.ice);
        op4.setImageResource(R.drawable.ice);

        LinearLayout foodOptions = (LinearLayout)findViewById(R.id.foodoptions);
        foodOptions.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(v);
        ClipData.Item item = new ClipData.Item(v.getTag().toString());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

        switch (v.getId()){
            case R.id.apple:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, mShadow, null, 0);
                } else {
                    v.startDrag(data, mShadow, null, 0);
                }
                break;

            case R.id.drink:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, mShadow, null, 0);
                } else {
                    v.startDrag(data, mShadow, null, 0);
                }
                break;
            case R.id.pancake:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, mShadow, null, 0);
                } else {
                    v.startDrag(data, mShadow, null, 0);
                }
                break;
            case R.id.ice:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, mShadow, null, 0);
                } else {
                    v.startDrag(data, mShadow, null, 0);
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event)
    {
        ImageView tama = (ImageView) findViewById(R.id.character);
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                tama.setImageResource(R.drawable.tama1);

                return true;
            case DragEvent.ACTION_DRAG_ENTERED:

                String clipData = event.getClipDescription().getLabel().toString();
                switch(clipData){
                    case "apple":
                        ((ImageView) v).setAlpha(155);
                        break;
                    case "drink":
                        ((ImageView) v).setAlpha(155);
                        break;
                    case "pancake":
                        ((ImageView) v).setAlpha(155);
                        break;
                    case "ice":
                        ((ImageView) v).setAlpha(155);
                        break;
                }
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                ((ImageView) v).setAlpha(255);

                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                // could change the text later
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                if(inKitchen){
                    if (event.getResult()) {
                        tama.setImageResource(R.drawable.tamabowing);

                        ((ImageView) v).setAlpha(255);
                        Toast toast1 = Toast.makeText(MainActivity.this, "Num num", Toast.LENGTH_SHORT);
                        View toastView = toast1.getView();

                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();

                        /*String clipData1 = event.getClipDescription().getLabel().toString();
                        System.out.println(clipData1);
                        if(clipData1.equals("apple")){
                            correctFood = true;
                            amountOfFood += 1;
                        }*/

                    } else {
                        ((ImageView) v).setAlpha(255);

                        Toast toast1 = Toast.makeText(MainActivity.this, "Nooooo", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();
                    }
                }else if (inShower){
                    if (event.getResult()) {
                        tama.setImageResource(R.drawable.tamabowing);

                        ((ImageView) v).setAlpha(255);
                        Toast toast1 = Toast.makeText(MainActivity.this, "All clean~", Toast.LENGTH_SHORT);
                        View toastView = toast1.getView();

                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();

                    } else {
                        ((ImageView) v).setAlpha(255);

                        Toast toast1 = Toast.makeText(MainActivity.this, "Nooooo", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();
                    }
                }

                return true;

            default:
                return false;
        }
    }
}
