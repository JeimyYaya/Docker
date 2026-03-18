package co.edu.escuelaing.framework;

import co.edu.escuelaing.framework.ioc.IoCContainer;
import co.edu.escuelaing.framework.routing.Router;
import co.edu.escuelaing.framework.http.HttpServerWrapper;

public class FrameworkApplication {
    public static void main(String[] args) throws Exception {

        int port = 8080; // default local

        String envPort = System.getenv("PORT");
        if (envPort != null) {
            port = Integer.parseInt(envPort);
        }

        IoCContainer container = new IoCContainer("co.edu.escuelaing.framework");
        container.initialize();

        Router router = new Router(container);
        router.loadRoutes();

        HttpServerWrapper server = new HttpServerWrapper(port, router);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}