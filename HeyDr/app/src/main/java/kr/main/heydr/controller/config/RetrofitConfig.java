package kr.main.heydr.controller.config;


import kr.main.heydr.controller.api.RetrofitAPI;

import java.util.concurrent.TimeUnit;

import kr.main.heydr.controller.api.RetrofitAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitConfig {


        private static RetrofitConfig instance = null;
//        private static com.maina.main_project.spring.api.initMyApi initMyApi;
        private static RetrofitAPI RetrofitAPI;
        //사용하고 있는 서버 BASE 주소
        private static final String baseUrl = "http://192.168.0.47:83";

        public RetrofitAPI getRetrofit(){
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

            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            return retrofitAPI;
        }
    public RetrofitAPI fcmToken(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//이건뭘까

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        return retrofitAPI;
    }


        public RetrofitConfig() {
            //로그를 보기 위한 Interceptor
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100,TimeUnit.SECONDS)
                    .writeTimeout(100,TimeUnit.SECONDS)
                    .build();
            //retrofit 설정
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) //로그 기능 추가
                    .build();

//            initMyApi = retrofit.create(initMyApi.class);
            RetrofitAPI = retrofit.create(RetrofitAPI.class);
        }

        public static RetrofitConfig getInstance() {
            if (instance == null) {
                instance = new RetrofitConfig();
            }
            return instance;
        }

        public static RetrofitAPI getRetorifitAPI(){
            return RetrofitAPI;
        }


        // public static initMyApi getRetrofitInterface() {
//            return initMyApi;
//        }
    }