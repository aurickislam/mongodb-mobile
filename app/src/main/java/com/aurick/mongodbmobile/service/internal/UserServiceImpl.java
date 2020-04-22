package com.aurick.mongodbmobile.service.internal;

import android.util.Log;

import com.aurick.mongodbmobile.MongoDBManager;
import com.aurick.mongodbmobile.model.User;
import com.aurick.mongodbmobile.service.UserService;
import com.aurick.mongodbmobile.utils.GsonUtils;
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

    private static UserServiceImpl userServiceImpl = null;

    private final MongoCollection<Document> localCollection = MongoDBManager.getCollection("user");

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        Log.e(TAG, "getInstance");
        if (userServiceImpl == null) {
            synchronized (UserServiceImpl.class) {
                if (userServiceImpl == null) {
                    userServiceImpl = new UserServiceImpl();
                }
            }
        }
        return userServiceImpl;
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
        localCollection.insertOne(document);
    }

    @Override
    public UpdateResult updateUser(User user) {
        Document filter = new Document() {
            {
                put("_id", user.getId());
            }
        };

        Document userDocument = Document.parse(GsonUtils.toJson(user));
        userDocument.remove("_id");

        Document updateDocument = new Document().append("$set",
                userDocument
        );

        Log.e(TAG, "updateUser" + updateDocument.toJson());

        return localCollection.updateOne(filter, updateDocument);
    }

    @Override
    public User findUserById(String userId) {
        Document query = new Document() {
            {
                put("_id", new ObjectId(userId));
            }
        };

        Document userDoc = localCollection.find(query).first();
        if (userDoc == null)
            return null;
        return GsonUtils.fromJson(userDoc.toJson(), User.class);
    }

    @Override
    public ArrayList<User> findUsersByName(String name) {
        Document query = new Document() {
            {
                put("name", name);
            }
        };
        FindIterable<Document> cursor = localCollection.find(query);
        return GsonUtils.fromJson(GsonUtils.toJson(cursor.into(new ArrayList<>())), new TypeToken<ArrayList<User>>() {
        }.getType());
    }

    @Override
    public ArrayList<User> getUsers() {
        FindIterable<Document> cursor = localCollection.find();
        return GsonUtils.fromJson(GsonUtils.toJson(cursor.into(new ArrayList<>())), new TypeToken<ArrayList<User>>() {
        }.getType());
    }

    @Override
    public DeleteResult deleteUser(String userId) {
        Document query = new Document() {
            {
                put("_id", new ObjectId(userId));
            }
        };
        return localCollection.deleteOne(query);
    }

}
