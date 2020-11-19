package com.dixitClient.dixit.login;

public class LoginManager {
    public static String name = "imie";
    public static int port = 9090;
    public static String ip= "localhost";
    public static boolean finish = false;

    public static Integer getPort() {
        return port;
    }

    public static String getName() {
        return name;
    }

    public static String getIp() {
        return ip;
    }
}
