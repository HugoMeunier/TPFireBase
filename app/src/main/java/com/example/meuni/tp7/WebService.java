package com.example.meuni.tp7;

import com.example.meuni.tp7.models.Bottle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebService {

    @GET("cellars/{user}/bottles")
    Call<List<Bottle>> getAllBottles(@Path("user") String user);

   @POST("cellars/{ownerName}/bottles")
    Call<Void> postBottle(@Path("ownerName") String ownerName, @Body Bottle bottleToCreate);


}
