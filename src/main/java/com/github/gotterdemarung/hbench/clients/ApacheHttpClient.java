package com.github.gotterdemarung.hbench.clients;

import com.github.gotterdemarung.hbench.Client;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ApacheHttpClient implements Client {
    private final CloseableHttpClient client;
    private final URI uri;

    public ApacheHttpClient(final int port) {
        HttpClientBuilder builder = HttpClients.custom()
                .disableAuthCaching()
                .disableAutomaticRetries()
                .disableRedirectHandling();

        this.uri = URI.create("http://127.0.0.1:" + port + "/ping");
        this.client = builder.build();
    }

    @Override
    public void send() {
        HttpPost req = new HttpPost(uri);
        req.setEntity(new StringEntity("ping", StandardCharsets.UTF_8));
        try (CloseableHttpResponse resp = client.execute(req)) {
            if (resp.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Received status " + resp.getStatusLine().getStatusCode());
            }

            HttpEntity entity = resp.getEntity();
            if (entity == null) {
                throw new RuntimeException("Empty response");
            }
            String responseBody = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            if (!"pong".equalsIgnoreCase(responseBody)) {
                throw new RuntimeException("Invalid response");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
