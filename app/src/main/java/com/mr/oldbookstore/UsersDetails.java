package com.mr.oldbookstore;

public class UsersDetails {
    private static String username = "";
    private static String password = "";
    private static String chatWith = "";

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UsersDetails.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UsersDetails.password = password;
    }

    public static String getChatWith() {
        return chatWith;
    }

    public static void setChatWith(String chatWith) {
        UsersDetails.chatWith = chatWith;
    }
}
