package com.example.demo1.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public boolean isCapable;
    public void setCapable(String sit) {
        if (sit.equals("true")) isCapable = true;
        else isCapable = false;
    }
    private static final int PORT = 12345;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    private static List<Thread> clientThreads = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client is connected!");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThreads.add(clientThread);
                pool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
