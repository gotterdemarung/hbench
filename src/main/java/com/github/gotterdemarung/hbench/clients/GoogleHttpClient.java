package com.github.gotterdemarung.hbench.clients;

import com.github.gotterdemarung.hbench.Client;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.net.URI;

public class GoogleHttpClient implements Client {
    private final URI uri;
    HttpRequestFactory requestFactory;

    public GoogleHttpClient(final int port) {
        this.uri = URI.create("http://127.0.0.1:" + port + "/ping");
        this.requestFactory = new NetHttpTransport.Builder()
                .build()
                .createRequestFactory();
    }

    @Override
    public void send() {
        try {
            HttpRequest req = requestFactory.buildPostRequest(
                    new GenericUrl(uri),
                    ByteArrayContent.fromString(null, "ping")
            );

            HttpResponse resp = req.execute();
            if (resp.getStatusCode() != 200) {
                throw new RuntimeException("Received status " + resp.getStatusCode());
            }
            if (!"pong".equalsIgnoreCase(resp.parseAsString())) {
                throw new RuntimeException("Invalid response");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
