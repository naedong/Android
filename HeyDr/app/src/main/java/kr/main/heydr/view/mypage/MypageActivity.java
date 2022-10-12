package kr.main.heydr.view.mypage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

import kr.main.heydr.R;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.utils.PreferenceUtils;
import kr.main.heydr.view.chatbot.CHATBOTActivity;
import kr.main.heydr.view.main.MainActivity;

public class MypageActivity extends AppCompatActivity {
    private TextView tx_nickname;
    private TextView tx_email;
    private ImageView iv_image, imageView2;
    private Intent intent;
    private IntentResource intentResource;
    private BottomNavigationView bottomNavigationView;
    private Button chatbot;
    public void UserUpdate(){

        tx_nickname.setText(PreferenceUtils.getNickname());
        Glide.with(iv_image).load(PreferenceUtils.getProfile()).circleCrop().into(iv_image);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceUtils.setConnected(true);
        setContentView(R.layout.activity_mypage);
        imageView2 = findViewById(R.id.imageView2);
        tx_nickname = findViewById(R.id.tx_title);
        tx_email = findViewById(R.id.tx_subtitle);
        iv_image = findViewById(R.id.imageView);
        chatbot = findViewById(R.id.chatda);

        iv_image.setBackground(new ShapeDrawable(new OvalShape()));
        iv_image.setClipToOutline(true);
        UserUpdate();
        intent = new Intent(MypageActivity.this, MainActivity.class);
        intentResource = new IntentResource(intent);

        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity.this, CHATBOTActivity.class);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if (result.getResultCode() == RESULT_OK)
                        {

                            Intent intent = result.getData();
                            Uri imgUri =  intent.getData();
                            PreferenceUtils.setProfile(String.valueOf(imgUri));
                            Glide.with(iv_image).load(String.valueOf(imgUri)).circleCrop().into(iv_image);
                        }
                    }
                });

        iv_image.setOnClickListener(view -> {
            Log.e("IVPROFIELEEE","클릭해!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Intent intent= new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            onActivityResult(1,100, intent);
            launcher.launch(intent);
        });



        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.action_home:
                    intent = intentResource.setIntent(getApplicationContext(), 0);
                    startActivity(intent);
                    finish();
                    break;
            }

            return true;
        });
    }
}