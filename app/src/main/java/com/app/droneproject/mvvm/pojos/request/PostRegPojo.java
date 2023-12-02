package com.app.droneproject.mvvm.pojos.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostRegPojo {
    @SerializedName("first_name")
    @Expose
    String firstName;
    @SerializedName("last_name")
    @Expose
    String lastName;
    @SerializedName("username")
    @Expose
    String userName;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("password2")
    @Expose
    String confirmPassword;
    @SerializedName("phone_number")
    @Expose
    String phoneNumber;

    public PostRegPojo() {
    }

    public PostRegPojo(String firstName,
                       String lastName,
                       String userName,
                       String email,
                       String password,
                       String confirmPassword,
                       String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
    }

}
