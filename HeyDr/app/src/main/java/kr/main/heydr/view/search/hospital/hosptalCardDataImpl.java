package kr.main.heydr.view.search.hospital;

import android.util.Log;

import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kr.main.heydr.R;
import kr.main.heydr.controller.api.RetrofitAPI;
import kr.main.heydr.controller.vo.HospitalVO;
import kr.main.heydr.controller.vo.PharmacyVO;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class hosptalCardDataImpl implements ECCardData<String> {

    private String cardTitle;
    private Integer mainBackgroundResource;
    private Integer headBackgroundResource;
    private List<String> listItems;
    private RetrofitAPI retrofitAPI;
    private List<HospitalVO> datat;
    private static final String baseUrl = "http://192.168.0.47:83/";
    public hosptalCardDataImpl(){}

    public hosptalCardDataImpl(String cardTitle, Integer mainBackgroundResource, Integer headBackgroundResource, List<String> listItems) {
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

        list.add(new hosptalCardDataImpl("정형외과", R.drawable.jungang, R.drawable.borns, createItemsList("정형외과")));

        list.add(new hosptalCardDataImpl("이비인후과", R.drawable.sebrance1, R.drawable.ibein, createItemsList("이비인후과")));

        list.add(new hosptalCardDataImpl("", R.drawable.hosbackground, R.drawable.doctor, createItemsList1("")));

        list.add(new hosptalCardDataImpl("가정의학과", R.drawable.seoulasan, R.drawable.family, createItemsList("가정의학과")));



        list.add(new hosptalCardDataImpl("치과", R.drawable.ulje, R.drawable.teeh_main, createItemsList("치과")));
        list.add(new hosptalCardDataImpl("내과", R.drawable.bg_intro_gm, R.drawable.soa, createItemsList("내과")));
        list.add(new hosptalCardDataImpl("정신건강의학과", R.drawable.fix_hospital, R.drawable.brain, createItemsList("정신건강의학과")));
        list.add(new hosptalCardDataImpl("피부과", R.drawable.hosbackground, R.drawable.skin, createItemsList("피부과")));
        return list;
    }

    public static List<String> createItemsList(String address) {
        ArrayList<String> list = new ArrayList<>();
        List<String> lis = new ArrayList<>(Arrays.asList());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofit1 = retrofit.create(RetrofitAPI.class);
        retrofit1.listHospital(address).enqueue(new Callback<List<HospitalVO>>(){
            @Override
            public void onResponse(Call<List<HospitalVO>> call, Response<List<HospitalVO>> response) {
                List<HospitalVO> datat;
                if (response.isSuccessful()) {
                    datat = response.body();
                    int e = 0;
                    for(HospitalVO i : datat){
                        Log.e("CARDDTATLATALFDG", i.getHname());
                        list.add(i.getHname());
                        lis.addAll(Arrays.asList(list.get(e)));
                        e += 1;
                    }

                }
                else {
                    Log.e("TAGGGAGAGA", "caalla"+call);
                }
            }
            @Override
            public void onFailure(Call<List<HospitalVO>> call, Throwable t) {
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