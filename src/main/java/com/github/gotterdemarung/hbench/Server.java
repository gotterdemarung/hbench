package com.github.gotterdemarung.hbench;

/**
 * Defines HTTP server for benchmarking
 */
public interface Server {
    /**
     * Starts HTTP server, that will respond `pong` for received `ping`
     */
    void start();

    /**
     * Shuts down server
     */
    void stop();
}
