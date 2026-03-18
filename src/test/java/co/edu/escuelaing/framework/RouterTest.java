package co.edu.escuelaing.framework;

import co.edu.escuelaing.framework.ioc.IoCContainer;
import co.edu.escuelaing.framework.routing.Router;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RouterTest {

    @Test
    public void shouldReturnHelloResponse() throws Exception {
        IoCContainer container = new IoCContainer("co.edu.escuelaing.framework");
        container.initialize();

        Router router = new Router(container);
        router.loadRoutes();

        String response = router.route("/hello");

        assertNotNull(response);
        assertTrue(response.contains("Hello"));
    }

    @Test
    public void shouldReturn404ForInvalidRoute() throws Exception {
        IoCContainer container = new IoCContainer("co.edu.escuelaing.framework");
        container.initialize();

        Router router = new Router(container);
        router.loadRoutes();

        String response = router.route("/no-existe");

        assertEquals("404 NOT FOUND", response);
    }

    @Test
    public void shouldLoadBeans() throws Exception {
        IoCContainer container = new IoCContainer("co.edu.escuelaing.framework");
        container.initialize();

        assertFalse(container.getBeans().isEmpty());
    }
}