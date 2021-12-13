package com.aurick.mongodbmobile.service;

import com.aurick.mongodbmobile.model.User;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;

public interface UserService {

    void createUser();

    UpdateResult updateUser(User user);

    User findUser(String userId);

    ArrayList<User> findUsersByName(String name);

    ArrayList<User> getUsers();

    DeleteResult deleteUser(String userId);

}
