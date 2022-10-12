package kr.main.heydr.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.PulseRing;
import com.github.ybq.android.spinkit.style.RotatingCircle;

import kr.main.heydr.R;
import kr.main.heydr.view.main.MainActivity;
import kr.main.heydr.view.main.SplashActivity;

public class ProgerssActivity extends Dialog {
    private Animation rotate;
    private ImageView imageView;

    public ProgerssActivity(@NonNull Context context) {
        super(context);
        setContentView(R.layout.activity_progerss);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //title 없이
        AnimationSet set = new AnimationSet(true);
        imageView = findViewById(R.id.spin_kit);
        Animation myanim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        imageView.startAnimation(myanim);
        set.addAnimation(myanim);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);

    }

//        //라이브러리 로딩이미지 사용
//        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
//        RotatingCircle pulseRing = new RotatingCircle();
//        progressBar.setIndeterminateDrawable(pulseRing);



    }
