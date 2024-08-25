package Infra.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static ConfigurationReader ourInstance;

    static {
        try {
            ourInstance = new ConfigurationReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigurationReader getInstance() {
        return ourInstance;
    }
    Properties properties = new Properties();

    private ConfigurationReader() throws IOException {

        try {
            String propertiesPath;
            File jarPath = new File(ConfigurationReader.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath_target = jarPath.getParentFile().getAbsolutePath();
            propertiesPath = propertiesPath_target.replace("target", "src/main/java/Infra/config_files/configuration.properties");
            System.out.println(" Properties File Path: " + propertiesPath);
            properties.load(new FileInputStream(propertiesPath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        }
    }

    public String getValue(String key){
        return (String)properties.get(key);
    }

}