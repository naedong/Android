package kr.main.heydr.view.search.hospital.frag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.HospitalVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HosFragment extends Fragment {
    private RetrofitConfig retrofitConfig;
    private RetrofitAPI retrofitAPI;
    private TextView shop_name, shop_address, shop_time, shop_cate, shop_tel;
    private String shopname, otime, ctime, shopcate , ploc, tel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HosFragment newInstance(String param1, String param2) {
        HosFragment fragment = new HosFragment();
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
        View view = inflater.inflate(R.layout.fragment_hos, container, false);
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        shop_address = view.findViewById(R.id.hos_shop_address);
        shop_name = view.findViewById(R.id.hos_shop_name);
        shop_tel = view.findViewById(R.id.hos_shop_tel);

        shopname = this.getArguments().getString("shopname");
        shop_name.setText(shopname);
        shop_time = view.findViewById(R.id.hos_shop_time);
        shop_cate = view.findViewById(R.id.hos_shop_ginro);
        shop_tel = view.findViewById(R.id.hos_shop_tel);
        retrofitAPI.getSelectHospital(shopname).enqueue(new Callback<List<HospitalVO>>() {
            @Override
            public void onResponse(Call<List<HospitalVO>> call, Response<List<HospitalVO>> response) {
                if (response.isSuccessful()) {
                    List<HospitalVO> datat = response.body();
                    int e = 0;
                    for (HospitalVO i : datat) {
                        ploc = i.getHloc();
                        shopcate = i.getHcate();
                        otime = i.getOtime();
                        ctime = i.getCtime();
                        tel = i.getHtel();
                    }
                    shop_time.setText(otime+" ~ "+ctime);
                    shop_cate.setText(shopcate);
                    shop_address.setText(ploc);
                    shop_tel.setText(tel);
                } else {

                }
            }
            @Override
            public void onFailure (Call < List < HospitalVO >> call, Throwable t){

            }

                });
        return view;
    }
}