package volki.soalab.secondservice.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@ApplicationScoped
public class CustomClient {

    // Метод для создания клиента, который игнорирует самоподписанные сертификаты
    @Produces
    public Client createClient() throws NoSuchAlgorithmException, KeyManagementException {
        // Создаем TrustManager, который будет игнорировать проверку сертификатов
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Игнорируем проверку сертификатов клиента
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Игнорируем проверку сертификатов сервера
                    }
                }
        };

        // Создаем SSLContext, который использует наш TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

        // Создаем клиента с использованием настроенного SSLContext
        return ClientBuilder.newBuilder()
                .sslContext(sslContext) // Устанавливаем SSLContext
                .property("jersey.config.client.connectTimeout", 5000)  // Таймаут на подключение
                .property("jersey.config.client.readTimeout", 10000)   // Таймаут на чтение
                .build();
    }
}
