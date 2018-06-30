package com.example.suman.roomdatabasepoc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.suman.roomdatabasepoc.R;
import com.example.suman.roomdatabasepoc.database.AppDatabase;
import com.example.suman.roomdatabasepoc.executor.AppExecutors;
import com.example.suman.roomdatabasepoc.utils.DatabaseInitializer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Button btnClick = (Button) findViewById(R.id.btn_click_here);
        btnClick.setOnClickListener(view ->
                DatabaseInitializer.populateAsync(AppDatabase.getInstance(getApplicationContext()), new AppExecutors(), this));

        //activityMainBinding.btnClickHere.setOnClickListener(view -> DatabaseInitializer.populateAsync(AppDatabase.getInstance(this, new AppExecutors())));
    }
}
