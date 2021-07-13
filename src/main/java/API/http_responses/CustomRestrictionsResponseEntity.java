package API.http_responses;

import model.Restriction;
import model.capacitycalculators.PhysicalCapacityCalculator;
import model.restrictions.RestrictionsCapacity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CustomRestrictionsResponseEntity extends CustomResponseEntity {
    private static final String ERROR_MESSAGE = "{\"message\":\"There was a problem, couldn't get information\"}";
    private static final String NO_DATA_NO_ERROR = "{}";

    public static ResponseEntity getResponseEntityWithEmptyBody(){
        return getResponse(NO_DATA_NO_ERROR);
    }

    public static ResponseEntity getResponseEntityWhenError() {
        return buildResponse(HttpStatus.CONFLICT, ERROR_MESSAGE);
    }

    public static ResponseEntity getResponseEntityWithRestrictions(List<Integer> active) {
        return getResponse("{\"selected\":" + active+"}");
    }

    public static ResponseEntity getResponseEntityWhenData(){
        return getResponse(getBody());
    }

    private static String getBody() {
        double density = new PhysicalCapacityCalculator().getDensity();
        String baseBody = "{\"CCFDensity\":"+ (1/density) +",\"restrictions\":[";

        ObjectMapper mapper = new ObjectMapper();
        for (Restriction restriction: RestrictionsCapacity.getInstance().getRestrictionsCapacity()) {
            try {
                baseBody += mapper.writeValueAsString(restriction) + ",";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return baseBody.substring(0, baseBody.length()-1) + "]}";
    }
}
