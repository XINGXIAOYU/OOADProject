package ooad.bean;

/**
 * Created by mayezhou on 2017/6/17.
 */
public class Response {
    boolean isSuccess;
    String message;
    Object object;

    public Response(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public Response() {
        this.isSuccess = true;
        this.message = "";
    }

    public Response(String message) {
        this.isSuccess = false;
        this.message = message;
    }
}
