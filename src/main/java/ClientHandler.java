import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// Tek bir tarayıcı bağlantısını işleyen sınıf: isteği okur, cevabı gönderir
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final HtmlPage page;

    public ClientHandler(Socket socket, HtmlPage page) {
        this.socket = socket;
        this.page = page;
    }

    @Override
    public void run() {
        try (Socket s = socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             OutputStream out = s.getOutputStream()) {

            String requestLine = in.readLine();          // ör: "GET / HTTP/1.1"
            if (requestLine == null) {
                return;
            }
            System.out.println("İstek: " + requestLine);

            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                // başlıkları okuyup geçiyoruz
            }

            String path = requestLine.split(" ")[1];
            HttpResponse response = path.equals("/")
                    ? HttpResponse.ok(page.render())
                    : HttpResponse.notFound();

            out.write(response.toBytes());
            out.flush();
        } catch (IOException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
        }
    }
}