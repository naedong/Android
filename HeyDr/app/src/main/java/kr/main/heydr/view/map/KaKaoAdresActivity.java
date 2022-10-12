package kr.main.heydr.view.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import kr.main.heydr.R;

public class KaKaoAdresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ka_kao_adres);

        WebView webView = findViewById(R.id.test_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new BridgeInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Android => Javascript 함수 호출
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
        // 최초 웹뷰 로드
        webView.loadUrl("FIREBASE WEBURL");
    }

    private class BridgeInterface {
        @JavascriptInterface
        public void processDATA(String data) {
            Intent intent = new Intent();
            intent.putExtra("data", data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
