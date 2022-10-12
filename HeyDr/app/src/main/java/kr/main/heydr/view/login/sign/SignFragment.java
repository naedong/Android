package kr.main.heydr.view.login.sign;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.main.heydr.R;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  TextView tx_ch;
    private EditText tx_sign;
    private Button check_id;

    public SignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignFragment newInstance(String param1, String param2) {
        SignFragment fragment = new SignFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign, container, false);
        tx_sign = view.findViewById(R.id.sign_id);
         check_id = view.findViewById(R.id.check_id);

        Log.e("TATAT", tx_sign.getText().toString());
         tx_ch = view.findViewById(R.id.textView4);

        check_id.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        RetrofitConfig retrofitConfig = RetrofitConfig.getInstance();
        RetrofitAPI retrofitAPI = retrofitConfig.getRetrofit();
        Log.e("TATAT", "클릭 이벤트1!!!!!!!!!!!!!!!!!!!!!!");
        switch(v.getId())
        {
            case R.id.check_id:
            {
                EditText tx = v.findViewById(R.id.sign_id);
                retrofitAPI.IdChk("xman").enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.e("TATAT", "xamn 연동확인!!!!!!!!!!");
                        if(response.isSuccessful()){
                            int i = response.body();
                                if( i == 1){
                                    tx_ch.setText("성공");
                                }
                                else {
                                    tx_ch.setText("실패");
                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("TATAㄻㄴㄹT", "xamn 연동실패!!!!!!!!!!"+t.getMessage());
                        Log.e("TATAㄻㄴㄹT", "xamn 연동실패!!!!!!!!!!"+t.getStackTrace());
                    }
                });


            }

        }
    }
}