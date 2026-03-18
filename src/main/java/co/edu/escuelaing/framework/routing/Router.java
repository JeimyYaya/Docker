package co.edu.escuelaing.framework.routing;

import java.util.HashMap;
import java.lang.reflect.*;
import java.util.Map;

import co.edu.escuelaing.framework.ioc.IoCContainer;
import co.edu.escuelaing.framework.annotations.RestController;
import co.edu.escuelaing.framework.annotations.GetMapping;

public class Router {

    private Map<String, Method> routes = new HashMap<>();
    private Map<Method, Object> controllers = new HashMap<>();
    private IoCContainer container;

    public Router(IoCContainer container) {
        this.container = container;
    }

    public void loadRoutes() {
        for (Object bean : container.getBeans()) {
            Class<?> clazz = bean.getClass();

            if (clazz.isAnnotationPresent(RestController.class)) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        String path = method.getAnnotation(GetMapping.class).value();
                        System.out.println("Ruta registrada: " + path);
                        routes.put(path, method);
                        controllers.put(method, bean);
                    }
                }
            }
        }
    }

    public String route(String path) throws Exception {
        Method method = routes.get(path);

        if (method == null) {
            return "404 NOT FOUND";
        }

        Object controller = controllers.get(method);
        return (String) method.invoke(controller);
    }
}
