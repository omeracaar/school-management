package com.schoolmanagement.payload.messages;

public class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String ALREADY_SEND_A_MESSAGE_TODAY = "Error: You have already send a message with thi e-mail";

    public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: User with username %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_SSN = "Error: User with ssn %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE_NUMBER = "Error: User with Phone Number %s is already registered";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: User with email %s is already registered";

    public static final String ROLE_NOT_FOUND = "Error: There is no role like that, check the database";

    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "Error: You don't have any permission to do this operation";

    public static final String ROLE_ALREADY_EXIST = "Error: Role already exist in DB";
}