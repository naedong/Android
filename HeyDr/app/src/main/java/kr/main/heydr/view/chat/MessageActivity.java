package kr.main.heydr.view.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import kr.main.heydr.R;
import kr.main.heydr.adapter.ChatAdapter;
import kr.main.heydr.controller.vo.MessageItem;
import kr.main.heydr.controller.vo.UserVO;
import kr.main.heydr.controller.vo.selectDTO;
import kr.main.heydr.service.MyFirebaseMessagingService;
import kr.main.heydr.utils.PreferenceUtils;
import kr.main.heydr.view.main.MainActivity;
import kr.main.heydr.view.video.VideoActivity;

public class MessageActivity extends AppCompatActivity {

    EditText et;
    ListView listView;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference userInfo;
    ArrayList<MessageItem> messageItems=new ArrayList<>();
    ChatAdapter adapter;
    ImageButton imageButton;
    //Firebase Database 관리 객체참조변수

    private static  MyFirebaseMessagingService myFirebaseMessagingService;
    //'chat'노드의 참조객체 참조변수
    DatabaseReference chatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PreferenceUtils.setConnected(true);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
            Log.d("FIREBASE 문제", "현재 접속하실 수 없습니다.");
        } else {
            mAuth.signInAnonymously();
        }

        imageButton = findViewById(R.id.imagebutton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, VideoActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //제목줄 제목글시를 닉네임으로(또는 채팅방)
//        getSupportActionBar().setTitle(G.nickName);

        et=findViewById(R.id.et);
        listView=findViewById(R.id.listview);
        adapter=new ChatAdapter(messageItems,getLayoutInflater());
        listView.setAdapter(adapter);


        //Firebase DB관리 객체와 'caht'노드 참조객체 얻어오기

        firebaseDatabase= FirebaseDatabase.getInstance();
        chatRef= firebaseDatabase.getReference("chat").child(selectDTO.choiceCate).child(selectDTO.choiceHos).child(selectDTO.choiceDoc);
        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                MessageItem messageItem= dataSnapshot.getValue(MessageItem.class);
                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                messageItems.add(messageItem);
                //리스트뷰를 갱신


                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void clickSend(View view) {

        //firebase DB에 저장할 값들( 닉네임, 메세지, 프로필 이미지URL, 시간)
        String nickName= PreferenceUtils.getNickname();
        String message= et.getText().toString();
        String pofileUrl= PreferenceUtils.getProfile();

        //메세지 작성 시간 문자열로..
        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16

        //firebase DB에 저장할 값(MessageItem객체) 설정
        MessageItem messageItem= new MessageItem(nickName,message,time,pofileUrl);
        //'char'노드에 MessageItem객체를 통해
        chatRef.push().setValue(messageItem);
        //EditText에 있는 글씨 지우기
        et.setText("");
        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
        myFirebaseMessagingService.SendFCM(nickName, message);
        //처음 시작할때 EditText가 다른 뷰들보다 우선시 되어 포커스를 받아 버림.
        //즉, 시작부터 소프트 키패드가 올라와 있음.

        //그게 싫으면...다른 뷰가 포커스를 가지도록
        //즉, EditText를 감싼 Layout에게 포커스를 가지도록 속성을 추가!![[XML에]
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(MessageActivity.this, MainActivity.class));
        Intent intent = new Intent(MessageActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

}