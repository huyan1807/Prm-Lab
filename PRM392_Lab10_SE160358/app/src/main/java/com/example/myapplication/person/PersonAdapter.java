package com.example.myapplication.person;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.activity.EditPersonActivity;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    private final Context context;
    private List<Person> personList = new ArrayList<>();

    public PersonAdapter(Context context) {
        this.context = context;
    }

    public void setTasks(List<Person> persons) {
        personList = persons;
        notifyDataSetChanged();
    }

    public List<Person> getTasks() {
        return personList;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.firstName.setText(person.getFirstName());
        holder.lastName.setText(person.getLastName());

        holder.editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPersonActivity.class);
            intent.putExtra(Constants.INTENT_PERSON_ID, person.getUid());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName;
        ImageView editIcon;

        PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            editIcon = itemView.findViewById(R.id.editIcon);
        }
    }
}
