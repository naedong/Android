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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.HospitalVO;
import kr.main.heydr.controller.vo.selectDTO;
import kr.main.heydr.view.main.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HosRoomActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    private SlidingPaneLayout mSlidingPaneLayout;
    private ListView mListView;
    private TextView mTextViewShow ;
    private String mOriginTitle;
    private String mShowTitle;
    private ArrayList<String> mStrings = new ArrayList<>();
    private RetrofitAPI retrofitAPI;
    private RetrofitConfig retrofitConfig;
    private List<String> listA = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hos_room);
        mOriginTitle = getTitle().toString();
        mSlidingPaneLayout = findViewById(R.id.hos_slidingPaneLayout);
        mListView = findViewById(R.id.hos_list_View);
        mTextViewShow = findViewById(R.id.hos_textView_show);
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();


        retrofitAPI.seletCate(selectDTO.choiceCate).enqueue(new Callback<List<HospitalVO>>() {
            @Override
            public void onResponse(Call<List<HospitalVO>> call, Response<List<HospitalVO>> response) {
                if (response.isSuccessful()) {
                    List<HospitalVO> datat = response.body();
                    int e = 0;
                    for(HospitalVO i : datat) {
                        Log.e("TATATA", i.getHname());
                        String cate = i.getHname();
                        listA.add(i.getHname());
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
        Log.e("TATATA", listA.size()+"0????????");


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
        selectDTO.choiceHos = text;
        mTextViewShow.setText(text);
        mSlidingPaneLayout.closePane();
        Intent intent = new Intent(HosRoomActivity.this, DocRoomActivity.class);
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
        stopService(new Intent(HosRoomActivity.this, RoomActivity.class)); //??? ?????????????????? ??????????????? ?????? ?????? ?????????????????? ??????
        // Toast.makeText(WebViewPlayer.this, "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();   //????????? ?????????
        Intent intent = new Intent(HosRoomActivity.this, RoomActivity.class); //?????? ?????????????????? ?????? ??????????????? ???????????? ????????? ??????
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //????????? ????????? ??????
        startActivity(intent);  //????????? ??????
        finish();   //?????? ???????????? ??????
    }
}