package steps;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import models.Response;
import utils.ApiUtils;
import utils.DataSettingsUtils;
import utils.Request;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UploadImageSteps {
    protected static final String VK_API_VERSION = DataSettingsUtils.getElementConfig("vkApiVersion");
    protected static final String TOKEN = DataSettingsUtils.getElementCreds("token");
    protected static final String SAVE_WALL_PHOTO = DataSettingsUtils.getElementConfig("saveWallPhoto");
    protected static final String GET_WALL_PHOTO = DataSettingsUtils.getElementConfig("getWallPhoto");
    protected static final String GET_WALL_REQUEST = String.format("%s?user_id=%s&access_token=%s&v=%s", GET_WALL_PHOTO, Request.userId(), TOKEN, VK_API_VERSION);
    public static Response getWallPhotoResponse() {
        return ApiUtils.post(GET_WALL_REQUEST);
    }

    public static String getUploadUrlOfServer() {
        return Unirest.post(GET_WALL_REQUEST).asJson().getBody().getObject().getJSONObject("response").getString("upload_url");
    }
    public static HttpResponse<JsonNode> uploadImageOnTheWallResponse(String uploadUrl) {
        return Unirest.post(uploadUrl).field(
                DataSettingsUtils.getElement("typeOfFile"),
                new File(DataSettingsUtils.getElement("imagePath"))).asJson();
    }
    public static Response getImageIdResponse(List<String> image) {
        String photo = URLEncoder.encode(image.get(0), StandardCharsets.UTF_8);
        String server = image.get(1);
        String hash = image.get(2);
        String request = String.format("%s?user_id=%s&photo=%s&server=%s&hash=%s&access_token=%s&v=%s",
                SAVE_WALL_PHOTO,
                Request.userId(), photo, server, hash, TOKEN, VK_API_VERSION);
        return ApiUtils.post(request);
    }
    public static String getImageId(List<String> image) {
        String photo = URLEncoder.encode(image.get(0), StandardCharsets.UTF_8);
        String server = image.get(1);
        String hash = image.get(2);
        String request = String.format("%s?user_id=%s&photo=%s&server=%s&hash=%s&access_token=%s&v=%s",
                SAVE_WALL_PHOTO,
                Request.userId(), photo, server, hash, TOKEN, VK_API_VERSION);
        return Unirest.post(request).asJson().getBody().getObject().getJSONArray("response")
                .getJSONObject(0).getString("id");
    }
}