package kr.main.heydr.view.web;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kr.main.heydr.R;
import kr.main.heydr.view.chat.DocRoomActivity;
import kr.main.heydr.view.chat.HosRoomActivity;
import kr.main.heydr.view.main.MainActivity;

public class WebviewActivity extends AppCompatActivity {

    private WebView web_rtc;
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);
        web_rtc = findViewById(R.id.web_rtc);
        WebSettings webSettings = web_rtc.getSettings();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSafeBrowsingEnabled(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);



            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);



/*
    필요한 설정은 참고
    setJavaScriptEnabled(true);
    // javascript를 실행할 수 있도록 설정
    setJavaScriptCanOpenWindowsAutomatically (true);
    // javascript가 window.open()을 사용할 수 있도록 설정
    setBuiltInZoomControls(false);
    // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정
    setPluginState(WebSettings.PluginState.ON_DEMAND);
    // 플러그인을 사용할 수 있도록 설정
    setSupportMultipleWindows(false);
    // 여러개의 윈도우를 사용할 수 있도록 설정
    setSupportZoom(false);
    // 확대,축소 기능을 사용할 수 있도록 설정
    setBlockNetworkImage(false);
    // 네트워크의 이미지의 리소스를 로드하지않음
    setLoadsImagesAutomatically(true);
    // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
    setUseWideViewPort(true);
    // wide viewport를 사용하도록 설정
    setCacheMode(WebSettings.LOAD_NO_CACHE);
    // 웹뷰가 캐시를 사용하지 않도록 설정
*/

        web_rtc.setWebViewClient(new SslWebViewConnect());
//ssl 인증이 없는 경우 해결을 위한 부분
        web_rtc.setWebChromeClient(new WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());

            }

            /* 웹 뷰 띄우기 */
        });


        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        web_rtc.loadUrl("https://1e39-125-133-93-99.jp.ngrok.io/");
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }
    private boolean allPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void startCamera() {
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(WebviewActivity.this, MainActivity.class));
        Intent intent = new Intent(WebviewActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}
class SslWebViewConnect extends WebViewClient {

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed(); // SSL 에러가 발생해도 계속 진행!
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;//응용프로그램이 직접 url를 처리함
    }

}