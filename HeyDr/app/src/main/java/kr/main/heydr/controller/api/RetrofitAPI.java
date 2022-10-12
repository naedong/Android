package kr.main.heydr.controller.api;

import java.util.List;

import kr.main.heydr.controller.vo.DoctorVO;
import kr.main.heydr.controller.vo.FCMTo;
import kr.main.heydr.controller.vo.HospitalVO;
import kr.main.heydr.controller.vo.PharmacyVO;
import kr.main.heydr.controller.vo.ReserveVO;
import kr.main.heydr.controller.vo.ReviewVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
//    @GET("1")
//    Call<PharmacyVOResponse> getPnumData(@Header("content_type") String contentType, @Body int pnum);

    @GET("citypharmacy")
    Call<List<PharmacyVO>> listPharmacy(
            @Query("address") String address);


    @GET("androidhospital")
    Call<List<HospitalVO>> listHospital(
            @Query("address") String address);

    @GET("androididchk")
    Call<Integer> IdChk(
            @Query("id") String id);

    @POST("authorize")
    Call<List<String>> login(String accessToken);


    @Headers({"Authorization:key=AAAAdr9jklw:APA91bFeUq3GHu6liKkr-rvxeRaWi2a42JYJ17tV28GV8Y4KhoCee14M7PVp_IaKXlfQ6rCjjSXMF_ksOpSsxuHHe1r4HeEMUr5WCf9ZNf9RxtmoSiONRJNCr6yPkAyiqqZvprdgXcLd"})
    @POST("fcm/send")
    Call<String> sendFCM(@Body FCMTo sendtoken);

    @GET("selectHospitalCate")
    Call<List<HospitalVO>> seletHospital();

    @GET("selectHospital")
    Call<List<HospitalVO>> seletCate(
            @Query("choice") String choice);

    @GET("selecHospitaldoc")
    Call<List<DoctorVO>> seletDoc(
            @Query("hos") String hos);

    @GET("getshoppharmacy")
    Call<List<PharmacyVO>> getShopPharmacy(
            @Query("name") String name);

    @GET("getSelectHospital")
    Call<List<HospitalVO>> getSelectHospital(
            @Query("name") String name);

    @GET("getHospitalReview")
    Call<List<ReviewVO>> getHospitalReview(
            @Query("name") String name);
    @GET("getReserveHospital")
    Call<List<ReserveVO>> getReserveHospital(
            @Query("name") String name);

}
