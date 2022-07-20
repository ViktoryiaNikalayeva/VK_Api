package utils;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class DataSettingsUtils {

    public static String getElement(String elementName) {
        ISettingsFile environment = new JsonSettingsFile("testData.json");
        return environment.getValue("/" + elementName).toString();
    }
    public static String getElementCreds(String elementName) {
        ISettingsFile environment = new JsonSettingsFile("creds.json");
        return environment.getValue("/" + elementName).toString();
    }
    public static String getElementConfig(String elementName) {
        ISettingsFile environment = new JsonSettingsFile("config.json");
        return environment.getValue("/" + elementName).toString();
    }
}
