package com.example.suman.roomdatabasepoc.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.suman.roomdatabasepoc.dao.UserDao;
import com.example.suman.roomdatabasepoc.entity.User;
import com.example.suman.roomdatabasepoc.executor.AppExecutors;

/**
 * Created by Suman on 26-02-2018.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTACE;

    @VisibleForTesting
    public static final String DATABASE_NAME = "user-database";

    public abstract UserDao userDao();

    private final MutableLiveData<Boolean> IS_DATABASE_CREATED = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executers) {
        if (INSTACE == null) {
            synchronized (AppDatabase.class) {
                if (INSTACE == null) {
                    INSTACE = buildDatabase(context.getApplicationContext(), executers);
                    INSTACE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTACE;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(Context applicationContext) {
        if (applicationContext.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        IS_DATABASE_CREATED.postValue(true);
    }

    private static AppDatabase buildDatabase(final Context applicationContext, final AppExecutors executers) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                }).build();
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return IS_DATABASE_CREATED;
    }

}
