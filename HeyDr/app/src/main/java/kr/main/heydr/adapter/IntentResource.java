package kr.main.heydr.adapter;

import android.content.Context;
import android.content.Intent;


import kr.main.heydr.view.chat.ChatActivity;
import kr.main.heydr.view.chat.RoomActivity;
import kr.main.heydr.view.login.LoginActivity;
import kr.main.heydr.view.login.googleLogin;
import kr.main.heydr.view.main.MainActivity;
import kr.main.heydr.view.map.MapActivity;
import kr.main.heydr.view.map.SearchActivity;
import kr.main.heydr.view.msg.MsgChatActivity;
import kr.main.heydr.view.mypage.MypageActivity;
import kr.main.heydr.view.search.hospital.HospitalActivity;
import kr.main.heydr.view.search.pharmacy.PharmacyActivity;
import kr.main.heydr.view.search.pharmacy.PharmacyDetailActivity;
import kr.main.heydr.view.web.WebviewActivity;

public class IntentResource {
    private Intent intent;

    public IntentResource(Intent intent) {
        this.intent = intent;
    }

    public Intent setIntent(Context context , int i) {
        switch (i){
            case 0:
                intent = new Intent(context, MainActivity.class);
                break;
            case 1:
                intent = new Intent(context, MapActivity.class);
                break;
            case 2:
                intent = new Intent(context, LoginActivity.class);
                break;
            case 3:
                intent = new Intent(context, MypageActivity.class);
                break;
            case 4:
                intent = new Intent(context, PharmacyActivity.class);
                break;
            case 5:
                intent = new Intent(context, PharmacyDetailActivity.class);
                break;
            case 6:
                intent = new Intent(context, ChatActivity.class);
                break;
            case 7:
                intent = new Intent(context, HospitalActivity.class);
                break;
            case 8:
                intent = new Intent(context, WebviewActivity.class);
                break;
            case 9:
                intent = new Intent(context, googleLogin.class);
                break;
            case 10:
                intent = new Intent(context, RoomActivity.class);
                break;
            case 11:
                intent = new Intent(context, SearchActivity.class);
                break;


        }

        return intent;
    }
}
