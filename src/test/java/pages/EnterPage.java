package pages;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class EnterPage extends Form {
    private final ITextBox loginInput = AqualityServices.getElementFactory().getTextBox(By.id("index_email"), "Login input");
    private final ITextBox passwordInput = AqualityServices.getElementFactory().getTextBox(By.id("index_pass"), "Password input");
    private final IButton enterButton = AqualityServices.getElementFactory().getButton(By.id("index_login_button"), "Enter button");

    public EnterPage(By locator, String name) {
        super(locator, name);
    }

    public void logInVKAndTransitionToMainPage(String login, String password) {
        loginInput.clearAndType(login);
        passwordInput.clearAndType(password);
        enterButton.click();
    }
}
