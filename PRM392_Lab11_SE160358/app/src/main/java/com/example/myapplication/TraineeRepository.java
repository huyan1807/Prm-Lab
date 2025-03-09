package com.example.myapplication;

public class TraineeRepository {

    public static TraineeService getTraineeService() {
        return APIClient.getClient().create(TraineeService.class);
    }
}
