import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String biography = """
                <p>Merhaba! Ben Merve Bilgiç, <b>Kırklareli Üniversitesi Yazılım Mühendisliği</b> 2. sınıf öğrencisiyim.</p>
                <p>2019 yılında <b>Bezmialem Vakıf Üniversitesi</b>'nden mezun oldum. İki yıllık özel sektör deneyimimin ardından
                <b>İstanbul Atlas Üniversitesi</b>'nde yüksek lisansa başladım. 2025 yılında ise <i>kariyer değiştirmek amacıyla</i>
                Kırklareli Üniversitesi Yazılım Mühendisliği bölümüne giriş yaptım.</p>
                <p style="color: #2e8b57;">Yeni alanımda öğrenmeye ve kendimi geliştirmeye devam ediyorum.</p>
                <p><b>İlgi alanlarım:</b></p>
                <ul>
                    <li>Örgü yapmak</li>
                    <li>Kitap okumak</li>
                </ul>
                """;

        HtmlPage page = new BiographyPage("Merve Bilgiç", "1250505009", biography);
        WebServer server = new WebServer(1989, page);
        server.start();
    }
}