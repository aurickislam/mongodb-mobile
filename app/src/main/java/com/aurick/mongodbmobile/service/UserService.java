package com.aurick.mongodbmobile.service;

import android.util.Log;

import com.aurick.mongodbmobile.MongoDBManager;
import com.aurick.mongodbmobile.model.User;
import com.aurick.mongodbmobile.utils.JacksonUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class UserService {

    private static UserService userService = null;

    private UserService() {
    }

    public static UserService getInstance() {
        Log.e("@Aurick", "UserService getInstance");
        if (userService == null) {
            synchronized (UserService.class) {
                if (userService == null) {
                    userService = new UserService();
                }
            }
        }
        return userService;
    }

    private MongoCollection<Document> localCollection = MongoDBManager.getCollection("user");

    public void createUser() {
        Document document = new Document() {
            {
                put("name", "Aurick");
                put("age", 26);
                put("email", "aurick@gmail.com");
            }
        };
        localCollection.insertOne(document);
    }

    public User findUserById(String id) {
        Document query = new Document() {
            {
                put("_id", new ObjectId(id));
            }
        };
        return JacksonUtils.fromJson(localCollection.find(query).first().toJson(), User.class);
    }

    public ArrayList<Document> findUsersByName(String name) {
        Document query = new Document() {
            {
                put("name", new BsonString(name));
            }
        };
        FindIterable<Document> cursor = localCollection.find(query);

        return cursor.into(new ArrayList<>());
    }

}
