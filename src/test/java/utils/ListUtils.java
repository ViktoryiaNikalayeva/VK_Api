package utils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import java.util.LinkedList;
import java.util.List;

public class ListUtils {

    public static List<String> addImageList(HttpResponse<JsonNode> response) {
        List<String> image = new LinkedList<>();
        image.add(response.getBody().getObject().getString("photo"));
        image.add(response.getBody().getObject().getString("server"));
        image.add(response.getBody().getObject().getString("hash"));
        return image;
    }
}