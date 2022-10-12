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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.DoctorVO;
import kr.main.heydr.controller.vo.HospitalVO;
import kr.main.heydr.controller.vo.selectDTO;
import kr.main.heydr.view.main.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocRoomActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_doc_room);
        mOriginTitle = getTitle().toString();
        mSlidingPaneLayout = findViewById(R.id.Doc_slidingPaneLayout);
        mListView = findViewById(R.id.Doc_list_View);
        mTextViewShow = findViewById(R.id.Doc_textView_show);
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();


        retrofitAPI.seletDoc(selectDTO.choiceHos).enqueue(new Callback<List<DoctorVO>>() {
            @Override
            public void onResponse(Call<List<DoctorVO>> call, Response<List<DoctorVO>> response) {
                if (response.isSuccessful()) {
                    List<DoctorVO> datat = response.body();
                    int e = 0;
                    for(DoctorVO i : datat) {
                        Log.e("TATATA", i.getDname());
                        String cate = i.getDname();
                        listA.add(i.getDname());
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
            public void onFailure(Call<List<DoctorVO>> call, Throwable t) {

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
        mListView.setOnItemClickListener(this::onItemClick);
        mSlidingPaneLayout.openPane();
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String text = (String) mListView.getAdapter().getItem(position);
        mShowTitle = text;
        selectDTO.choiceDoc = text;
        mTextViewShow.setText(text);
        mSlidingPaneLayout.closePane();
        Intent intent = new Intent(DocRoomActivity.this, MessageActivity.class);
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
        stopService(new Intent(DocRoomActivity.this, HosRoomActivity.class));
        Intent intent = new Intent(DocRoomActivity.this, HosRoomActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}