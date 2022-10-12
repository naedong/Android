package kr.main.heydr.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import kr.main.heydr.R;

public class SplashActivity extends AppCompatActivity {
    Animation anim_FadeIn;
    Animation anim_ball;
    ConstraintLayout constraintLayout;
    ImageView lcklockImageView;
    ImageView oImageView;
    ImageView faceRecgnitionImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        constraintLayout=findViewById(R.id.constraintLayout1);
        lcklockImageView= (ImageView) findViewById(R.id.lock_lck);
        oImageView= (ImageView) findViewById(R.id.lock_o);

        AnimationSet set = new AnimationSet(true);


        TranslateAnimation anim = new TranslateAnimation(0,0,0, -50);
        anim_FadeIn= AnimationUtils.loadAnimation(this,R.anim.anim_splash_fadein);
        anim_ball=AnimationUtils.loadAnimation(this,R.anim.anim_splash_ball);

        anim.setDuration(1000);


        anim_FadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    lcklockImageView.startAnimation(anim_FadeIn);
    oImageView.startAnimation(anim_ball);
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                startActivity(new Intent(SplashActivity.this, MainActivity.class)); //딜레이 후 시작할 코드 작성
//            }
//        }, 3000);// 0.6초 정도 딜레이를 준 후 시작

         lcklockImageView.setOnClickListener((View.OnClickListener) (v) -> {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        finish();
        startActivity(intent);

    });
    }


}
