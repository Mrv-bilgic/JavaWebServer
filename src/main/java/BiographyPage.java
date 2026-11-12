// Biyografi sayfasının HTML içeriğini üreten sınıf
public class BiographyPage implements HtmlPage {
    private final String fullName;
    private final String studentNo;
    private final String biography;

    public BiographyPage(String fullName, String studentNo, String biography) {
        this.fullName = fullName;
        this.studentNo = studentNo;
        this.biography = biography;
    }

    @Override
    public String render() {
        return """
                <!DOCTYPE html>
                <html lang="tr">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                    <style>
                        body { font-family: Georgia, serif; background-color: #f4f1ea;
                               color: #2e2e2e; max-width: 720px; margin: 40px auto; padding: 0 20px; }
                        h1 { font-family: Verdana, sans-serif; color: #1f5f8b; margin-bottom: 4px; }
                        h2 { font-family: Verdana, sans-serif; color: #7a7a7a; font-weight: normal; }
                        .bio { background-color: #ffffff; border-left: 6px solid #1f5f8b;
                               padding: 16px 24px; line-height: 1.6; }
                        .bio b { color: #c0392b; }
                    </style>
                </head>
                <body>
                    <h1>%s</h1>
                    <h2>Öğrenci No: %s</h2>
                    <div class="bio">
                        %s
                    </div>
                </body>
                </html>
                """.formatted(fullName, fullName, studentNo, biography);
    }
}