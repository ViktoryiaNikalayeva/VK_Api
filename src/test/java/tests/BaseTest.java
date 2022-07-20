package tests;
import aquality.selenium.browser.AqualityServices;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DataSettingsUtils;

public class BaseTest {

    @BeforeMethod
    public void setup() {
        AqualityServices.getBrowser().maximize();
        AqualityServices.getBrowser().goTo(DataSettingsUtils.getElementConfig("url"));
        AqualityServices.getBrowser().waitForPageToLoad();
    }
    @AfterMethod
    public void tearDown() {
        AqualityServices.getBrowser().quit();
    }
}
