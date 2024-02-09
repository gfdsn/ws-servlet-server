package com.ngfds.wsserver.utils;

public enum ResponseInfo {

    /* Just an example */
    /* Every model should have its own enum */

    USER_CREATED("User created successfully"),
    AUTH_INVALID_CREDENTIALS("Invalid credentials"),
    MESSAGE_CREATION_ERROR("An error occurred when trying to create the image");

    private final String value;
    private ResponseInfo(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }

}
