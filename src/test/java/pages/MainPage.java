package pages;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import models.Response;
import org.openqa.selenium.By;
import utils.DataSettingsUtils;

import java.time.Duration;

public class MainPage extends Form {

    private final ILink downloadsLink = getElementFactory().getLink(By.id("l_pr"), "My page");
    public static final String REGEX = "[^0-9]";
    public MainPage(By locator, String name) {
        super(locator, name);
    }
    public void clickOnMyPage() {downloadsLink.clickAndWait();}

    public String getTextFromPost(String postId) {
        return AqualityServices.getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%s')]//div[contains(@class, 'post_text')]", postId)), "Post text")
                .getText();
    }
    public static String getAuthorFromPost(String postId, String attribute) {
        return AqualityServices.getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id,'%s')]//a[@class='author']", postId)), "User as author")
                .getAttribute(attribute);
    }
    public void clickTimeForRightPost(String postId) {
        AqualityServices.getElementFactory().getLink(By.xpath(String.format("//div[contains(@class, 'replies_wrap')]//a[contains(@href, '%s')]", postId)), "Time post link")
                .clickAndWait();
    }
    public String getTextFromComment(String postId) {
        return AqualityServices.getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%s')]//div[contains(@class, 'wall_reply_text')]", postId)),
                "Comment text").getText();
    }
    public void clickLikePost(String postId) {
        AqualityServices.getElementFactory().getButton(By.xpath(String.format("//div[contains(@id, '%s')]//div[contains(@class, '__icon')]", postId)), "Like button")
                .clickAndWait();
    }
    public boolean isPostExist(String postId) {
        return AqualityServices.getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%s')]//div[contains(@class, 'wall_post_text')]", postId)), "Post")
                .state().waitForNotDisplayed(Duration.ofSeconds(5));
    }
    public boolean isPostLiked(Response response) {
        return response.getBody().replaceAll(REGEX, "").contains(DataSettingsUtils.getElement("isPostLiked"));
    }
}