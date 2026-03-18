package co.edu.escuelaing.framework.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.edu.escuelaing.framework.annotations.Component;
import co.edu.escuelaing.framework.annotations.RestController;

public class ClassScanner {

    public static List<Class<?>> scan(String basePackage) {

        List<Class<?>> controllers = new ArrayList<>();

        try {
            String packagePath = basePackage.replace(".", "/");

            String classPath = System.getProperty("java.class.path");

            String[] paths = classPath.split(File.pathSeparator);

            String basePath = null;

            for (String path : paths) {
                if (path.endsWith("classes") && !path.contains("test-classes")) {
                    basePath = path;
                    break;
                }
            }

            if (basePath == null) {
                System.out.println("⚠ No valid classpath found");
                return controllers;
            }

            File baseDir = new File(basePath + "/" + packagePath);

            if (!baseDir.exists()) {
                System.out.println("⚠ Directory not found: " + baseDir.getAbsolutePath());
                return controllers;
            }

            scanDirectory(baseDir, basePackage, controllers);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return controllers;
    }

    private static void scanDirectory(File directory, String packageName, List<Class<?>> controllers) {

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {

            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName(), controllers);

            } else if (file.getName().endsWith(".class")) {

                String className = packageName + "." + file.getName().replace(".class", "");

                try {
                    Class<?> clazz = Class.forName(className);

                    if (clazz.isAnnotationPresent(RestController.class) ||
                        clazz.isAnnotationPresent(Component.class)) {

                        controllers.add(clazz);
                        System.out.println("Found bean: " + className);
                    }
                } catch (Throwable ignored) {
                }
            }
        }
    }
}