package dataLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The setup.ConfigurationLoader class is used to read the properties file and provides a method
 * to access different properties
 */
public class ConfigurationLoader {

    private static Properties properties;
    static {
        try(InputStream is = ClassLoader.class.getResourceAsStream("/env.properties")) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyValue(String key){
        return properties.getProperty(key);
    }
}