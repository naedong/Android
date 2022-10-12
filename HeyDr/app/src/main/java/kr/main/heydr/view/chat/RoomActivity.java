package kr.main.heydr.view.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.HospitalVO;
import kr.main.heydr.controller.vo.selectDTO;
import kr.main.heydr.view.main.MainActivity;
import kr.main.heydr.view.search.pharmacy.PaneListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    private SlidingPaneLayout mSlidingPaneLayout;
    private ListView mListView;
    private TextView mTextViewShow , fa, fa1, fa2;
    private String mOriginTitle;
    private String mShowTitle;
    private ArrayList<String> mStrings = new ArrayList<>();
    private RetrofitAPI retrofitAPI;
    private RetrofitConfig retrofitConfig;
    private List<String> listA = new ArrayList<String>();
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        mOriginTitle = getTitle().toString();
        mSlidingPaneLayout = findViewById(R.id.slidingPaneLayout);
        mListView = findViewById(R.id.listView);
        mTextViewShow = findViewById(R.id.textView_show);
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        frameLayout = findViewById(R.id.frames);
        fa = findViewById(R.id.fa1);
        fa1 = findViewById(R.id.fa2);
        fa2 = findViewById(R.id.fa3);


        retrofitAPI.seletHospital().enqueue(new Callback<List<HospitalVO>>() {
            @Override
            public void onResponse(Call<List<HospitalVO>> call, Response<List<HospitalVO>> response) {
                if (response.isSuccessful()) {
                    List<HospitalVO> datat = response.body();
                    int e = 0;
                    for(HospitalVO i : datat) {
                        Log.e("TATATA", i.getHcate());
                        String cate = i.getHcate();
                        listA.add(i.getHcate());
                        mStrings.add(cate);
                        Log.e("TATATA", listA.size()+"");
                        e += 1;
                    }
                    getSet();
                    Log.e("TATATA", listA.size()+"");
                }
                else {
                    Log.e("TAGGGAGAGA", "caalla"+call);

                }
            }

            @Override
            public void onFailure(Call<List<HospitalVO>> call, Throwable t) {

            }
        });
        Log.e("TATATA", listA.size()+"0인가??");


        mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelOpened(View panel) {
                super.onPanelOpened(panel);
                setTitle(mOriginTitle);
            }

            @Override
            public void onPanelClosed(View panel) {
                super.onPanelClosed(panel);
                setTitle(mShowTitle);
            }
        });


    }
    public void getSet(){
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mStrings));
        mListView.setOnItemClickListener(this);
        mSlidingPaneLayout.openPane();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String text = (String) mListView.getAdapter().getItem(position);
        mShowTitle = text;
        selectDTO.choiceCate = text;
        mTextViewShow.setText(text);
        mSlidingPaneLayout.closePane();
        Intent intent = new Intent(RoomActivity.this, HosRoomActivity.class);
        startActivity(intent);
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            if (mSlidingPaneLayout.isOpen()) {
                finish();
            } else {
                mSlidingPaneLayout.openPane();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(RoomActivity.this, MainActivity.class)); //이 액티비티에서 종료되어야 하는 활동 종료시켜주는 함수
        // Toast.makeText(WebViewPlayer.this, "방송 시청이 종료되었습니다.", Toast.LENGTH_SHORT).show();   //토스트 메시지
        Intent intent = new Intent(RoomActivity.this, MainActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}