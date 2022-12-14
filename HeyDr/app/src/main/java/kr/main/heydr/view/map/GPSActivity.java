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

    private static  String mApiKey = "APIKEY";
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
        stopService(new Intent(GPSActivity.this, MainActivity.class)); //??? ?????????????????? ??????????????? ?????? ?????? ?????????????????? ??????
        // Toast.makeText(WebViewPlayer.this, "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();   //????????? ?????????
        Intent intent = new Intent(GPSActivity.this, MainActivity.class); //?????? ?????????????????? ?????? ??????????????? ???????????? ????????? ??????
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //????????? ????????? ??????
        startActivity(intent);  //????????? ??????
        finish();   //?????? ???????????? ??????
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



        //Tmap ?????? ?????? ??????
        tmapdata = new TMapData(); //POI??????, ???????????? ?????? ?????????????????? ???????????? ?????????
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
                        /* ??? ????????? */
                        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                        item1.setTMapPoint(point);
                        item1.setName(i.getPOIName());
                        item1.setVisible(item1.VISIBLE);
                        item1.setPosition(0.5f,1.0f);

                        item1.setIcon(bitmap);
                        /* ??? ????????? */
                        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                        item1.setCalloutTitle(i.getPOIName());
                        item1.setCanShowCallout(true);
                        item1.setAutoCalloutVisible(true);

                        /* ?????? ??? ???????????? */
                        Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);

                        item1.setCalloutRightButtonImage(bitmap_i);

                        String strID = String.format("Pmarker%d", mMarkerID++);

                        tmapview.addMarkerItem(strID, item1);
                        mArrayMarkerID.add(strID);
                        tmapview.setCenterPoint(i.getPOIPoint().getLongitude(),i.getPOIPoint().getLatitude(),true);
                        tmapview.setLocationPoint(i.getPOIPoint().getLongitude(), i.getPOIPoint().getLatitude());
                        Log.d("?????? ????????? ??? ?????? ","POI Name: " + i.getPOIName().toString() + ", " +
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




    //3. ?????????????????? ??????, ?????? ????????????
    /* ?????? ????????? ?????? ?????? ?????? */
    public void convertToAddress() {
        //??????????????? ?????????, ???????????? ????????????
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("POI ?????? ??????");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
                            /* ??? ????????? */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setTMapPoint(point);
                            item1.setName(item.getPOIName());
                            item1.setVisible(item1.VISIBLE);
                            item1.setPosition(0.5f,1.0f);

                            item1.setIcon(bitmap);
                            /* ??? ????????? */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setCalloutTitle(item.getPOIName());
                            item1.setCanShowCallout(true);
                            item1.setAutoCalloutVisible(true);

                            /* ?????? ??? ???????????? */
                            Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);

                            item1.setCalloutRightButtonImage(bitmap_i);

                            String strID = String.format("Pmarker%d", mMarkerID++);

                            tmapview.addMarkerItem(strID, item1);
                            mArrayMarkerID.add(strID);
                            tmapview.setCenterPoint(item.getPOIPoint().getLongitude(),item.getPOIPoint().getLatitude(),true);


                            Log.d("???????????????", "POI Name: " + item.getPOIName().toString() + ", " +
                                    "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                                    "Point: " + item.getPOIPoint().toString());
                        }
                    }

                });
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
        tmapdata.findAroundNamePOI(point, "??????", 1, 5,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        for (int i = 0; i < poiItem.size(); i++) {

                            TMapPOIItem item = poiItem.get(i);
                            TMapPoint point = new TMapPoint(item.getPOIPoint().getLatitude(),
                                    item.getPOIPoint().getLongitude());
                            TMapMarkerItem item1 = new TMapMarkerItem();

                            Bitmap bitmap = null;
                            /* ??? ????????? */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setTMapPoint(point);
                            item1.setName(item.getPOIName());
                            item1.setVisible(item1.VISIBLE);
                            item1.setPosition(0.5f,1.0f);

                            item1.setIcon(bitmap);
                            /* ??? ????????? */
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_baseline_pin_drop_24);

                            item1.setCalloutTitle(item.getPOIName());
                            item1.setCanShowCallout(true);
                            item1.setAutoCalloutVisible(true);

                            /* ?????? ??? ???????????? */
                            Bitmap bitmap_i = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);

                            item1.setCalloutRightButtonImage(bitmap_i);

                            String strID = String.format("Pmarker%d", mMarkerID++);

                            tmapview.addMarkerItem(strID, item1);
                            mArrayMarkerID.add(strID);
                            tmapview.setCenterPoint(item.getPOIPoint().getLongitude(),item.getPOIPoint().getLatitude(),true);
                            Log.d("????????????","POI Name: " + item.getPOIName() + "," + "Address: "
                                    + item.getPOIAddress().replace("null", "")+ "Point"+item.getPOIPoint().toString());
                        }
                    }
                });
    }

    @Override
    public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {

        TMapPoint tMapPointStart = new TMapPoint(mylat, mylon);
        TMapPoint tMapPointEnd = new TMapPoint(tMapPoint.getLatitude(), tMapPoint.getLongitude()); // N????????????(?????????)
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
