package kr.main.heydr.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

import kr.main.heydr.R;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.FCMBody;
import kr.main.heydr.controller.vo.FCMTo;
import kr.main.heydr.controller.vo.FCMData;
import kr.main.heydr.utils.PreferenceUtils;
import kr.main.heydr.view.main.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static String TAG = "FirebaseMessagingService";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getData().size() > 0) {
            showNotification(message.getData().get("title"), message.getData().get("body"));
        }
    }
    public void SendFCM(String title, String cont){

        FCMData fcmData = new FCMData();
        fcmData.setBody(cont);
        fcmData.setTitle(title);
        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16
        fcmData.setDate(time);
        FCMTo fcmTo = new FCMTo();
        fcmTo.setTo(PreferenceUtils.getToken());
        fcmTo.setData(fcmData);
        FCMBody fcmBody = new FCMBody();
        fcmBody.setBody(cont);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PreferenceUtils.setConnected(true);
        FirebaseUser user = mAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance("https://heydr-755f2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                RetrofitConfig retrofitConfig = RetrofitConfig.getInstance();
                retrofitConfig.fcmToken().sendFCM(fcmTo).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Log.e("TAGEGA",  "FCM 성공!!!!!!!!!!!!!");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });


    }

    public MyFirebaseMessagingService() {
        super();
        Task<String> token = FirebaseMessaging.getInstance().getToken();
        token.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    Log.d("FCM Token", "여기서 부터는 MY TOKEN"+task.getResult());
                    onNewToken(task.getResult());

                    PreferenceUtils.setConnected(true);
                    PreferenceUtils.setToken(task.getResult());
                    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance("https://heydr-755f2-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(PreferenceUtils.getNickname());
                    databaseReference.setValue(task.getResult()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.e("tatat","DADSdadsad");
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);


    }

    RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.activity_fcmactivity);
        remoteViews.setTextViewText(R.id.noti_title, title);
        remoteViews.setTextViewText(R.id.noti_message, message);
        remoteViews.setImageViewResource(R.id.logo, R.drawable.haydrlogo);
        return remoteViews;
    }
    public void showNotification(String title, String message) {

        //팝업 터치시 이동할 액티비티를 지정합니다.
        Intent intent = new Intent(this, MainActivity.class);
        //알림 채널 아이디 : 본인 하고싶으신대로...
        String channel_id = "CHN_ID";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        //기본 사운드로 알림음 설정. 커스텀하려면 소리 파일의 uri 입력
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.haydrlogo)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000}) //알림시 진동 설정 : 1초 진동, 1초 쉬고, 1초 진동
                .setOnlyAlertOnce(true) //동일한 알림은 한번만.. : 확인 하면 다시 울림
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //안드로이드 버전이 커스텀 알림을 불러올 수 있는 버전이면
            //커스텀 레이아웃 호출
            builder = builder.setContent(getCustomDesign(title, message));

          //  PendingIntent pendingIntent = PendingIntent.getActivity(this, alarmID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else { //아니면 기본 레이아웃 호출
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.haydrlogo); //커스텀 레이아웃에 사용된 로고 파일과 동일하게..
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //알림 채널이 필요한 안드로이드 버전을 위한 코드
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "CHN_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //알림 표시 !
        notificationManager.notify(0, builder.build());

    }
}


