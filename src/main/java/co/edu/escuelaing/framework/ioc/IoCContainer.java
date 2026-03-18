package co.edu.escuelaing.framework.ioc;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;

import co.edu.escuelaing.framework.util.ClassScanner;
import co.edu.escuelaing.framework.annotations.Component;
import co.edu.escuelaing.framework.annotations.RestController;

public class IoCContainer {

    private Map<Class<?>, Object> beans = new HashMap<>();
    private String basePackage;

    public IoCContainer(String basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() throws Exception {
        List<Class<?>> classes = ClassScanner.scan(basePackage);

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class) ||
                clazz.isAnnotationPresent(RestController.class)) {

                Object instance = clazz.getDeclaredConstructor().newInstance();
                beans.put(clazz, instance);
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }

    public Collection<Object> getBeans() {
        return beans.values();
    }
}
