package co.edu.escuelaing.framework.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import co.edu.escuelaing.framework.routing.Router;

public class HttpServerWrapper {

    private HttpServer server;
    private Router router;
    private int port;

    public HttpServerWrapper(int port, Router router) throws IOException {
        this.router = router;
        this.port = port;

        server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        server.createContext("/", new RequestHandler(router));

        server.setExecutor(Executors.newFixedThreadPool(10));
    }

    public void start() {
        server.start();
        System.out.println("Server running on port " + port);
    }

    public void stop() {
        System.out.println("Shutting down...");
        server.stop(1);
    }
}
