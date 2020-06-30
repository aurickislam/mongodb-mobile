package com.aurick.mongodbmobile.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import org.bson.types.ObjectId;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("_id")
    @JsonProperty("_id")
//    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    private String name;

    private Integer age;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
