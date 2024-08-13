package org.pytorch.demo.objectdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button bt;
Animation scale_up,scale_down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bt=findViewById(R.id.button8);
         scale_up= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scale_down= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        bt.setOnTouchListener(new View.OnTouchListener() {
                                  @Override
                                  public boolean onTouch(View view, MotionEvent motionEvent) {
                                      if(motionEvent.getAction()==motionEvent.ACTION_UP){
                                          bt.startAnimation(scale_up);
                                          start(view);
                                      }
                                      else if(motionEvent.getAction()==motionEvent.ACTION_DOWN){
                                          bt.startAnimation(scale_down);
                                          start(view);

                                      }


                                      return true;
                                  }
                              }

        );
    }
    public void start(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();

    }

}