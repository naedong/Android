package kr.main.heydr.view.search.pharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
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

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.xml.parsers.ParserConfigurationException;

import kr.main.heydr.R;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.PharmacyVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacyDetailActivity extends AppCompatActivity {
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
    private String shopname;
    private TextView shop_name, shop_address;
    private double lat, lon;
    private String ploc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_detail);
        pane = (SlidingPaneLayout) findViewById(R.id.spd);
        pane.setPanelSlideListener(new PaneListener());
        intent = getIntent();
        shopname = intent.getStringExtra("name");
        shop_address = findViewById(R.id.shop_address);
        shop_name = findViewById(R.id.shop_name);
        shop_name.setText(shopname);

        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        mContext = this;
        tmapview = new TMapView(mContext);

        tmapview.setSKTMapApiKey(mApiKey);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutTmap);

        tmapview.setCompassMode(false);
        tmapview.setIconVisibility(true);

        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        linearLayout.addView(tmapview);
        Timer timer =new Timer();

        retrofitAPI.getShopPharmacy(shopname).enqueue(new Callback<List<PharmacyVO>>() {
            @Override
            public void onResponse(Call<List<PharmacyVO>> call, Response<List<PharmacyVO>> response) {
                if (response.isSuccessful()) {
                    List<PharmacyVO> datat = response.body();
                    int e = 0;
                    for(PharmacyVO i : datat) {
                        ploc = i.getPloc();
                        lat =  i.getPlat();
                        lon = i.getPlong();
                    }
                    shop_address.setText(ploc);
                    tmapdata = new TMapData();

                    tmapdata.findAddressPOI(ploc, new TMapData.FindAddressPOIListenerCallback() {
                        @Override
                        public void onFindAddressPOI(ArrayList<TMapPOIItem> arrayList) {
                            for(TMapPOIItem i : arrayList) {

                                TMapPoint point = new TMapPoint(i.getPOIPoint().getLatitude(),
                                        i.getPOIPoint().getLongitude());
                                TMapMarkerItem item1 = new TMapMarkerItem();

                                Bitmap bitmap = null;
                                /* 핀 이미지 */
                                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                                item1.setTMapPoint(point);
                                item1.setName(i.getPOIName());
                                item1.setVisible(item1.VISIBLE);
                                item1.setPosition(0.5f,1.0f);

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
                                tmapview.setCenterPoint(i.getPOIPoint().getLongitude(),i.getPOIPoint().getLatitude(),true);
                                tmapview.setLocationPoint(i.getPOIPoint().getLongitude(), i.getPOIPoint().getLatitude());
                                Log.d("포이 아이템 값 확인 ","POI Name: " + i.getPOIName().toString() + ", " +
                                        "Address: " + i.getPOIAddress().replace("null", "")  + ", " +
                                        "Point: " + i.getPOIPoint().toString());
                            }
                        }
                    });

                }
                else {
                    Log.e("TAGGGAGAGA", "caalla"+call);
                }
            }

            @Override
            public void onFailure(Call<List<PharmacyVO>> call, Throwable t) {

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
        stopService(new Intent(PharmacyDetailActivity.this, PharmacyActivity.class));
        Intent intent = new Intent(PharmacyDetailActivity.this, PharmacyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

}