package kr.main.heydr.view.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.main.heydr.R;
import kr.main.heydr.adapter.CourseAdapter;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.controller.vo.CourseModel;
import kr.main.heydr.utils.PreferenceUtils;
import kr.main.heydr.view.login.googleLogin;


public class MyFragment extends Fragment {
    private Intent intent;
    private IntentResource intentResource;
    private String[] numberword = {"지도", "WebSite", "병원 검색", "약 검색",  "약국 검색", "Login"};
    private int[] numberImage = {R.drawable.ic_baseline_map_24, R.drawable.ic_baseline_web_asset_24, R.drawable.ic_baseline_local_hospital_24
            , R.drawable.ic_baseline_medical_services_24, R.drawable.ic_baseline_local_pharmacy_24, R.drawable.ic_baseline_bluetooth_24};
    private GridView gridView;
    private TextView tx_hello;
    private Animation rotate;
    private ImageView spin_kit;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_my, container, false);
        intentResource = new IntentResource(intent);
        gridView = rootView.getRootView().findViewById(R.id.gridView);
        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();
        tx_hello = rootView.getRootView().findViewById(R.id.tx_hello);
        spin_kit = rootView.getRootView().findViewById(R.id.spin_kit1);
        frameLayout = rootView.getRootView().findViewById(R.id.main_lo);
        TranslateAnimation anim = new TranslateAnimation(0,0,0, -50);
        rotate = AnimationUtils.loadAnimation(getContext().getApplicationContext(),R.anim.rotate);
// 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬

        anim.setDuration(1000);
        Bundle extra = this.getArguments();
        if(extra != null) {
            extra = getArguments();
            String name = extra.getString("name");
            tx_hello.setText(name);
        }


        courseModelArrayList.add(new CourseModel("WebSite", R.drawable.ic_baseline_web_asset_24));
        courseModelArrayList.add(new CourseModel("병원 검색", R.drawable.ic_baseline_local_hospital_24));
        courseModelArrayList.add(new CourseModel("약국 검색", R.drawable.ic_baseline_local_pharmacy_24));




        CourseAdapter adapter = new CourseAdapter(getContext().getApplicationContext(), courseModelArrayList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        frameLayout.setVisibility(View.VISIBLE);
                        rotate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        spin_kit.startAnimation(rotate);
                        intent = intentResource.setIntent(getContext().getApplicationContext(), 8);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = intentResource.setIntent(getContext().getApplicationContext(), 7);
                       startActivity(intent);
                        break;
                    case 2:
                        intent = intentResource.setIntent(getContext().getApplicationContext(), 4);
                        startActivity(intent);
                        break;

                }
            }
        });

//        NumberAdapter numberAdapter = new NumberAdapter(getContext().getApplicationContext(), numberword, numberImage);
//        gridView.setAdapter(numberAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(getContext().getApplicationContext(), numberword[position] + "을 클릭하셨습니다.", Toast.LENGTH_LONG).show();
//                if (position == 0) {
//                    intent = intentResource.setIntent(getContext().getApplicationContext(), 0);
//                    startActivity(intent);
//                } else if (position == 1) {
////                    setContentView(FragmentWebviewBinding.inflate(getLayoutInflater()).getRoot());
////                    ft = getSupportFragmentManager().beginTransaction();
////                    WebFragment fragment1 = new WebFragment();
////                    ft.replace(R.id.webView, fragment1);
////                    ft.addToBackStack(null);
////                    ft.commit();
//                } else if (position == 2) {
//                    intent = intentResource.setIntent(getContext().getApplicationContext(), 7);
//                    startActivity(intent);
//
//                } else if (position == 3) {
//                    intent = intentResource.setIntent(getContext().getApplicationContext(), 4);
//                    startActivity(intent);
//
//               } else if (position == 4) {
//                   intent = intentResource.setIntent(getContext().getApplicationContext(), 4);
//                    startActivity(intent);
//
//                } else if (position == 5) {
//                    intent = intentResource.setIntent(getContext().getApplicationContext(), 4);
//                    startActivity(intent);
//
//                } else if (position == 6) {
//                    intent = intentResource.setIntent(getContext().getApplicationContext(), 8);
//                    startActivity(intent);
//
//                } else if (position == 7) {
//                    intent = intentResource.setIntent(getContext().getApplicationContext(), 9);
//                   startActivity(intent);
//
//                }
//            }
//        });

        return rootView;
    }
    List<String> loadData() {
        String name = PreferenceUtils.getNickname();
        Log.d("FRAGMENT", name + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        String address = PreferenceUtils.getProfile();
        List<String> str = new ArrayList<>();
        str.add(name);
        str.add(address);
        return str;
    }
}