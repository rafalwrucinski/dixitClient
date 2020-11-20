package com.dixitClient.dixit.serverClient;

import com.dixitClient.gameEngine.GameContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientManager {


    private Socket socket;
    private int portInt;
    private String ip;
//    private TimeLimiter timeLimiter = new SimpleTimeLimiter();

    public ServerClientManager(String ip, int port) throws IOException {
        this.ip = ip;
        this.portInt= port;
        socket= new Socket(this.ip,this.portInt);
    }

    public Socket getSocket() {
        return socket;
    }
}
