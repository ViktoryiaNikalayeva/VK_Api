package utils;
import kong.unirest.Unirest;
import models.Response;

public class ApiUtils {
    static {Unirest.config().defaultBaseUrl(DataSettingsUtils.getElementConfig("vkApiUrl"));}
    public static Response post(String request) {
        return new Response(Unirest.post(request).asJson());
    }
}