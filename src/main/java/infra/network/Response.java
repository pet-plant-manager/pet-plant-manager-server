package infra.network;

import java.util.Map;

public class Response {
    public enum StatusCode{
        SUCCESS, FAIL
    }

    public StatusCode statusCode;
    public Map<String, Object> data; //TODO : Object -> DTO interface로 변환
}
