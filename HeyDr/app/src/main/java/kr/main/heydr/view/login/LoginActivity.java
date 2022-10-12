package kr.main.heydr.view.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kr.main.heydr.R;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.service.MyFirebaseMessagingService;
import kr.main.heydr.utils.PreferenceUtils;
import kr.main.heydr.view.login.sign.SignFragment;
import kr.main.heydr.view.main.MainActivity;

public class LoginActivity extends AppCompatActivity  {
    private OAuthLogin mOAuthLoginModule;
    private View loginButton, logoutButton;
    private EditText tx_id, tx_pass;
    private ImageView profileImage;
    private Intent intent;
    private IntentResource intentResource;
    private BottomNavigationView bottomNavigationView;
    private String nickname, image, email, password;
    private ImageView imageView;
    private TextView textView, login_tx;
    private ImageButton google_login;
    private int count = 0;
    private static String TAG = "LoginActiviy";
    private static OAuthLogin mOAuthLoginInstance;
    public static String accessToken = "";
    private OAuthLoginHandler oAuthLoginHandler;
    private Button bt_sign_up, bt_sign_in;
    private ImageButton qr_login;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Context mContext;
    private boolean sd;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FragmentTransaction fragmentTransaction;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.imageView);
        google_login = findViewById(R.id.google_login);
        bt_sign_up = findViewById(R.id.bt_sign_up);
        bt_sign_in = findViewById(R.id.bt_sign_in);
        login_tx = findViewById(R.id.login_tx);

        loginButton = findViewById(R.id.btn_kakao_login);
        logoutButton = findViewById(R.id.btn_logout);
        tx_id = findViewById(R.id.tx_id);
        tx_pass = findViewById(R.id.tx_pass);
        userChk();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // email addresses도 요청함
                .build();
        PreferenceUtils.init(getApplicationContext());
        PreferenceUtils.getConnected();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        PreferenceUtils.setConnected(true);

        findViewById(R.id.google_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setVisibility(View.GONE);
                google_login.setVisibility(View.GONE);
                tx_id.setVisibility(View.GONE);
                tx_pass.setVisibility(View.GONE);
                bt_sign_in.setVisibility(View.GONE);
                bt_sign_up.setVisibility(View.GONE);
                login_tx.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);
                PreferenceUtils.setConnected(false);
                Intent intent = new Intent(LoginActivity.this, googleLogin.class);
                startActivity(intent);
            }
        });

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mContext = this;


        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentView(1);
            }
        });



//
//        mOAuthLoginModule = OAuthLogin.getInstance();
//        mOAuthLoginModule.init(
//                getApplicationContext()
//                , getString(R.string.naver_client_id)
//                , getString(R.string.naver_client_secret)
//                , getString(R.string.naver_client_name)
//        );




        intent = new Intent(LoginActivity.this, MainActivity.class);
        intentResource = new IntentResource(intent);


//        naver_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("TatataTUoch" , "터치확인!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                Log.e("TAGGAGA", "token 확인 "+ mOAuthLoginModule.getAccessToken(getApplicationContext()));
//
//                if (!HasNaverSession()) {
//                    // 핸들러가 세션 없을 시 동작을 대체한다.
//                  OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
//                        @Override
//                        public void run(boolean success) {
//                            if (success) {
//                                //String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
//                                //String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
//                                //long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
//                                //String tokenType = mOAuthLoginInstance.getTokenType(mContext);
//                                //mOauthRT.setText(refreshToken);
//                                //mOauthExpires.setText(String.valueOf(expiresAt));
//                                //mOauthTokenType.setText(tokenType);
//                                //mOAuthState.setText(mOAuthLoginInstance.getState(mContext).toString());
//                                    Log.e("TAHGGAGAG", String.valueOf(success));
//                                RetrofitConfig retrofitConfig = RetrofitConfig.getInstance();
//                                RetrofitAPI retrofitAPI = retrofitConfig.naverLogin();
//                                mOAuthLoginModule = OAuthLogin.getInstance();
//                                String accessToken = mOAuthLoginModule.getAccessToken(getApplicationContext());
//                                String haedar = "Bearer" + accessToken;
//
//                                retrofitAPI.login(haedar).enqueue(new retrofit2.Callback<List<String>>() {
//                                    @Override
//                                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                                        if(response.isSuccessful()){
//                                            Log.e("TAGAGAG", String.valueOf(response.body()));
//                                            Log.e("TAGAGAG", response.message());
//                                            Log.e("TAGAGAG", String.valueOf(response.code()));
//
//                                        }
//                                        Log.e("TAGAGAG", String.valueOf(call));
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<List<String>> call, Throwable t) {
//                                        Log.e("TAGAGAG", String.valueOf(call));
//                                        Log.e("TAGAGAG", String.valueOf(t.fillInStackTrace()));
//                                        Log.e("TAGAGAG", t.getMessage());
//
//                                    }
//                                });
//                            } else {
//                                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
//                                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
//                                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
//                            }
//                        };
//                    };
//
//                    Log.e("TATATANOT HANDLER", "HANDEL가 없을겨여우 .");
//                } else if (HasNaverSession()) {
//                    mOAuthLoginModule = OAuthLogin.getInstance();
//
//
//                    if(accessToken != null && OAuthLoginState.OK.equals(mOAuthLoginModule.getState(getApplicationContext()))){
//                        ReqNHNUserInfo reqNHNUserinfo = new ReqNHNUserInfo();
//
//
//
//                    }
//                }
//                }
//


//                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
//                    @Override
//                    public void run(boolean success) {
//                        if (success) {
//                            accessToken = mOAuthLoginInstance.getAccessToken(getApplicationContext());
//                            Log.e("TatataTUoch" , "연동확인!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String header = "Bearer " + accessToken;
//                                    Map<String, String> requestHeaders = new HashMap<>();
//                                    requestHeaders.put("Authorization", header);
//                                    Log.e("TatataTUoch" , "연동확인!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                    String apiURL = "https://openapi.naver.com/v1/nid/me"; //엑세스 토큰으로 유저정보를 받아올 주소
//                                    String responseBody = get(apiURL, requestHeaders);
//                                    Log.d("responseBody 확인 ", responseBody); //주소로 얻은 유저정보 (제이슨)
//                                    NaverUserInfo(responseBody);
//                                }
//                            }).start();
//                        } else {
//                            String errorCode = mOAuthLoginInstance.getLastErrorCode(getApplicationContext()).getCode();
//                            String errorDesc = mOAuthLoginInstance.getLastErrorDesc(getApplicationContext());
//                            Toast.makeText(getApplicationContext(), "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                };
//                naver_login.setOAuthLoginHandler(mOAuthLoginHandler);
//            }

//                                       });


//
//        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
//            @Override
//            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
//                if(oAuthToken != null) {
//                    // TBD
//                }
//                if(throwable != null) {
//
//                }
//                updateKaKaoLoginUi();
//                return null;
//            }
//        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(getApplicationContext())) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, new Function2<OAuthToken, Throwable, Unit>() {
                        @Override
                        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                            if (oAuthToken != null) {
                                // TBD
                                Log.e("KAKAO TOKEN", "카카오 토근 문제!!!!!!!!!!!!!!!!");
                            }
                            if (throwable != null) {

                                Log.e("KAKAO TOKEN", "카카오 thowable 문제!!!!!!!!!!!!!!!!");
                            }
                            updateKaKaoLoginUi();

                            return null;
                        }
                    });


                } else {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, new Function2<OAuthToken, Throwable, Unit>() {
                        @Override
                        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                            if (oAuthToken != null) {
                                // TBD
                            }
                            if (throwable != null) {

                            }
                            updateKaKaoLoginUi();

                            return null;
                        }
                    });
                }
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                boolean isGoogleLoggedIn = googleSignInAccount != null;
                if (UserApiClient.getInstance() != null) {
                    UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {
                            updateKaKaoLoginUi();

                            return null;
                        }
                    });
                } else if(UserApiClient.getInstance() == null) {
                    mOAuthLoginModule.logout(getApplicationContext());
                    PreferenceUtils.setConnected(false);
                }
                else if (isGoogleLoggedIn){
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                    googleSignInClient.signOut();

                    PreferenceUtils.clearAll();
                    bt_sign_in.setVisibility(View.VISIBLE);
                    bt_sign_up.setVisibility(View.VISIBLE);
                    google_login.setVisibility(View.VISIBLE);
                    tx_id.setVisibility(View.VISIBLE);
                    login_tx.setVisibility(View.VISIBLE);
                    tx_pass.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.GONE);

                }
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_home:
                    intent = intentResource.setIntent(getApplicationContext(), 0);
                    startActivity(intent);

                    break;
            }

            return true;
        });
    }



//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.google_login:
//                signIn();
//
//                break;
//            // ...
//        }
////    }
////    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
//
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String personEmail = acct.getEmail();
//                String personId = acct.getId();
//
//
//                Log.d(TAG, "handleSignInResult:personName "+personName);
//                Log.d(TAG, "handleSignInResult:personGivenName "+personGivenName);
//                Log.d(TAG, "handleSignInResult:personEmail "+personEmail);
//                Log.d(TAG, "handleSignInResult:personId "+personId);
//                Log.d(TAG, "handleSignInResult:personFamilyName "+personFamilyName);
//            }
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
//
//        }
//    }

    private void userChk(){
        if(!TextUtils.isEmpty(PreferenceUtils.getNickname())){
            loginButton.setVisibility(View.GONE);
            google_login.setVisibility(View.GONE);
            tx_id.setVisibility(View.GONE);
            tx_pass.setVisibility(View.GONE);
            bt_sign_in.setVisibility(View.GONE);
            bt_sign_up.setVisibility(View.GONE);
            login_tx.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
        }
        else{
            bt_sign_in.setVisibility(View.VISIBLE);
            bt_sign_up.setVisibility(View.VISIBLE);
            google_login.setVisibility(View.VISIBLE);
            tx_id.setVisibility(View.VISIBLE);
            login_tx.setVisibility(View.VISIBLE);
            tx_pass.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
        }

    }

    private void updateKaKaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @SuppressLint("StringFormatInvalid")
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    FCMtoken();

                    PreferenceUtils.setNickname(user.getKakaoAccount().getProfile().getNickname());
                    PreferenceUtils.setProfile(user.getKakaoAccount().getProfile().getProfileImageUrl());
                    MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
                    //            Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getProfileImageUrl()).circleCrop().into(profileImage);
                    loginButton.setVisibility(View.GONE);
                    google_login.setVisibility(View.GONE);
                    tx_id.setVisibility(View.GONE);
                    tx_pass.setVisibility(View.GONE);
                    bt_sign_in.setVisibility(View.GONE);
                    bt_sign_up.setVisibility(View.GONE);
                    login_tx.setVisibility(View.GONE);
                    logoutButton.setVisibility(View.VISIBLE);
                } else {
                    PreferenceUtils.setConnected(true);
                    bt_sign_in.setVisibility(View.VISIBLE);
                    bt_sign_up.setVisibility(View.VISIBLE);
                    google_login.setVisibility(View.VISIBLE);
                    tx_id.setVisibility(View.VISIBLE);
                    login_tx.setVisibility(View.VISIBLE);
                    tx_pass.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.GONE);
                    tx_id.setText(null);
                    PreferenceUtils.clearAll();
                    //   profileImage.setImageBitmap(null);

                }
                return null;
            }
        });

    }

//    private void NaverUserInfo(String msg) {
//        JSONParser jsonParser = new JSONParser();
//
//        try {
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(msg);
//            String resultcode = jsonObject.get("resultcode").toString();
//            Log.d("resultcode 확인 ", resultcode);
//
//            String message = jsonObject.get("message").toString();
//            Log.d("message 확인 ", message);
//
//            if (resultcode.equals("00")) {
//                if (message.equals("success")) {
//                    JSONObject naverJson = (JSONObject) jsonObject.get("response");
//
//                    String id = naverJson.get("id").toString();
//                    String nickName = naverJson.get("nickname").toString();
//                    String profileImage = naverJson.get("profile_image").toString();
//                    String email = naverJson.get("email").toString();
//                    String name = naverJson.get("name").toString();
//
//                    Log.d("id 확인 ", id);
//                    Log.d("nickName 확인 ", nickName);
//                    Log.d("profileImage 확인 ", profileImage);
//                    Log.d("email 확인 ", email);
//                    Log.d("name 확인 ", name);
//                }
//            } else {
//                //Toast.makeText(getApplicationContext(),"로그인 오류가 발생했습니다.",Toast.LENGTH_SHORT).show();
//            }
//        } catch (JSONException | org.json.simple.parser.ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private static String get(String apiUrl, Map<String, String> requestHeaders) {
//        HttpURLConnection con = connect(apiUrl);
//        try {
//            con.setRequestMethod("GET");
//            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
//                con.setRequestProperty(header.getKey(), header.getValue());
//            }
//
//            int responseCode = con.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
//                return readBody(con.getInputStream());
//            } else { // 에러 발생
//                return readBody(con.getErrorStream());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("API 요청과 응답 실패", e);
//        } finally {
//            con.disconnect();
//        }
//    }
//
//    private static HttpURLConnection connect(String apiUrl) {
//        try {
//            java.net.URL url = new URL(apiUrl);
//            return (HttpURLConnection) url.openConnection();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
//        } catch (IOException e) {
//            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
//        }
//    }
//
//    private static String readBody(InputStream body) {
//        InputStreamReader streamReader = new InputStreamReader(body);
//
//        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
//            StringBuilder responseBody = new StringBuilder();
//
//            String line;
//            while ((line = lineReader.readLine()) != null) {
//                responseBody.append(line);
//            }
//
//            return responseBody.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
//        }
//    }
//    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//        // [START verify_with_code]
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        // [END verify_with_code]
//
//    }
    private void FragmentView(int fragment){

        //FragmentTransactiom를 이용해 프래그먼트를 사용합니다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment){
            case 1:
                // 첫번 째 프래그먼트 호출
                SignFragment fragment1 = new SignFragment();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;


        }

    }
    public void FCMtoken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {

                    public void onSuccess(String token) {
                        Log.d("MessageToken",token);
                    }
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "토큰 생성 실패", task.getException());
                            return;
                        }
                        // 새로운 토큰 생성 성공 시
                        String token = task.getResult();
                        Log.d("MessageToken",token);
                    }
                });
//        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
//            SendBird.registerPushTokenForCurrentUser(token,true, (pushTokenRegistrationStatus, e) -> {
//                if (e != null) {
//                    e.printStackTrace();
//                }
//            });
//        });
    }

}
