package com.github.gotterdemarung.hbench.servers;

import com.github.gotterdemarung.hbench.Server;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.PathHandler;

import java.nio.charset.StandardCharsets;

public class UndertowServer implements Server, HttpHandler {
    private final int port;
    private Undertow server;

    public UndertowServer(final int port) {
        this.port = port;
    }

    public void start() {
        PathHandler pathHandler = Handlers.path();
        pathHandler.addExactPath("/ping", new BlockingHandler(this));

        server = Undertow.builder()
                .addHttpListener(port, "localhost")
                .setHandler(pathHandler)
                .build();

        server.start();
    }

    @Override
    public void stop() {
        server.stop();
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        String body = new String(exchange.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        if (!"ping".equalsIgnoreCase(body)) {
            throw new Exception("Invalid request");
        }

        exchange.getOutputStream().write("pong".getBytes());
    }
}
