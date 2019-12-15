package com.aurick.mongodbmobile;

import android.util.Log;

import com.aurick.mongodbmobile.service.UserService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.services.mongodb.local.LocalMongoDbService;

import org.bson.Document;

public class MongoDBManager {

    private static MongoDatabase mongoDatabase = null;

    private MongoDBManager() {
    }

    private static MongoDatabase getDatabase() {
        Log.e("@Aurick", "MongoDatabase getDatabase");

        if (mongoDatabase == null) {
            synchronized (MongoDBManager.class) {
                if (mongoDatabase == null) {
                    StitchAppClient client = Stitch.initializeDefaultAppClient(BuildConfig.APPLICATION_ID);

                    MongoClient mongoClient = client.getServiceClient(LocalMongoDbService.clientFactory);

                    mongoDatabase = mongoClient.getDatabase("my_db");
                }
            }
        }
        return mongoDatabase;
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        Log.e("@Aurick", "MongoDatabase getCollection");

        return getDatabase().getCollection(collectionName);
    }

}
