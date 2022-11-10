package com.example.oathapp.requesting;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserResponse {

    @SerializedName("response")
    @Expose
    private List<User> response = null;

    public List<User> getResponse() {
        return response;
    }

    public void setResponse(List<User> response) {
        this.response = response;
    }

}