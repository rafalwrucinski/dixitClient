package com.dixitClient.dixit.serverClient;


import java.io.IOException;
import java.io.PrintWriter;

public class ServerResponder implements Runnable {
    private ServerClientManager serverClientManager;

    private PrintWriter out;
    private String serverResponse=null;

    public ServerResponder(ServerClientManager serverClientManager) throws IOException {
        this.serverClientManager = serverClientManager;
        out = new PrintWriter(serverClientManager.getSocket().getOutputStream());
    }

    @Override
    public void run(){
        while (true){
            if(serverResponse!=null){
                out.println(serverResponse);
                out.flush();
                serverResponse=null;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setServerResponse(String serverResponse){
        this.serverResponse=serverResponse;
    }
}
