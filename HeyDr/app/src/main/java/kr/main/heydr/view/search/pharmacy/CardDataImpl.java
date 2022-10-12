package kr.main.heydr.view.search.pharmacy;

import android.util.Log;


import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kr.main.heydr.R;
import kr.main.heydr.adapter.CardListItemAdapter;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.config.RetrofitConfig;
import kr.main.heydr.controller.vo.PharmacyVO;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardDataImpl implements ECCardData<String> {

    private String cardTitle;
    private Integer mainBackgroundResource;
    private Integer headBackgroundResource;
    private List<String> listItems;
    private RetrofitAPI retrofitAPI;
    private List<PharmacyVO> datat;
    private static final String baseUrl = "http://192.168.0.47:83/";
    public CardDataImpl(){}


    public CardDataImpl(String cardTitle, Integer mainBackgroundResource, Integer headBackgroundResource, List<String> listItems) {
        this.mainBackgroundResource = mainBackgroundResource;
        this.headBackgroundResource = headBackgroundResource;
        this.listItems = listItems;
        this.cardTitle = cardTitle;
    }

    @Override
    public Integer getMainBackgroundResource() {
        return mainBackgroundResource;
    }

    @Override
    public Integer getHeadBackgroundResource() {
        return headBackgroundResource;
    }

    @Override
    public List<String> getListItems() {
        return listItems;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public static List<ECCardData> generateExampleData() {
        List<ECCardData> list = new ArrayList<>();
        list.add(new CardDataImpl("논현동", R.drawable.hospi_gang, R.drawable.pharmacyman, createItemsList("논현")));
        list.add(new CardDataImpl("대치동", R.drawable.fix_hospital, R.drawable.pharmacywoman, createItemsList("대치")));
        list.add(new CardDataImpl("개포동", R.drawable.hosbackground, R.drawable.doctor, createItemsList1("개포")));
        list.add(new CardDataImpl("개포동", R.drawable.seoulasan, R.drawable.pharmacyman1, createItemsList("개포")));
        return list;
    }

    public static List<String> createItemsList(String address) {
        ArrayList<String> list = new ArrayList<>();
        List<String> lis = new ArrayList<>(Arrays.asList());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        RetrofitAPI retrofit1 = retrofit.create(RetrofitAPI.class);
        retrofit1.listPharmacy(address).enqueue(new Callback<List<PharmacyVO>>(){
            @Override
            public void onResponse(Call<List<PharmacyVO>> call, Response<List<PharmacyVO>> response) {
                List<PharmacyVO> datat;
                if (response.isSuccessful()) {
                    datat = response.body();
                    int e = 0;
                    for(PharmacyVO i : datat){
                        Log.e("CARDDTATLATALFDG", i.getPname());
                            list.add(i.getPname());

                        lis.addAll(Arrays.asList(list.get(e)));
                            e += 1;
                    }

                }
                else {
                    Log.e("TAGGGAGAGA", "caalla"+call);
                }
            }
            @Override
            public void onFailure(Call<List<PharmacyVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return list;
    }

    public static List<String> createItemsList1(String address) {
        ArrayList<String> list = new ArrayList<>();

                    list.addAll(Arrays.asList("대구", "대구", "대구", "대구", "대구", "대구", "대구"));

        return list;
    }
}