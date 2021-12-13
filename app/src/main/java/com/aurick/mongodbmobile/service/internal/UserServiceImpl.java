package com.aurick.mongodbmobile.service.internal;

import android.util.Log;

import com.aurick.mongodbmobile.MongoDBManager;
import com.aurick.mongodbmobile.model.User;
import com.aurick.mongodbmobile.service.UserService;
import com.aurick.mongodbmobile.utils.GsonUtils;
import com.aurick.mongodbmobile.utils.JacksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private static final String TAG = "UserService";

    private static UserServiceImpl userService = null;

    private final MongoCollection<Document> userCollection = MongoDBManager.getCollection("user");

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        Log.e(TAG, "getInstance");
        if (userService == null) {
            synchronized (UserServiceImpl.class) {
                if (userService == null) {
                    userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }

    @Override
    public void createUser() {
        Document document = new Document() {
            {
                put("name", "Aurick");
                put("age", 26);
                put("email", "aurick@gmail.com");
            }
        };
        userCollection.insertOne(document);
    }

    @Override
    public UpdateResult updateUser(User user) {
        Document filter = new Document() {
            {
                put("_id", user.getId());
            }
        };

//        Document userDocument = Document.parse(GsonUtils.toJson(user));
        Document userDocument = Document.parse(JacksonUtils.toJson(user));
        userDocument.remove("_id");

        Document updateDocument = new Document().append("$set",
                userDocument
        );

        Log.e(TAG, "updateUser" + updateDocument.toJson());

        return userCollection.updateOne(filter, updateDocument);
    }

    @Override
    public User findUser(String userId) {
        Document query = new Document() {
            {
                put("_id", new ObjectId(userId));
            }
        };

        Document userDoc = userCollection.find(query).first();

        if (userDoc == null)
            return null;
        return JacksonUtils.fromJson(userDoc.toJson(), User.class);
//        return GsonUtils.fromJson(userDoc.toJson(), User.class);
    }

    @Override
    public ArrayList<User> findUsersByName(String name) {
        Document query = new Document() {
            {
                put("name", name);
            }
        };
        FindIterable<Document> cursor = userCollection.find(query);
        return JacksonUtils.fromJson(JacksonUtils.toJson(cursor.into(new ArrayList<>())), new TypeReference<ArrayList<User>>() {});
//        return GsonUtils.fromJson(GsonUtils.toJson(cursor.into(new ArrayList<>())), new TypeToken<ArrayList<User>>() {}.getType());
    }

    @Override
    public ArrayList<User> getUsers() {
        FindIterable<Document> cursor = userCollection.find();
        return JacksonUtils.fromJson(JacksonUtils.toJson(cursor.into(new ArrayList<>())), new TypeReference<ArrayList<User>>() {});
//        return GsonUtils.fromJson(GsonUtils.toJson(cursor.into(new ArrayList<>())), new TypeToken<ArrayList<User>>() {}.getType());
    }

    @Override
    public DeleteResult deleteUser(String userId) {
        Document query = new Document() {
            {
                put("_id", new ObjectId(userId));
            }
        };
        return userCollection.deleteOne(query);
    }

}
