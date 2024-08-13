package org.pytorch.demo.objectdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstActivity_1 extends AppCompatActivity {
    private static final long ANIMATION_DURATION = 2000;
    private static final long DELAY_TIME = 1000;
    private TextView textView;
    private ImageView logo;
    Animation Splash_top,Splash_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first1);

        textView = findViewById(R.id.textView);
        logo=findViewById(R.id.imageView3);

        Splash_top = AnimationUtils.loadAnimation(this, R.anim.splash_top);
        Splash_bottom = AnimationUtils.loadAnimation(this, R.anim.splash_bottom);
        logo.setAnimation(Splash_top);
        textView.setAnimation(Splash_bottom);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Code here
                Intent myIntent = new Intent(FirstActivity_1.this,HomeActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        },2500);
    }

}