package OnlineChat.Server;


import OnlineChat.Server.DB.ConnectToDB;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private int port;
    private final ServerSocket serverSocket;
    private static final Logger logger = LogManager.getLogger(Server.class.getName());
//    private final AuthService authService;
    private final ConnectToDB authService = new ConnectToDB();
    private final Set<ClientHandler> clients;

    public Server(int port) {
        try {
            this.port = port;
            serverSocket = new ServerSocket(port);
//            authService = new AuthService();
            clients = new HashSet<ClientHandler>();
            logger.info("server start");
            while (true){
                System.out.println("Waiting for new client");
                Socket socket = serverSocket.accept();
                String mes = "Client "+ socket+ " connect.";
                System.out.println(mes);
                logger.info(mes);
                ClientHandler clientHandler = new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            logger.fatal("SWW when up a server", e);
            throw new RuntimeException("SWW when up a server", e);
        } finally {
            logger.info("Server down.");
            authService.closeConnection();
        }

    }

    public synchronized void broadcast (String message, String name){
        for (ClientHandler client : clients){
            logger.info(name + " broadcast message");
            client.sendMessage(message);
        }
    }

    public synchronized boolean privateMessage (String name, String message, String sender){
        for (ClientHandler client : clients){
            if (client.getName().equals(name)) {
                logger.info(sender + " whisper to " + name);
                client.sendMessage(message);
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe (ClientHandler clientHandler){
        logger.info(clientHandler.getName() + " login");
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe (ClientHandler clientHandler){
        logger.info(clientHandler.getName() + " logout");
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
                logger.info(nickname + " create new account");
                return nickname;
            } else return null;
        } catch (SQLException e) {
            logger.error("SWW when create new account", e);
            throw new RuntimeException("SWW when create new account", e);
        }
    }

    public boolean changeLogin (String nickname, String password, String newLogin){
        try {
            logger.info(nickname + " change login.");
            return authService.changeLogin(nickname, password, newLogin);
        } catch (SQLException e) {
            logger.error("SWW when change login.", e);
            throw new RuntimeException("SWW when change login.", e);
        }
    }

    public boolean changeNickname (String nickname, String password, String newNickname){
        try {
            logger.info(nickname + " change name to " + newNickname);
            return authService.changeNickname(nickname, password, newNickname);
        } catch (SQLException e) {
            logger.error("SWW when change name.", e);
            throw new RuntimeException("SWW when change name.", e);
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
