package OnlineChat.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private final Server server;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String name;
    boolean authorisationFailed = false;
    private final ExecutorService es;
    Thread t1;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        es = Executors.newSingleThreadExecutor();
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            es.execute(this::work);
//            new Thread (this::work).start();
        } catch (IOException e) {
            throw new RuntimeException("SWW when create client handler.");
        } finally {
            es.shutdown();
        }


    }

    private void work (){
        try {
            sendMessage("Hello, for login start your message \"-auth\" then send login and password, " +
                    "or start \"-new\" login, password and nickname to create new account.");
            authorizationLimit();
            authorization();
            waitingMessage();
        } catch (IOException e) {
            throw new RuntimeException("SWW when client handler work");
        } finally {
            closeConnection();
        }
    }

    private void waitingMessage () throws IOException {
        while (true){
            String message = in.readUTF();
            if (message.equals("/end")){
//                server.broadcast(name + "logout");
                return;
            } else if (message.startsWith("/")){
                if (message.startsWith("/w")) {
                    privateMessage(message);
                } else if (message.startsWith("/newlog")){
                    changeLogin(message);
                } else if (message.startsWith("/newnick")){
                    changeNickname(message);
                }
            } else if (!message.isBlank()) server.broadcast(name + ": " +message, name);
        }
    }

    private void authorizationLimit(){
        t1 = new Thread  (()->
        {
            try {
                Thread.sleep(120000);
                authorisationFailed = true;
                sendMessage("Time to connecting lost");
            } catch (InterruptedException e) {
                throw new RuntimeException("SWW when authorization Limit", e);
            }
        });
        t1.start();
    }

    private void privateMessage (String message) {
        String[] parts = message.split("\\s");
        if (parts.length >= 3) {
            StringBuilder sb = new StringBuilder(name);
            sb.append(" whisper you: ");
            for (int i = 2; i < parts.length; i++) {
                sb.append(parts[i]);
                sb.append(" ");
            }
            sb.setLength(sb.length() - 1);
            if (server.privateMessage(parts[1], sb.toString(), name)) sendMessage(parts[1] + " get your message.");
            else sendMessage("Receiver logout or incorrect message.");
        }
    }

    private void changeLogin (String message){
        String[] parts = message.split("\\s");
        if (parts.length == 3) {
            if (server.changeLogin(name, parts[1], parts[2])) sendMessage("Change complete");
            else sendMessage("Incorrect password or login busy.");
        } else sendMessage("Incorrect message length/");
    }

    private void changeNickname (String message){
        String[] parts = message.split("\\s");
        if (parts.length == 3) {
            if (server.changeNickname(name, parts[1], parts[2])) {
                sendMessage("Change complete");
                server.broadcast(String.format("%s change nickname to %s", name, parts[2]), name);
                name = parts[2];
            }
            else sendMessage("Incorrect password or Nickname busy.");
        } else sendMessage("Incorrect message length/");
    }

    private void closeConnection(){
        server.unsubscribe(this);
        server.broadcast(name + " logout.", name);
        try {
            in.close();
            out.close();
            socket.close();
            es.shutdown();
        } catch (IOException e) {
            throw new RuntimeException("SWW when close connection.");
        }

    }

    private  void authorization() throws IOException {
        while (true) {
            String message = in.readUTF();
//            if (message.equals("/end")) {
//                closeConnection();
//            }
            String[] parts = message.split("\\s");
            if (parts[0].equals("-new") || parts[0].equals("-auth")){
            if (parts[0].equals("-new")){
                if (parts.length==4) {
                    name = server.createAccount(parts[1], parts[2], parts[3]);
                    if (name!=null){
                        if (authorisationFailed) {
                            sendMessage("Time to connecting lost");
                            closeConnection();
                            return;
                        }
                        sendMessage("Hello "+name);
                        sendMessage("Create account successful. For send private message yot should start your message" +
                                "/w recipient nickname and yor message. For change login stat /newlog password new login" +
                                "For change login stat /newnick password new nickname");
                        server.broadcast(name + " login", name);
                        server.subscribe(this);
                        authorisationFailed = false;
                        t1.stop();
                        return;
                    } else sendMessage("Login or nickname is busy.");

                } else sendMessage("Incorrect message length.");
            } //else sendMessage("To continue you should login. Please start your message \"-auth\" or \"-new\"");

                if (parts[0].equals("-auth")) {
                    name = server.getNickname(parts[1], parts[2]);
                    if (name != null && parts.length == 3) {
                        if (authorisationFailed) {
                            sendMessage("Time to connecting lost");
                            closeConnection();
                            return;
                        } else if (server.isNickFree(name)) {
                            sendMessage("Hello "+name);
                            sendMessage("Login successful. For send private message yot should start your message" +
                                    "/w recipient nickname and yor message. For change login stat /newlog password new login" +
                                    "For change login stat /newnick password new nickname");
                            server.broadcast(name + " login", name);
                            server.subscribe(this);
                            authorisationFailed = false;
                            t1.stop();
                            return;
                        } else sendMessage("This login busy");
                    } else sendMessage("Incorrect message length, login of password.");
                }
                } else sendMessage("To continue you should login. Please start your message \"-auth\" or \"-new\"");
        }
    }

    public void sendMessage (String message){
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException("SWW when send message.", e);
        }
    }

    public String getName() {
        return name;
    }
}
