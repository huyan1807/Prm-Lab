package com.example.myapplication.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.myapplication.person.Person;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("SELECT * FROM person WHERE uid IN (:personId)")
    Person loadPersonById(long personId);

    @Query("SELECT * FROM person WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    Person findByName(String first, String last);

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);
}
