package kr.main.heydr.view.chat;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.main.heydr.R;
import kr.main.heydr.controller.vo.UserVO;
import kr.main.heydr.utils.PreferenceUtils;

public class ChatActivity extends AppCompatActivity {

    private EditText etName;
    private CircleImageView ivProfile;
    private Uri imgUri;//선택한 프로필 이미지 경로 Uri
    boolean isFirst= true; //앱을 처음 실행한 것인가?
    boolean isChanged= false; //프로필을 변경한 적이 있는가?
    Button move_btn;
    TextView ip_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        etName=findViewById(R.id.et_name);
        ivProfile=findViewById(R.id.iv_profile);
        move_btn = findViewById(R.id.move_btn);
        PreferenceUtils.setConnected(true);

        ip_save = findViewById(R.id.ip_save);
        //폰에 저장되어 있는 프로필 읽어오기

        if(!TextUtils.isEmpty(PreferenceUtils.getNickname())){
            etName.setText(PreferenceUtils.getNickname());
            Picasso.get().load(PreferenceUtils.getProfile()).into(ivProfile);
            //처음이 아니다, 즉, 이미 로그인을 했다.
            isFirst=false;
        }

        move_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //바꾼것도 없고, 처음 접속도 아니고..
                if(!isChanged && !isFirst){
                    //ChatActivity로 전환
                    Intent intent= new Intent(ChatActivity.this, MessageActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //1. save작업
                    saveData();
                }
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent= new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        onActivityResult(10,10, intent);
                        launcher.launch(intent);
            }
        });
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {

                            Intent intent = result.getData();
                            imgUri = intent.getData();
                            //Glide.with(this).load(imgUri).into(ivProfile);
                            //Glide는 이미지를 읽어와서 보여줄때 내 device의 외장메모리에 접근하는 퍼미션이 요구됨.
                            //(퍼미션이 없으면 이미지가 보이지 않음.)
                            //Glide를 사용할 때는 동적 퍼미션 필요함.

                            //Picasso 라이브러리는 퍼미션 없어도 됨.
                        PreferenceUtils.setProfile(String.valueOf(imgUri));
                        Picasso.get().load(imgUri).into(ivProfile);

                            //변경된 이미지가 있다.
                            isChanged=true;


                    }
                }
            });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    imgUri= data.getData();
                    //Glide.with(this).load(imgUri).into(ivProfile);
                    //Glide는 이미지를 읽어와서 보여줄때 내 device의 외장메모리에 접근하는 퍼미션이 요구됨.
                    //(퍼미션이 없으면 이미지가 보이지 않음.)
                    //Glide를 사용할 때는 동적 퍼미션 필요함.

                    //Picasso 라이브러리는 퍼미션 없어도 됨.
                    Picasso.get().load(imgUri).into(ivProfile);

                    //변경된 이미지가 있다.
                    isChanged=true;
                }
                break;
        }
    }

    void saveData(){
        //EditText의 닉네임 가져오기 [전역변수에]

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        },1000);

        UserVO.name = etName.getText().toString();

        //이미지를 선택하지 않았을 수도 있으므로
        if(imgUri==null) return;

        //Firebase storage에 이미지 저장하기 위해 파일명 만들기(날짜를 기반으로)
        SimpleDateFormat sdf= new SimpleDateFormat("yyyMMddhhmmss"); //20191024111224
        String fileName= sdf.format(new Date())+".png";

        //Firebase storage에 저장하기
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
        final StorageReference imgRef= firebaseStorage.getReference("profileImages/"+fileName);

        //파일 업로드
        UploadTask uploadTask=imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //이미지 업로드가 성공되었으므로...
                //곧바로 firebase storage의 이미지 파일 다운로드 URL을 얻어오기
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //파라미터로 firebase의 저장소에 저장되어 있는
                        //이미지에 대한 다운로드 주소(URL)을 문자열로 얻어오기
                        ;
                        FancyToast.makeText(ChatActivity.this, "프로필 저장 완료", Toast.LENGTH_SHORT).show();

                        //1. Firebase Database에 nickName, profileUrl을 저장
                        //firebase DB관리자 객체 소환
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        //'profiles'라는 이름의 자식 노드 참조 객체 얻어오기
                        DatabaseReference profileRef= firebaseDatabase.getReference("profiles");
                        UserVO.ip = ip_save.getText().toString();
                        //닉네임을 key 식별자로 하고 프로필 이미지의 주소를 값으로 저장
                        profileRef.child(PreferenceUtils.getNickname()).setValue(uri.toString());
                        UserVO.profile = uri.toString();
                        //저장이 완료되었으니 ChatActivity로 전환
                        Intent intent=new Intent(ChatActivity.this, MessageActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });
    }//saveData() ..

    //내 phone에 저장되어 있는 프로필정보 읽어오기

    }
