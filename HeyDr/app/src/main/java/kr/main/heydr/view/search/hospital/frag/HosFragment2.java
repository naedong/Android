package kr.main.heydr.view.search.hospital.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.adapter.ListViewAdapter;
import kr.main.heydr.adapter.ListViewAdapter1;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.ReserveVO;
import kr.main.heydr.controller.vo.ReviewVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HosFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HosFragment2 extends Fragment {
    private RetrofitConfig retrofitConfig;
    private RetrofitAPI retrofitAPI;
    private ListView listView;
    private ArrayList<ReviewVO> list;
    private String shopname, otime, ctime, shopcate , ploc, tel;
    final ListViewAdapter1 listViewAdapter1 = new ListViewAdapter1();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HosFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HosFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static HosFragment2 newInstance(String param1, String param2) {
        HosFragment2 fragment = new HosFragment2();
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
        View view = inflater.inflate(R.layout.fragment_hos2, container, false);

        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        listView = view.findViewById(R.id.review_list);

        shopname = this.getArguments().getString("shopname");
        retrofitAPI.getReserveHospital(shopname).enqueue(new Callback<List<ReserveVO>>() {
            @Override
            public void onResponse(Call<List<ReserveVO>> call, Response<List<ReserveVO>> response) {
                list = new ArrayList<>();
                if (response.isSuccessful()) {
                    List<ReserveVO> datat = response.body();
                    int e = 0;
                    Log.e("tatatata", datat.toString());
                    if(datat != null) {
                        for (ReserveVO i : datat) {
                            Log.e("TTATATAT:",i.getRdate()+"");
                            Log.e("TTATATAT:",i.getRtime()+"");
                            Log.e("TTATATAT:",i.getReservNum()+"");
                            listViewAdapter1.addItem(i.getReservNum(),i.getRdate(),i.getRtime());
                        }
                    }
                    getSet();



                } else {

                }
            }


            @Override
            public void onFailure(Call<List<ReserveVO>> call, Throwable t) {

            }
        });



        return view;
    }
    public void getSet(){

        listView.setAdapter(listViewAdapter1);

    }
}