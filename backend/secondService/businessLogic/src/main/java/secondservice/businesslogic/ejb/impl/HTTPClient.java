package secondservice.businesslogic.ejb.impl;

import jakarta.ejb.Stateless;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import secondservice.businesslogic.config.CustomClient;
import secondservice.businesslogic.ejb.HTTPClientLocal;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Stateless(name = "HTTPClient")
public class HTTPClient implements HTTPClientLocal {

    @Override
    public Response getRequest(String url) throws NoSuchAlgorithmException, KeyManagementException {
        Client client = new CustomClient().createClient();
        WebTarget target = client.target(url);
        return target.request(MediaType.APPLICATION_XML).get();
    }

    @Override
    public Response deleteRequest(String url) throws NoSuchAlgorithmException, KeyManagementException {
        Client client = new CustomClient().createClient();
        WebTarget target = client.target(url);
        return target.request(MediaType.APPLICATION_XML).delete();
    }
}
