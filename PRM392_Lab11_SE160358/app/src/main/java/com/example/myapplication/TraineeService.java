package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TraineeService {

    String TRAINEES = "Nhanvien";

    @GET(TRAINEES)
    Call<List<Trainee>> getAllTrainees();

    @GET(TRAINEES + "/{id}")
    Call<Trainee> getTrainee(@Path("id") int id);

    @POST(TRAINEES)
    Call<Trainee> createTrainee(@Body Trainee trainee);

    @PUT(TRAINEES + "/{id}")
    Call<Trainee> updateTrainee(@Path("id") Object id, @Body Trainee trainee);

    @DELETE(TRAINEES + "/{id}")
    Call<Trainee> deleteTrainee(@Path("id") Object id);


}
