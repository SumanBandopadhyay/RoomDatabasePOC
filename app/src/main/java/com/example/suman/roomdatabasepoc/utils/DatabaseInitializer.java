package com.example.suman.roomdatabasepoc.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.suman.roomdatabasepoc.database.AppDatabase;
import com.example.suman.roomdatabasepoc.entity.User;
import com.example.suman.roomdatabasepoc.executor.AppExecutors;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by Suman on 26-02-2018.
 */

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db, AppExecutors executors, Context context) {
        executors.getDiskIO().execute(() -> {
            // Add delay to simulate long running Action
            addDelay();
            //Generate the data for pre-population
            AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
            populateWithTestData(database);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private static void addUser(AppDatabase db, User user) {
        db.runInTransaction(() -> {
            db.userDao().insertAll(user);
        });
    }
}
