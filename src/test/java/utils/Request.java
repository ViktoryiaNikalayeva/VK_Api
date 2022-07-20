package utils;
import kong.unirest.Unirest;
import models.Response;

public class Request {

    protected static final String TOKEN = DataSettingsUtils.getElementCreds("token");
    protected static final String WALL_POST = DataSettingsUtils.getElementConfig("wallPost");
    protected static final String WALL_EDIT = DataSettingsUtils.getElementConfig("wallEdit");
    protected static final String WALL_CREATE_COMMENT = DataSettingsUtils.getElementConfig("wallCreateComment");
    protected static final String WALL_DELETE = DataSettingsUtils.getElementConfig("wallDelete");
    protected static final String LIKE = DataSettingsUtils.getElementConfig("like");
    protected static final String VK_API_VERSION = DataSettingsUtils.getElementConfig("vkApiVersion");
    protected static final String USER_GET_ID = DataSettingsUtils.getElementConfig("getUserId");
    protected static final String USER_ID_REQUEST = String.format("%s?access_token=%s&v=%s", USER_GET_ID, TOKEN, VK_API_VERSION);
    public static Response getUserIdResponse() {
        return ApiUtils.post(USER_ID_REQUEST);
    }
    public static String userId() {
        return Unirest.post(USER_ID_REQUEST).asJson().getBody().getObject().getJSONArray("response")
                .getJSONObject(0).getString("id");
    }
    public static Response createPostOnTheWall(String text) {
        String request = String.format("%s?owner_id=%s&message=%s&access_token=%s&v=%s",
                WALL_POST, userId(), text, TOKEN, VK_API_VERSION);
        return ApiUtils.post(request);
    }
    public static Response editPostOnTheWall(String text, String postId, String photoId) {
        String request = String.format("%s?owner_id=%s&post_id=%s&message=%s&attachments=photo%s_%s&access_token=%s&v=%s",
                WALL_EDIT, userId(), postId, text, userId(), photoId, TOKEN, VK_API_VERSION);
        return ApiUtils.post(request);
    }
    public static void createCommentPostOnTheWall(String text, String postId) {
        String request = String.format("%s?owner_id=%s&post_id=%s&message=%s&access_token=%s&v=%s",
                WALL_CREATE_COMMENT, userId(), postId, text, TOKEN, VK_API_VERSION);
        ApiUtils.post(request);
    }
    public static Response isPostLiked(String postId) {
        String request = String.format("%s?owner_id=%s&user_id=%s&type=%s&item_id=%s&access_token=%s&v=%s",
                LIKE, userId(), userId(), DataSettingsUtils.getElement("objectType"), postId, TOKEN,
                VK_API_VERSION);
        return ApiUtils.post(request);
    }
    public static void deletePostFromTheWall(String postId)  {
            String request = String.format("%s?owner_id=%s&post_id=%s&access_token=%s&v=%s", WALL_DELETE, userId(), postId,
                    TOKEN, VK_API_VERSION);
            ApiUtils.post(request);
    }
}