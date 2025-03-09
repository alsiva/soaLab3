package secondservice.businesslogic.ejb;

import jakarta.ejb.Local;
import jakarta.ws.rs.core.Response;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Local
public interface HTTPClientLocal {
    Response getRequest(String url) throws NoSuchAlgorithmException, KeyManagementException;
    Response deleteRequest(String url) throws NoSuchAlgorithmException, KeyManagementException;
}
