package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.interfaces.PersonDao;
import com.example.myapplication.person.Person;

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
