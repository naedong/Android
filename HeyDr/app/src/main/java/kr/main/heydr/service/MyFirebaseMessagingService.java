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
        Calendar calendar= Calendar.getInstance(); //?????? ????????? ????????? ?????? ??????
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
                            Log.e("TAGEGA",  "FCM ??????!!!!!!!!!!!!!");
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
                    Log.d("FCM Token", "????????? ????????? MY TOKEN"+task.getResult());
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

        //?????? ????????? ????????? ??????????????? ???????????????.
        Intent intent = new Intent(this, MainActivity.class);
        //?????? ?????? ????????? : ?????? ?????????????????????...
        String channel_id = "CHN_ID";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        //?????? ???????????? ????????? ??????. ?????????????????? ?????? ????????? uri ??????
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.haydrlogo)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000}) //????????? ?????? ?????? : 1??? ??????, 1??? ??????, 1??? ??????
                .setOnlyAlertOnce(true) //????????? ????????? ?????????.. : ?????? ?????? ?????? ??????
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //??????????????? ????????? ????????? ????????? ????????? ??? ?????? ????????????
            //????????? ???????????? ??????
            builder = builder.setContent(getCustomDesign(title, message));

          //  PendingIntent pendingIntent = PendingIntent.getActivity(this, alarmID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else { //????????? ?????? ???????????? ??????
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.haydrlogo); //????????? ??????????????? ????????? ?????? ????????? ????????????..
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //?????? ????????? ????????? ??????????????? ????????? ?????? ??????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "CHN_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //?????? ?????? !
        notificationManager.notify(0, builder.build());

    }
}


