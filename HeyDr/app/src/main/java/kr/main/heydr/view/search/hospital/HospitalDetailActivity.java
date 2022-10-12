package kr.main.heydr.view.search.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import kr.main.heydr.R;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.HospitalVO;
import kr.main.heydr.controller.vo.PharmacyVO;
import kr.main.heydr.view.search.hospital.frag.HosFragment;
import kr.main.heydr.view.search.hospital.frag.HosFragment1;
import kr.main.heydr.view.search.hospital.frag.HosFragment2;
import kr.main.heydr.view.search.pharmacy.PaneListener;
import kr.main.heydr.view.search.pharmacy.PharmacyActivity;
import kr.main.heydr.view.search.pharmacy.PharmacyDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalDetailActivity extends AppCompatActivity implements  View.OnClickListener {
    private static String mApiKey = "l7xx8fc6162789f747579d26c53413bd30f7";
    private static final String TAG = "DemoActivity";
    private SlidingPaneLayout pane;
    private TMapGpsManager tMapGPS = null;
    private BottomNavigationView bottomNavigationView;
    private Intent intent;
    private IntentResource intentResource;
    private TMapPoint tMapPoint;
    private TMapCircle tMapCircle;
    private TMapMarkerItem tMapMarkerItem;
    private Context mContext = null;
    private boolean m_bTrackingMode = true;
    private TMapData tmapdata = null;
    private TMapGpsManager tmapgps = null;
    private TMapView tmapview;
    private static int mMarkerID;
    private RetrofitConfig retrofitConfig;
    private RetrofitAPI retrofitAPI;
    private String shopname, otime, ctime, shopcate ;
    private TextView shop_name, shop_address, shop_time, shop_cate;
    private double lat, lon;
    private String ploc;
    private HosFragment hosFragment;
    private HosFragment1 hosFragment1;
    private HosFragment2 hosFragment2;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Button hos_review, hos_reserve, hos_det;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        pane = (SlidingPaneLayout) findViewById(R.id.hos_spd);
        pane.setPanelSlideListener(new PaneListener());
        intent = getIntent();
        shopname = intent.getStringExtra("name");
        hos_det = findViewById(R.id.hos_det);
        hos_reserve = findViewById(R.id.hos_reserve);
        hos_review = findViewById(R.id.hos_review);


        hos_review.setOnClickListener(this);
        hos_det.setOnClickListener(this);
        hos_reserve.setOnClickListener(this);

        mContext = this;
        tmapview = new TMapView(mContext);

        tmapview.setSKTMapApiKey(mApiKey);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutTmap1);

        tmapview.setCompassMode(false);
        tmapview.setIconVisibility(true);

        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        linearLayout.addView(tmapview);
        Timer timer = new Timer();
        Bundle bundle = new Bundle();
        bundle.putString("shopname", shopname);


        hosFragment = new HosFragment();
        hosFragment.setArguments(bundle);
        hosFragment1 = new HosFragment1();
        hosFragment1.setArguments(bundle);
        hosFragment2 = new HosFragment2();
        hosFragment2.setArguments(bundle);
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        retrofitAPI.getSelectHospital(shopname).enqueue(new Callback<List<HospitalVO>>() {
            @Override
            public void onResponse(Call<List<HospitalVO>> call, Response<List<HospitalVO>> response) {
                if (response.isSuccessful()) {
                    List<HospitalVO> datat = response.body();
                    int e = 0;
                    for (HospitalVO i : datat) {
                        ploc = i.getHloc();
                        shopcate = i.getHcate();
                        otime = i.getOtime();
                        ctime = i.getCtime();
                    }
//                    shop_address.setText(ploc);
//                    shop_cate.setText(shopcate);
//                    shop_time.setText(otime+" ~ "+ctime);

                    tmapdata = new TMapData();

                    tmapdata.findAddressPOI(ploc, new TMapData.FindAddressPOIListenerCallback() {
                        @Override
                        public void onFindAddressPOI(ArrayList<TMapPOIItem> arrayList) {
                            for (TMapPOIItem i : arrayList) {

                                TMapPoint point = new TMapPoint(i.getPOIPoint().getLatitude(),
                                        i.getPOIPoint().getLongitude());
                                TMapMarkerItem item1 = new TMapMarkerItem();

                                Bitmap bitmap = null;
                                /* 핀 이미지 */
                                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                                item1.setTMapPoint(point);
                                item1.setName(i.getPOIName());
                                item1.setVisible(item1.VISIBLE);
                                item1.setPosition(0.5f, 1.0f);

                                item1.setIcon(bitmap);
                                /* 핀 이미지 */
                                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                                item1.setCalloutTitle(i.getPOIName());
                                item1.setCanShowCallout(true);
                                item1.setAutoCalloutVisible(true);

                                /* 풍선 안 우측버튼 */
                                Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);

                                item1.setCalloutRightButtonImage(bitmap_i);

                                tmapview.addMarkerItem("marker", item1);
                                tmapview.setCenterPoint(i.getPOIPoint().getLongitude(), i.getPOIPoint().getLatitude(), true);
                                tmapview.setLocationPoint(i.getPOIPoint().getLongitude(), i.getPOIPoint().getLatitude());
                                Log.d("포이 아이템 값 확인 ", "POI Name: " + i.getPOIName().toString() + ", " +
                                        "Address: " + i.getPOIAddress().replace("null", "") + ", " +
                                        "Point: " + i.getPOIPoint().toString());
                            }
                        }
                    });

                } else {
                    Log.e("TAGGGAGAGA", "caalla" + call);
                }
            }

            @Override
            public void onFailure(Call<List<HospitalVO>> call, Throwable t) {

            }
        });

        //      pane.openPane();
        TMapPoint tMapPoint;

//
//        mContext = this;
//
//        //Tmap 각종 객체 선언
//        tmapdata = new TMapData(); //POI검색, 경로검색 등의 지도데이터를 관리하는 클래스
//
//
//        tmapview.setSKTMapApiKey(mApiKey);
//        tmapview.setCompassMode(false);
//        tmapview.setIconVisibility(true);
//
//        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
//        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
//
//
//        tmapview.setTrackingMode(true);
//        tmapview.setSightVisible(true);
//
//        tmapdata.findAddressPOI("가산디지털역", arrayList -> {
//                for(TMapPOIItem i : arrayList) {
//
//                    TMapPoint point = new TMapPoint(i.getPOIPoint().getLatitude(),
//                            i.getPOIPoint().getLongitude());
//                    TMapMarkerItem item1 = new TMapMarkerItem();
//
//                    Bitmap bitmap = null;
//                    /* 핀 이미지 */
//                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);
//
//                    item1.setTMapPoint(point);
//                    item1.setName(i.getPOIName());
//                    item1.setVisible(item1.VISIBLE);
//                    item1.setPosition(0.5f,1.0f);
//
//                    item1.setIcon(bitmap);
//                    /* 핀 이미지 */
//                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);
//
//                    item1.setCalloutTitle(i.getPOIName());
//                    item1.setCanShowCallout(true);
//                    item1.setAutoCalloutVisible(true);
//
//                    /* 풍선 안 우측버튼 */
//                    Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//
//                    item1.setCalloutRightButtonImage(bitmap_i);
//
//                    String strID = String.format("Pmarker%d", mMarkerID++);
//
//                    tmapview.addMarkerItem(strID, item1);
//                    tmapview.setCenterPoint(i.getPOIPoint().getLongitude(),i.getPOIPoint().getLatitude(),true);
//                    tmapview.setLocationPoint(i.getPOIPoint().getLongitude(), i.getPOIPoint().getLatitude());
//                    Log.d("포이 아이템 값 확인 ","POI Name: " + i.getPOIName().toString() + ", " +
//                            "Address: " + i.getPOIAddress().replace("null", "")  + ", " +
//                            "Point: " + i.getPOIPoint().toString());
//                }
//            });
//
//


        pane.setPanelSlideListener(new SlidingPaneLayout.SimplePanelSlideListener() {
            @Override
            public void onPanelOpened(View panel) {
                super.onPanelOpened(panel);
            }

            @Override
            public void onPanelClosed(View panel) {
                super.onPanelClosed(panel);
            }
        });


    }
//
//    private void getSet(){
//
//        try {
//
//            for(int i = 0; i < 1 ; i++){
//
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//
//        TMapPoint point = new TMapPoint(lat, lon);
//        TMapMarkerItem item1 = new TMapMarkerItem();
//
//        Bitmap bitmap = null;
//        /* 핀 이미지 */
//        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);
//
//        item1.setTMapPoint(point);
//        item1.setName(shopname);
//        item1.setVisible(item1.VISIBLE);
//        item1.setPosition(0.5f,1.0f);
//
//        item1.setIcon(bitmap);
//        /* 핀 이미지 */
//        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);
//
//        item1.setCalloutTitle(shopname);
//        item1.setCanShowCallout(true);
//        item1.setAutoCalloutVisible(true);
//
//        /* 풍선 안 우측버튼 */
//        Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//
//        item1.setCalloutRightButtonImage(bitmap_i);
//
//        tmapview.addMarkerItem("Pmarker", item1);
//
//        tmapview.setCenterPoint(lon, lat);
//        tmapview.setLocationPoint(lon, lat);
//
//    }

    private void initMap() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(HospitalDetailActivity.this, HospitalActivity.class));
        Intent intent = new Intent(HospitalDetailActivity.this, HospitalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hos_det:
                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.hosframeLayout, hosFragment).commitAllowingStateLoss();
                break;
            case R.id.hos_review:

                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.hosframeLayout, hosFragment1).commitAllowingStateLoss();
                break;
            case R.id.hos_reserve:
                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.hosframeLayout, hosFragment2).commitAllowingStateLoss();
                break;

        }
    }
}
