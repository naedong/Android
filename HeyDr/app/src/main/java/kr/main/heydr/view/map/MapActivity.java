package kr.main.heydr.view.map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapView;

import kr.main.heydr.R;
import kr.main.heydr.view.chat.DocRoomActivity;
import kr.main.heydr.view.chat.HosRoomActivity;
import kr.main.heydr.view.main.MainActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("l7xx8fc6162789f747579d26c53413bd30f7");
        linearLayoutTmap.addView( tMapView );

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                Intent intent = new Intent(MapActivity.this, GPSActivity.class);
                                startActivity(intent);
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                Intent intent = new Intent(MapActivity.this, GPSActivity.class);
                                startActivity(intent);
                            } else {
                                // No location access granted.
                                Toast.makeText(getApplicationContext(), "GPS를 이용할 수 없습니다. 권한 설정을 하십시오", Toast.LENGTH_LONG).show();
                            }
                        }
                );

        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(MapActivity.this, MainActivity.class));
        Intent intent = new Intent(MapActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}