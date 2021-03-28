package HW2.Server;


import HW2.Server.DB.ConnectToDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private int port;
    private final ServerSocket serverSocket;
//    private final AuthService authService;
    private final ConnectToDB authService = new ConnectToDB();
    private final Set<ClientHandler> clients;

    public Server(int port) {
        try {
            this.port = port;
            serverSocket = new ServerSocket(port);
//            authService = new AuthService();
            clients = new HashSet<ClientHandler>();
            while (true){
                System.out.println("Waiting for new client");
                Socket socket = serverSocket.accept();
                System.out.println("Client "+ socket+ " connect.");
                ClientHandler clientHandler = new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW when up a server", e);
        } finally {
            authService.closeConnection();
        }

    }

    public synchronized void broadcast (String message){
        for (ClientHandler client : clients){
            client.sendMessage(message);
        }
    }

    public synchronized boolean privateMessage (String name, String message){
        for (ClientHandler client : clients){
            if (client.getName().equals(name)) {
                client.sendMessage(message);
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe (ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe (ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public synchronized boolean isNickFree(String name){
        for (ClientHandler client : clients){
            if (client.getName().equals(name)) return false;
        }
        return true;
    }

    public String createAccount (String login, String password, String nickname){
        try {
            if (authService.createNewClient(login,password,nickname)){
                return nickname;
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException("SWW when create new account");
        }
    }

    public boolean changeLogin (String nickname, String password, String newLogin){
        try {
            return authService.changeLogin(nickname, password, newLogin);
        } catch (SQLException e) {
            throw new RuntimeException("SWW when change login.", e);
        }
    }

    public boolean changeNickname (String nickname, String password, String newNickname){
        try {
            return authService.changeNickname(nickname, password, newNickname);
        } catch (SQLException e) {
            throw new RuntimeException("SWW when change login.", e);
        }
    }

    public String getNickname(String login, String password) {
        try {
            return authService.getNicknameByLogin(login, password);
        } catch (SQLException e) {
            throw new RuntimeException("SWW when getting nickname", e);
        }
    }
}
