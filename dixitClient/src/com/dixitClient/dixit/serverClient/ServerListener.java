package com.dixitClient.dixit.serverClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ServerListener implements Runnable {
    private String serverRequest = null;
    private String serverRequestLast = null;
    private ServerClientManager serverClientManager;
    private BufferedReader in;

    private ArrayList<String> listOfResponses;

    public ServerListener(ServerClientManager serverClientManager) throws IOException {
        this.serverClientManager =  serverClientManager;
        in = new BufferedReader(new InputStreamReader(serverClientManager.getSocket().getInputStream()));
        listOfResponses= new ArrayList<>();
    }

    @Override
    public void run() {
        try{
            while (true){
                serverRequest = in.readLine();
                if(!serverRequest.equals(serverRequestLast)){
                    serverRequestLast=serverRequest;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getServerRequestLast() {
        return serverRequestLast;
    }
}
