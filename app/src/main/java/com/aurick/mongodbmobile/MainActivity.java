package com.aurick.mongodbmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aurick.mongodbmobile.model.User;
import com.aurick.mongodbmobile.service.UserService;
import com.aurick.mongodbmobile.utils.JacksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UserService userService = UserService.getInstance();

//        userService.createUser();

        // Find the first document
        /*User user = userService.findUserById("5ca3ab76e6a59d6f5d038fc8");
        Log.e("@Aurick", JacksonUtils.toJson(user));
        Log.e("@Aurick user: ", user.getId());*/


        //Find all documents that match the find criteria
        ArrayList<Document> results = userService.findUsersByName("Aurick");
        for (Document user1 : results) {
            Log.e("@user1", user1.toJson());
        }


        /*ArrayList<User> users = JacksonUtils.fromJson(JacksonUtils.toJson(results), new TypeReference<ArrayList<User>>(){});

        Log.e("@Aurick", "onCreate: results: " + users.toString());

        for (User user1 : users) {
            Log.e("@user1", user1.getId());
        }*/
    }
}
