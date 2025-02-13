package volki.soalab.secondservice;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

@ApplicationPath("/api")
public class SecondService extends Application{

}