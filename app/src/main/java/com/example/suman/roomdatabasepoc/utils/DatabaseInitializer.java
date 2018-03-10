package com.example.suman.roomdatabasepoc.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.suman.roomdatabasepoc.database.AppDatabase;
import com.example.suman.roomdatabasepoc.entity.User;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by Suman on 26-02-2018.
 */

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void populateWithTestData(AppDatabase db) {
        User user = new User();
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");
        user.setAge(25);
        addUser(db, user);

        List<User> users = db.userDao().getAllUsers();
        Log.d(DatabaseInitializer.TAG, "Rows Count : " + users.size());
    }

    private static User addUser(AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        public PopulateDbAsync(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithTestData(db);
            return null;
        }
    }
}
