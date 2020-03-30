package com.github.gotterdemarung.hbench.clients;

import com.github.gotterdemarung.hbench.Client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Java11HttpClient implements Client {
    private final URI uri;
    private final HttpClient client;

    public Java11HttpClient(
            final int port,
            final boolean use_2_0
    ) {
        this.uri = URI.create("http://127.0.0.1:" + port + "/ping");

        HttpClient.Builder builder = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER);
        builder.version(
                use_2_0 ? HttpClient.Version.HTTP_2 : HttpClient.Version.HTTP_1_1
        );

        this.client = builder.build();
    }

    public void send() {
        HttpRequest req = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString("ping")).build();
        HttpResponse<String> resp;
        try {
            resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Received status " + resp.statusCode());
        }
        if (!"pong".equalsIgnoreCase(resp.body())) {
            throw new RuntimeException("Invalid response");
        }
    }
}
