package com.github.gotterdemarung.hbench;

import com.github.gotterdemarung.hbench.clients.ApacheHttpClient;
import com.github.gotterdemarung.hbench.clients.GoogleHttpClient;
import com.github.gotterdemarung.hbench.clients.Java11HttpClient;
import com.github.gotterdemarung.hbench.servers.UndertowServer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int port = 7000;
        final int warmup = 10000;

        final String serverArg = args[0];
        final String clientArg = args[1];
        final int threads = Integer.parseInt(args[2]);
        final int total = Integer.parseInt(args[3]);

        HashMap<String, Supplier<Server>> servers = new HashMap<>();
        HashMap<String, Supplier<Client>> clients = new HashMap<>();

        clients.put("j11", () -> new Java11HttpClient(port, false));
        clients.put("j11_2_0", () -> new Java11HttpClient(port, true));
        clients.put("apache", () -> new ApacheHttpClient(port));
        clients.put("google", () -> new GoogleHttpClient(port));

        servers.put("undertow", () -> new UndertowServer(port));

        // Choosing
        Server server = servers.get(serverArg).get();

        // Running server
        server.start();

        // Running warmup
        Sampling wsampl = new Sampling(warmup);
        Client wclient = clients.get(clientArg).get();
        while (wsampl.isNotFull()) {
            long ts = System.nanoTime();
            wclient.send();
            wsampl.add(System.nanoTime() - ts);
        }
        System.out.println("Warmup done");


        Sampling sample = new Sampling(total);
        long before = System.currentTimeMillis();

        // Running observer
        new Thread(() -> {
            long now = 0;
            while (sample.isNotFull()) {
                now = System.currentTimeMillis();
                System.out.printf(
                        Locale.ROOT,
                        "Done: %d RPS: %.1f Min: %.1fms Avg: %.1fms Max: %.1fms\n",
                        sample.getDone(),
                        sample.getDone() * 1000. / (now - before),
                        sample.getMin() / 1000000.,
                        sample.getAverage() / 1000000.,
                        sample.getMax() / 1000000.
                );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }

            // Stopping everything
            server.stop();

            // Printing summary
            sample.sort();
            System.out.printf("Client sig: %s\nServer sig: %s\n", clientArg, serverArg);
            System.out.printf(
                    Locale.ROOT,
                    "  Memory: %sMb\n  Threads: %d\n  Done: %d\n  RPS: %.1f\n  Elapsed: %ds\n\n",
                    Runtime.getRuntime().maxMemory() / 1024 / 1024,
                    threads,
                    total,
                    sample.getDone() * 1000. / (now - before),
                    Duration.ofMillis(now - before).getSeconds()
            );
            System.out.printf(
                    Locale.ROOT,
                    "  Mean 50: %.2fms\n  Mean 90: %.2fms\n  Mean 95: %.2fms\n  Mean 98: %.2fms\n  Mean 99: %.2fms\n",
                    sample.getMean(50) / 1000000.,
                    sample.getMean(90) / 1000000.,
                    sample.getMean(95) / 1000000.,
                    sample.getMean(98) / 1000000.,
                    sample.getMean(99) / 1000000.
            );
        }).start();

        // Running executor threads
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                Client client = clients.get(clientArg).get();
                while (sample.isNotFull()) {
                    long ts = System.nanoTime();
                    client.send();
                    sample.add(System.nanoTime() - ts);
                }
            }).start();
        }
    }
}
