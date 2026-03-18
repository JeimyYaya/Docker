package co.edu.escuelaing.framework.http;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import co.edu.escuelaing.framework.routing.Router;

public class RequestHandler implements HttpHandler {

    private Router router;

    public RequestHandler(Router router) {
        this.router = router;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        System.out.println("Request: " + path);

        String response;

        try {
            response = router.route(path);
            //response = "FUNCIONA";
        } catch (Exception e) {
            response = "500 Internal Server Error";
            e.printStackTrace();
        }

        byte[] bytes = response.getBytes();

        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.flush();
        os.close();
    }
}
