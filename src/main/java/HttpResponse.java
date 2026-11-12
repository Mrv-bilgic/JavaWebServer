import java.nio.charset.StandardCharsets;

// HTTP cevabını (durum satırı + başlıklar + gövde) oluşturan sınıf
public class HttpResponse {
    private final int statusCode;
    private final String statusText;
    private final String body;

    public HttpResponse(int statusCode, String statusText, String body) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.body = body;
    }

    public static HttpResponse ok(String body) {
        return new HttpResponse(200, "OK", body);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(404, "Not Found", "<h1>404 - Sayfa bulunamadı</h1>");
    }

    public byte[] toBytes() {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        String headers = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Content-Length: " + bodyBytes.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";
        byte[] headerBytes = headers.getBytes(StandardCharsets.UTF_8);

        byte[] result = new byte[headerBytes.length + bodyBytes.length];
        System.arraycopy(headerBytes, 0, result, 0, headerBytes.length);
        System.arraycopy(bodyBytes, 0, result, headerBytes.length, bodyBytes.length);
        return result;
    }
}