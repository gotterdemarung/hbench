package com.github.gotterdemarung.hbench;

/**
 * Defines HTTP client for benchmarking
 */
public interface Client {
    /**
     * Sends `ping` request
     */
    void send();
}
