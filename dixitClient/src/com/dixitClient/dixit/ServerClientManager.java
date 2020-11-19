package com.dixitClient.dixit;

import com.dixitClient.gameEngine.GameContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientManager implements Runnable{
    private Thread serverClientThread;
    private Socket socket;
    private int portInt;
    private String ip;
    private BufferedReader in;
    private PrintWriter outputStream;

    private String serverRequest = null;
    private String clientResponse;

    ServerClientManager(String ip, int port) throws IOException {
        this.ip = ip;
        this.portInt= port;
        socket= new Socket(this.ip,this.portInt);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new PrintWriter(socket.getOutputStream());
        serverClientThread = new Thread(this);
    }

    @Override
    public void run() {
        try{
            while (true){
                serverRequest = in.readLine();
                if(!clientResponse.isEmpty()){
                    outputStream.println(clientResponse);
                    outputStream.flush();
                    clientResponse=null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getServerRequest() {
        return serverRequest;
    }

    public void setRequest(String request) {
        this.serverRequest = request;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(String clientResponse) {
        this.clientResponse = clientResponse;
    }

    public PrintWriter getOutputStream() {
        return outputStream;
    }
}
