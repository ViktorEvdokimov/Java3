package OnlineChat.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ClientAdapter {
    private  ClientNetwork network;
    private  ChatWindow window;
    private final ExecutorService es = Executors.newSingleThreadExecutor();

    public ClientAdapter(String host, int port) {
        try {
            this.network = new ClientNetwork(host, port);
        this.window = new ChatWindow(new Consumer<String>() {
            @Override
            public void accept(String message) {
                network.sendMessage(message);
            }
        });
            es.execute(this::receive);
        } finally {
            es.shutdown();
        }
    }

    private void createWindow (){
        this.window = new ChatWindow(new Consumer<String>() {
            @Override
            public void accept(String message) {
                network.sendMessage(message);
            }
        });
    }

    private void receive (){
 //       new Thread(()-> {
            while (true){
                String message = network.receive();
                if (!message.isBlank()) {
                    window.append(message);
                }
            }
 //       }).start();
    }
}
