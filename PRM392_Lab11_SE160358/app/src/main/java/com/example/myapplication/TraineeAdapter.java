package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.TraineeViewHolder> {

    private List<Trainee> traineeList;
    private Context context;
    private TraineeService service;

    public TraineeAdapter(List<Trainee> traineeList, Context context) {
        this.traineeList = traineeList;
        this.context = context;
        this.service = TraineeRepository.getTraineeService();
    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TraineeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Trainee trainee = traineeList.get(position);
        holder.tvId.setText(String.valueOf(trainee.getId()));
        holder.tvName.setText(trainee.getName());
        holder.tvEmail.setText(trainee.getEmail());
        holder.tvPhone.setText(trainee.getPhone());
        holder.tvGender.setText(trainee.getGender());

        holder.btnDelete.setOnClickListener(v -> {
            Call<Trainee> call = service.deleteTrainee(trainee.getId());
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    Toast.makeText(context, "Trainee deleted", Toast.LENGTH_SHORT).show();
                    traineeList.remove(position);
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable t) {
                    Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return traineeList.size();
    }

    public static class TraineeViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvEmail, tvPhone, tvGender;
        Button btnDelete;

        public TraineeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvGender = itemView.findViewById(R.id.tvGender);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
