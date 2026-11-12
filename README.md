# Java Web Sunucusu (Socket Programlama)

Bu proje, **Kırklareli Üniversitesi Yazılım Mühendisliği** bölümü **Nesneye Yönelik Programlama** dersi kapsamında hazırlanmıştır. Herhangi bir üçüncü parti kütüphane kullanılmadan, yalnızca Java'nın standart `java.net` ve `java.io` paketleriyle yazılmış basit bir HTTP web sunucusudur.

Sunucu **1989** numaralı portu dinler. Bir web tarayıcısı bağlandığında, öğrencinin adını, numarasını ve HTML/CSS ile biçimlendirilmiş kısa bir biyografisini içeren bir sayfa gönderir.

## 👩‍💻 Öğrenci Bilgileri

| | |
|---|---|
| **Ad Soyad** | Merve Bilgiç |
| **Öğrenci No** | 1250505009 |
| **Ders** | Nesneye Yönelik Programlama |

## ✨ Özellikler

- `ServerSocket` ile 1989 numaralı portun dinlenmesi
- Her istemci bağlantısının ayrı bir iş parçacığında (thread) işlenmesi
- HTTP isteğinin okunması ve istenen yolun ayrıştırılması
- HTTP standardına uygun cevap üretimi (`200 OK`, `404 Not Found`)
- UTF-8 desteği ile Türkçe karakterlerin doğru gösterilmesi
- CSS ile biçimlendirilmiş biyografi sayfası (renk, yazı tipi, kenarlık)
- SOLID ilkelerine uygun, sorumlulukları ayrılmış sınıf yapısı

## 🛠️ Gereksinimler

- Java 17 veya üzeri (metin blokları `"""` için Java 15+ gereklidir)
- Maven
- IntelliJ IDEA (önerilir)

## ▶️ Çalıştırma

1. Depoyu klonlayın:
   ```bash
   git clone https://github.com/Mrv-bilgic/JavaWebServer.git
   ```
2. Projeyi IntelliJ IDEA ile açın.
3. `Main.java` dosyasını çalıştırın.
4. Konsolda `Sunucu çalışıyor: http://localhost:1989` mesajını görün.
5. Tarayıcıda şu adrese gidin:
   ```
   http://localhost:1989
   ```

## 📁 Proje Yapısı

```
src/main/java
├── Main.java            → Uygulamanın giriş noktası; nesneleri oluşturur ve sunucuyu başlatır
├── WebServer.java       → 1989 portunu dinler, gelen bağlantıları kabul eder
├── ClientHandler.java   → Tek bir istemci bağlantısını işler (isteği okur, cevabı gönderir)
├── HttpResponse.java    → HTTP cevabını (durum satırı, başlıklar, gövde) oluşturur
├── HtmlPage.java        → Sayfaların uyması gereken arayüz (render metodu)
└── BiographyPage.java   → Biyografi sayfasının HTML içeriğini üretir
```

## ⚙️ Çalışma Prensibi

```
Tarayıcı ──"GET / HTTP/1.1"──► WebServer (port 1989)
                                     │ accept()
                                     ▼
                              ClientHandler (yeni thread)
                                     │ isteği okur
                                     ▼
                     BiographyPage.render() → HTML üretir
                                     │
                                     ▼
                     HttpResponse.ok(html) → HTTP cevabı
                                     │
Tarayıcı ◄──── "HTTP/1.1 200 OK" + HTML ───┘
```

1. `WebServer`, `ServerSocket` ile 1989 numaralı portta beklemeye başlar.
2. Tarayıcı bağlandığında `accept()` metodu, bu bağlantıya ait bir `Socket` nesnesi döndürür.
3. Bağlantı, yeni bir iş parçacığında çalışan `ClientHandler`'a devredilir; sunucu bir sonraki bağlantıyı beklemeye devam eder.
4. `ClientHandler`, tarayıcının gönderdiği HTTP isteğinin ilk satırını (`GET / HTTP/1.1`) okur ve istenen yolu belirler.
5. Yol `/` ise `HtmlPage` arayüzü üzerinden sayfanın HTML'i üretilir; değilse `404 Not Found` cevabı hazırlanır.
6. `HttpResponse`, durum satırı, başlıklar ve gövdeden oluşan cevabı bayt dizisine çevirir ve soket üzerinden tarayıcıya gönderilir.
7. İşlem bitince bağlantı otomatik olarak kapatılır (try-with-resources).

## 🧱 SOLID İlkelerinin Uygulanması

| İlke | Projede Uygulanışı |
|---|---|
| **S** – Single Responsibility (Tek Sorumluluk) | Her sınıfın tek bir görevi vardır: `WebServer` bağlantıları kabul eder, `ClientHandler` isteği işler, `HttpResponse` cevabı oluşturur, `BiographyPage` HTML üretir. |
| **O** – Open/Closed (Açık/Kapalı) | Yeni bir sayfa eklemek için mevcut sınıfları değiştirmeye gerek yoktur; `HtmlPage` arayüzünü uygulayan yeni bir sınıf yazmak yeterlidir. |
| **L** – Liskov Substitution (Yerine Geçme) | `HtmlPage` arayüzünü uygulayan her sınıf, sunucuda `BiographyPage`'in yerine sorunsuz kullanılabilir. |
| **I** – Interface Segregation (Arayüz Ayrımı) | `HtmlPage` arayüzü yalnızca tek bir metot (`render`) içerir; sınıflar kullanmadıkları metotları uygulamak zorunda kalmaz. |
| **D** – Dependency Inversion (Bağımlılığın Tersine Çevrilmesi) | `WebServer` ve `ClientHandler`, somut `BiographyPage` sınıfına değil `HtmlPage` arayüzüne bağımlıdır. Hangi sayfanın kullanılacağı `Main` sınıfında dışarıdan verilir. |

## 🌐 Temel Kavramlar

- **IP adresi:** Ağdaki bir cihazın adresidir. `localhost` (127.0.0.1), programın çalıştığı bilgisayarın kendisini ifade eder.
- **Port:** Aynı cihazda çalışan farklı ağ uygulamalarını birbirinden ayıran numaradır. Bu sunucu **1989** numaralı portu kullanır.
- **Socket:** İki program arasındaki iletişim kanalının uç noktasıdır. Sunucu tarafında `ServerSocket` bağlantıları bekler; her bağlantı için oluşturulan `Socket` nesnesi üzerinden veri okunur ve yazılır.

## 📄 Lisans

Bu proje eğitim amaçlı hazırlanmıştır.