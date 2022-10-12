package kr.main.heydr.view.map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.LogManager;

import javax.xml.parsers.ParserConfigurationException;

import kr.main.heydr.R;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.view.chat.RoomActivity;
import kr.main.heydr.view.main.MainActivity;

public class GPSActivity extends Activity implements TMapGpsManager.onLocationChangedCallback, TMapView.OnLongClickListenerCallback {

    private static  String mApiKey = "l7xx8fc6162789f747579d26c53413bd30f7";
    // T Map View
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
    private TMapView tmapview = null;
    private static int mMarkerID;
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();

    private Double lat = null;
    private Double lon = null;
    private static double mylon;
    private static double mylat;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(GPSActivity.this, MainActivity.class)); //이 액티비티에서 종료되어야 하는 활동 종료시켜주는 함수
        // Toast.makeText(WebViewPlayer.this, "방송 시청이 종료되었습니다.", Toast.LENGTH_SHORT).show();   //토스트 메시지
        Intent intent = new Intent(GPSActivity.this, MainActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

    @Override
    public void onLocationChange(Location location) {
        if (m_bTrackingMode) {
            tmapview.setLocationPoint(location.getLongitude(), location.getLatitude());
            mylon = location.getLongitude();
            mylat = location.getLatitude();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivity);
        bottomNavigationView = findViewById(R.id.bottomMapNavi);


        mContext = this;



        //Tmap 각종 객체 선언
        tmapdata = new TMapData(); //POI검색, 경로검색 등의 지도데이터를 관리하는 클래스
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mapview);
        tmapview = new TMapView(this);

        linearLayout.addView(tmapview);
        tmapview.setSKTMapApiKey(mApiKey);

        tmapview.setCompassMode(false);
        tmapview.setIconVisibility(true);

        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);

        tmapgps = new TMapGpsManager(GPSActivity.this);
        tmapgps.setMinTime(100);
        tmapgps.setMinDistance(5);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);
        //tmapgps.setProvider(tmapgps.GPS_PROVIDER);
        tmapgps.OpenGps();


        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);

        intent = getIntent();
        if(!TextUtils.isEmpty(intent.getStringExtra("address"))){

            tmapdata.findAddressPOI(intent.getStringExtra("address"), new TMapData.FindAddressPOIListenerCallback() {
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

                        String strID = String.format("Pmarker%d", mMarkerID++);

                        tmapview.addMarkerItem(strID, item1);
                        mArrayMarkerID.add(strID);
                        tmapview.setCenterPoint(i.getPOIPoint().getLongitude(),i.getPOIPoint().getLatitude(),true);
                        tmapview.setLocationPoint(i.getPOIPoint().getLongitude(), i.getPOIPoint().getLatitude());
                        Log.d("포이 아이템 값 확인 ","POI Name: " + i.getPOIName().toString() + ", " +
                                "Address: " + i.getPOIAddress().replace("null", "")  + ", " +
                                "Point: " + i.getPOIPoint().toString());
                    }
                }
            });

        }



        intentResource = new IntentResource(intent);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.action_search_bus:
                    intent = intentResource.setIntent(getApplicationContext(), 11);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.bt_findfac:
                    getAroundBizPoi();
                    break;
                case R.id.bt_find:
                    convertToAddress();
                    break;
                case R.id.action_homi:
                    intent = intentResource.setIntent(getApplicationContext(), 0);
                    startActivity(intent);
                    finish();
                    break;

            }

            return true;
        });

    }




    //3. 주소검색으로 위도, 경도 검색하기
    /* 명칭 검색을 통한 주소 변환 */
    public void convertToAddress() {
        //다이얼로그 띄워서, 검색창에 입력받음
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("POI 통합 검색");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strData = input.getText().toString();
                TMapPoint point = tmapview.getLocationPoint();
                TMapData tmapdata = new TMapData();





                tmapdata.findAroundNamePOI(point ,strData, new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> arrayList) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            TMapPOIItem item = arrayList.get(i);


                            TMapPoint point = new TMapPoint(item.getPOIPoint().getLatitude(),
                                    item.getPOIPoint().getLongitude());
                            TMapMarkerItem item1 = new TMapMarkerItem();

                            Bitmap bitmap = null;
                            /* 핀 이미지 */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setTMapPoint(point);
                            item1.setName(item.getPOIName());
                            item1.setVisible(item1.VISIBLE);
                            item1.setPosition(0.5f,1.0f);

                            item1.setIcon(bitmap);
                            /* 핀 이미지 */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setCalloutTitle(item.getPOIName());
                            item1.setCanShowCallout(true);
                            item1.setAutoCalloutVisible(true);

                            /* 풍선 안 우측버튼 */
                            Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);

                            item1.setCalloutRightButtonImage(bitmap_i);

                            String strID = String.format("Pmarker%d", mMarkerID++);

                            tmapview.addMarkerItem(strID, item1);
                            mArrayMarkerID.add(strID);
                            tmapview.setCenterPoint(item.getPOIPoint().getLongitude(),item.getPOIPoint().getLatitude(),true);


                            Log.d("주소로찾기", "POI Name: " + item.getPOIName().toString() + ", " +
                                    "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                    "Point: " + item.getPOIPoint().toString());
                        }
                    }

                });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void getAroundBizPoi() {
        TMapData tmapdata = new TMapData();

        TMapPoint point = tmapview.getLocationPoint();
        Context context = this;
        tmapdata.findAroundNamePOI(point, "병원", 1, 5,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        for (int i = 0; i < poiItem.size(); i++) {

                            TMapPOIItem item = poiItem.get(i);
                            TMapPoint point = new TMapPoint(item.getPOIPoint().getLatitude(),
                                    item.getPOIPoint().getLongitude());
                            TMapMarkerItem item1 = new TMapMarkerItem();

                            Bitmap bitmap = null;
                            /* 핀 이미지 */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setTMapPoint(point);
                            item1.setName(item.getPOIName());
                            item1.setVisible(item1.VISIBLE);
                            item1.setPosition(0.5f,1.0f);

                            item1.setIcon(bitmap);
                            /* 핀 이미지 */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setCalloutTitle(item.getPOIName());
                            item1.setCanShowCallout(true);
                            item1.setAutoCalloutVisible(true);

                            /* 풍선 안 우측버튼 */
                            Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);

                            item1.setCalloutRightButtonImage(bitmap_i);

                            String strID = String.format("Pmarker%d", mMarkerID++);

                            tmapview.addMarkerItem(strID, item1);
                            mArrayMarkerID.add(strID);
                            tmapview.setCenterPoint(item.getPOIPoint().getLongitude(),item.getPOIPoint().getLatitude(),true);
                            Log.d("편의시설","POI Name: " + item.getPOIName() + "," + "Address: "
                                    + item.getPOIAddress().replace("null", "")+ "Point"+item.getPOIPoint().toString());
                        }
                    }
                });
    }

    @Override
    public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {

        TMapPoint tMapPointStart = new TMapPoint(mylat, mylon);
        TMapPoint tMapPointEnd = new TMapPoint(tMapPoint.getLatitude(), tMapPoint.getLongitude()); // N서울타워(목적지)
        new Thread(() -> {
            try {
                TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(2);
                tmapview.addTMapPolyLine("Line1", tMapPolyLine);

            }catch(Exception e) {
                e.printStackTrace();
            }
        }).start();



    }
}