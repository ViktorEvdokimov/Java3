package HW2.Client;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ClientNetwork {
    private final int port;
    private final String host;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String name;


    public ClientNetwork(String host, int port) {
        this.port = port;
        this.host = host;
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        } finally {
            sendMessage("/end");
        }
    }

    public void sendMessage (String message){
        try {
            if (message.length()!=0) {
                out.writeUTF(message);
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW when send message", e);
        }
    }

    public String receive (){
        try {
            String message = in.readUTF();
            if (name!=null) saveMessage(message);
            if (message.startsWith("Hello ")) {
                name = message.substring(6);
                saveMessage("New login " + getTime());
                saveMessage(message);
                return getOldMessages();
            }
                return message;
        } catch (IOException e) {
            throw new RuntimeException("SWW when receive message", e);
        }
    }

    private String getOldMessages (){
        try (FileInputStream inData = new FileInputStream(String.format("%s.txt", name))){
            StringBuilder sb = new StringBuilder();
            ArrayList<String> list = new ArrayList<>();
            int temp;
            while ((temp=inData.read())!=-1){
                sb.append((char) temp);
                if (temp=='\n'){
                    list.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
            sb = new StringBuilder();
            int size;
            if (list.size()<=100) size = list.size();
            else size= 100;
            for (int i= list.size()-size; i<list.size();i++) sb.append(list.get(i));
            String st = sb.toString();
            return st;
        } catch (IOException e){
            throw new RuntimeException("SWW when get old messages.", e);
        }
    }

    private void saveMessage (String message){
        message = message + "\n";
        byte[] saveData = message.getBytes();
        try (FileOutputStream outData = new FileOutputStream(String.format("%s.txt", name), true)){
            outData.write(saveData);
        } catch (IOException e){
            throw new RuntimeException("SWW when save file.", e);
        }
    }

    private String getTime (){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss ");
        return format.format(date);
    }
}
