import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Belirtilen portu dinleyen ve gelen her bağlantıyı ClientHandler'a devreden sunucu
public class WebServer {
    private final int port;
    private final HtmlPage page;

    public WebServer(int port, HtmlPage page) {
        this.port = port;
        this.page = page;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Sunucu çalışıyor: http://localhost:" + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();   // bir tarayıcı bağlanana kadar bekler
                new Thread(new ClientHandler(clientSocket, page)).start();
            }
        }
    }
}