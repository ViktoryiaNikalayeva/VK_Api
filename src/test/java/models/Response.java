package models;
import kong.unirest.HttpResponse;
import lombok.Data;

@Data
public class Response {
    private int status;
    private String body;

    public Response(HttpResponse httpResponse) {
        status = httpResponse.getStatus();
        body = httpResponse.getBody().toString();
    }
}