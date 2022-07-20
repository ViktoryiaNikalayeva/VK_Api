package tests;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import models.Response;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EnterPage;
import pages.MainPage;
import steps.UploadImageSteps;
import utils.*;
import java.util.List;

import static pages.MainPage.REGEX;

public class VkApiTest extends BaseTest {

    @Test
    public void task() {
        EnterPage enterPage = new EnterPage(By.id("index_email"), "Login input");
        enterPage.logInVKAndTransitionToMainPage(DataSettingsUtils.getElementCreds("login"),
                DataSettingsUtils.getElementCreds("password"));
        MainPage mainPage = new MainPage(By.className("page_name"), "Username");
        mainPage.clickOnMyPage();
        Response getUserIdResponse = Request.getUserIdResponse();
        Assert.assertEquals(getUserIdResponse.getStatus(), HttpStatus.OK,
                String.format("Actual %s, expected %s", getUserIdResponse.getStatus(), HttpStatus.OK));
        Response postIdResponse = Request.createPostOnTheWall(RandomUtils.createRandomText());
        Assert.assertEquals(postIdResponse.getStatus(), HttpStatus.OK,
                String.format("Actual %s, expected %s", postIdResponse.getStatus(), HttpStatus.OK));
        String postId = postIdResponse.getBody().replaceAll(REGEX, "");
        String author = MainPage.getAuthorFromPost(postId, DataSettingsUtils.getElement("attribute"));
        String textFromPost = mainPage.getTextFromPost(postId);
        Assert.assertEquals(textFromPost, RandomUtils.getText(),
                String.format("Actual %s, expected %s", textFromPost, RandomUtils.getText()));
        Assert.assertEquals(author, DataSettingsUtils.getElementConfig("userAsAuthor"),
                String.format("Actual %s, expected %s", author, DataSettingsUtils.getElementConfig("userAsAuthor")));

        Response uploadUrlResponse = UploadImageSteps.getWallPhotoResponse();
        System.out.println(uploadUrlResponse.getBody());
        Assert.assertEquals(uploadUrlResponse.getStatus(), HttpStatus.OK,
                String.format("Actual %s, expected %s", uploadUrlResponse.getStatus(), HttpStatus.OK));
        String uploadUrl = UploadImageSteps.getUploadUrlOfServer();
        System.out.println(uploadUrl);
        HttpResponse<JsonNode> uplImageOnTheWallResp = UploadImageSteps.uploadImageOnTheWallResponse(uploadUrl);
        Assert.assertEquals(uplImageOnTheWallResp.getStatus(), HttpStatus.OK,
                String.format("Actual %s, expected %s", uplImageOnTheWallResp.getStatus(), HttpStatus.OK));
        List<String> imageList = ListUtils.addImageList(uplImageOnTheWallResp);
        Response getImageIdResponse = UploadImageSteps.getImageIdResponse(imageList);
        Assert.assertEquals(getImageIdResponse.getStatus(), HttpStatus.OK,
                String.format("Actual %s, expected %s", getImageIdResponse.getStatus(), HttpStatus.OK));
        String imageId = UploadImageSteps.getImageId(imageList);
        Response editResponse = Request.editPostOnTheWall(RandomUtils.createRandomText(), postId, imageId);
        Assert.assertEquals(editResponse.getStatus(), HttpStatus.OK,
                String.format("Actual %s, expected %s", editResponse.getStatus(), HttpStatus.OK));
        String textFromEditedPost = mainPage.getTextFromPost(postId);
        Assert.assertEquals(textFromEditedPost, RandomUtils.getText(),
                String.format("Actual %s, expected %s", textFromEditedPost, RandomUtils.getText()));

        Request.createCommentPostOnTheWall(RandomUtils.createRandomText(), postId);
        mainPage.clickTimeForRightPost(postId);
        String textFromComment = mainPage.getTextFromComment(postId);
        Assert.assertEquals(textFromComment, RandomUtils.getText(),
                String.format("Actual %s, expected %s", textFromComment, RandomUtils.getText()));
        String commentAuthor = MainPage.getAuthorFromPost(postId, DataSettingsUtils.getElement("attribute"));
        Assert.assertEquals(commentAuthor, DataSettingsUtils.getElementConfig("userAsAuthor"),
                String.format("Actual %s, expected %s", commentAuthor, DataSettingsUtils.getElementConfig("userAsAuthor")));

        mainPage.clickLikePost(postId);
        Response postLikeResponse = Request.isPostLiked(postId);
        Assert.assertEquals(postLikeResponse.getStatus(), HttpStatus.OK,
                String.format("Actual %s, expected %s", postLikeResponse.getStatus(), HttpStatus.OK));

        Assert.assertTrue(mainPage.isPostLiked(postLikeResponse), "Post is not liked");
        Request.deletePostFromTheWall(postId);
        Assert.assertTrue(mainPage.isPostExist(postId), "Post is exist");
    }
}