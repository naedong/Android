package kr.main.heydr.view.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.sdk.common.KakaoSdk;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.Map;

import kr.main.heydr.R;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.adapter.PageAdapter;
import kr.main.heydr.databinding.ActivityMainBinding;

import kr.main.heydr.view.Fragment.MyFragment;
import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationBar;
    private ActivityMainBinding binding;
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
    private CircleIndicator3 mIndicator;
    private Intent intent;
    private IntentResource intentResource;
    private long backBtnTime = 0;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        KakaoSdk.init(MainActivity.this, "5dfa293c579530ff0bf93ad469907a45");



//        if(!TextUtils.isEmpty(intent.getStringExtra("name"))){
//            String name = intent.getStringExtra("name");
//            Bundle bundle = new Bundle();
//            bundle.putString("name",name);
//            new MyFragment().getArguments().putString("name", name);
//        }
        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new PageAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page, 0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(3);
        mPager.setOffscreenPageLimit(2);
        intentResource = new IntentResource(intent);
      //  sendPushTokenToDB();
     //   loadData();
    //    passPushTokenToServer();
        bottomNavigationBar = findViewById(R.id.bottomNavi);
        bottomNavigationBar.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_home:
                    intent = intentResource.setIntent(getApplicationContext(), 0);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_map:
                    intent = intentResource.setIntent(getApplicationContext(), 1);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_login:
                    intent = intentResource.setIntent(getApplicationContext(), 2);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_human:
                    intent = intentResource.setIntent(getApplicationContext(), 3);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.action_chat:
                    intent = intentResource.setIntent(getApplicationContext(), 10);
                    startActivity(intent);
                    finish();
                    break;
            }
            return true;
        });


        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position % num_page);
            }

        });


        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        mPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(1 * pageOffset + pageMargin);
                if (mPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationX(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

//        if (0 <= gapTime && 2000 >= gapTime) {
//            super.onBackPressed();
//        } else {
//            backBtnTime = curTime;
//            FancyToast.makeText(this, "한번 더 누르면 종료입니다.", Toast.LENGTH_LONG).show();
//        }
        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("종료")
                .setCancelable(false)
                .setMessage("종료하시겠습니까?")
                .setIcon(R.drawable.haydrlogo)
                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity(MainActivity.this);
                System.exit(0);
            }
            })
                .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        }).create()
          .show();


//    private void getHashKey() {
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("KeyHash", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
    }

    void passPushTokenToServer() {

//         FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
//         fUser.getIdToken(true)
//                 .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                     @Override
//                     public void onComplete(@NonNull Task<GetTokenResult> task) {
//                         if(task.isSuccessful()){
//                             Map<String, Object> map = new HashMap<>();
//                             GetTokenResult token = task.getResult();
//                            Log.e("Tag", (String) map.put("pushToekn",  String.valueOf(token.getToken())));
//                             FirebaseDatabase.getInstance().getReference().child("users")
//                                     .updateChildren(map);
//                             return;
//                         }
//                         else{
//
//
//                         }
        //             }
        //           });
    }

    private void sendPushTokenToDB() {
        //파이어베이스
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FIREBASE !!!!TTOEKEN", "getInstanceId failed", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        Map<String, Object> map = new HashMap<>();
                        map.put("fcmToken", token);
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        ;
                        DatabaseReference hatRef = firebaseDatabase.getReference();
                        hatRef.child("users").setValue(token);

                    }

                });
    }


}