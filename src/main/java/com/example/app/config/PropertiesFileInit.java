package com.example.app.config;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileInit {

    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE = "db.properties";

    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesFileInit.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            PROPERTIES.load(inputStream);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}
