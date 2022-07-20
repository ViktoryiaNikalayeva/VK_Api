package utils;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {
    private static String randomText;
    public static String createRandomText() {
        return randomText = RandomStringUtils.randomAlphanumeric(Integer.parseInt(DataSettingsUtils.getElement("length")));
    }
    public static String getText() {
        return randomText;
    }
}