package kr.main.heydr.view.search.hospital.frag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.adapter.ListViewAdapter;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.HospitalVO;
import kr.main.heydr.controller.vo.ReviewVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HosFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HosFragment1 extends Fragment {
    private RetrofitConfig retrofitConfig;
    private RetrofitAPI retrofitAPI;
    private ListView listView;
    private ArrayList<ReviewVO> list;
    private String shopname, otime, ctime, shopcate , ploc, tel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayAdapter<String> adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final ListViewAdapter listViewAdapter = new ListViewAdapter();
    public HosFragment1() {
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
    public static HosFragment1 newInstance(String param1, String param2) {
        HosFragment1 fragment = new HosFragment1();
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
        View view = inflater.inflate(R.layout.fragment_hos1, container, false);

        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        listView = view.findViewById(R.id.review_list);

        shopname = this.getArguments().getString("shopname");
        retrofitAPI.getHospitalReview(shopname).enqueue(new Callback<List<ReviewVO>>() {
            @Override
            public void onResponse(Call<List<ReviewVO>> call, Response<List<ReviewVO>> response) {
                list = new ArrayList<>();
                if (response.isSuccessful()) {
                    List<ReviewVO> datat = response.body();
                    int e = 0;
                    Log.e("tatatata", datat.toString());
                    if(datat != null) {
                        for (ReviewVO i : datat) {
                            Log.e("TTATATAT:",i.getRnum()+"");
                            Log.e("TTATATAT:",i.getTitle()+"");
                            Log.e("TTATATAT:",i.getCont()+"");
                            listViewAdapter.addItem(i.getTitle(),i.getRnum(),i.getCont(), i.getId());
                        }
                    }
                    getSet();



                } else {

                }
            }


            @Override
            public void onFailure(Call<List<ReviewVO>> call, Throwable t) {

            }
        });
        return view;
    }
    public void getSet(){

        listView.setAdapter(listViewAdapter);

    }
}