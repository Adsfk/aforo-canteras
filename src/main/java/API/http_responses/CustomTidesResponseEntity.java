package API.http_responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CustomTidesResponseEntity extends CustomResponseEntity {
    private static final String BODY_WHEN_NO_DATA = "{\"information\":[{\"tide\":no-content}]}";

    public static ResponseEntity getResponseEntityWhenNoData() {
        return getResponse(BODY_WHEN_NO_DATA);
    }

    public static ResponseEntity getResponseEntityWhenData(String tide, List<Integer> surface, List<Integer> physicalCapacity,
                                                           List<Integer> recommendedCapacity, List<Integer> effective,
                                                           List<Integer> ... args){
        return getResponse(getBody(tide, surface, physicalCapacity, recommendedCapacity, effective, args));
    }

    public static ResponseEntity getResponseEntityWhenError() {
        return buildResponse(HttpStatus.OK, BODY_WHEN_NO_DATA);
    }

    private static String getBody(String tide, List<Integer> surface, List<Integer> physicalCapacity,
                                  List<Integer> recommendedCapacity, List<Integer> effective,List<Integer> ...args) {
        String baseBody = "{\"information\":[{\"tide\":\""+ tide +"\"},{\"superficie\":"+
                surface + "},{\"aforo_fisico\":" + physicalCapacity +
                "},{\"aforo_recomendado\": "+ recommendedCapacity +"},{\"aforo_efectivo\": " + effective;

        for (int position = 0; position < args.length; position++) {
            baseBody += "},{\"distancia\":" + args[position];
        }
        return baseBody + "}]}";
    }
}

