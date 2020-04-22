package com.aurick.mongodbmobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.aurick.mongodbmobile.model.User;
import com.aurick.mongodbmobile.service.UserService;
import com.aurick.mongodbmobile.service.internal.UserServiceImpl;
import com.aurick.mongodbmobile.utils.GsonUtils;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e(TAG, "Button Click");
                runTest();
            }
        });

        new Thread() {
            @Override
            public void run() {
                Log.e(TAG, "Initializing Service");
                userService = UserServiceImpl.getInstance();
                Log.e(TAG, "Service Initialized");
            }
        }.start();

        /*new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Initializing Service");
                        userService = UserService.getInstance();
                        Log.e(TAG, "Service Initialized");
                    }
                },
                500
        );*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void runTest() {
        userService.createUser();

        User userById = userService.findUserById("5e9c73db2a86281d78b666ca");
        Log.e("@userById", GsonUtils.toJson(userById));

        if (userById != null) {
            userById.setName("Aurick Islam");
            UpdateResult updateResult = userService.updateUser(userById);
            Log.e("@updateResult", String.valueOf(updateResult.getMatchedCount()));
            Log.e("@updateResult", String.valueOf(updateResult.wasAcknowledged()));
            Log.e("@updateResult", String.valueOf(updateResult.getModifiedCount()));
        }

        ArrayList<User> usersByName = userService.findUsersByName("Aurick Islam");
        Log.e("@usersByName", GsonUtils.toJson(usersByName));

        DeleteResult deleteResult = userService.deleteUser("5e9c7468e3a34247f459f5e0");
        Log.e("@deleteResult", String.valueOf(deleteResult.getDeletedCount()));
        Log.e("@deleteResult", String.valueOf(deleteResult.wasAcknowledged()));

        ArrayList<User> users = userService.getUsers();
        Log.e("@users", GsonUtils.toJson(users));
    }
}
