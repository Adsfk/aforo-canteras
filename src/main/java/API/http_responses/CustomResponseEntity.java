package API.http_responses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponseEntity {
    static ResponseEntity<String> getResponse(String body) {
        return buildResponse(HttpStatus.OK, body);
    }

    static <T> ResponseEntity<T> buildResponse(HttpStatus _status, T _body) {
        return org.springframework.http.ResponseEntity.status(_status)
                .headers(setHeaders())
                .body(_body);
    }

    static HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        return headers;
    }
}
