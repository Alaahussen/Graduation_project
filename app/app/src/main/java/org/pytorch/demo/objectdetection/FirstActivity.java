package org.pytorch.demo.objectdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    private TextView animatedText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button movingButtondeaf = findViewById(R.id.button);
        Button movingButton = findViewById(R.id.button2);
        Animation but1 = AnimationUtils.loadAnimation(this, R.anim.splash_top);
        Animation but2 = AnimationUtils.loadAnimation(this, R.anim.splash_top);
        movingButtondeaf.setAnimation(but1);
        movingButton.setAnimation(but2);

    }
    public void Deaf(View view) {
        Intent intent=new Intent(this,MainActivity2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    public void Non_Deaf(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
    @Override
    public void onBackPressed() {
        back(null);
    }
    public void back(View view) {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }


}