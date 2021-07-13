package API.controllers;

import API.http_responses.CustomRestrictionsResponseEntity;
import model.Restriction;
import model.capacitycalculators.PhysicalCapacityCalculator;
import model.restrictions.RestrictionsCapacity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@CrossOrigin
@Controller
@RequestMapping("/restrictions")
public class RestrictionController {

    @RequestMapping(path = "/actives", method = RequestMethod.GET)
    public ResponseEntity getRestriction() {
        try {

            List<Restriction> restrictionsCapacity = RestrictionsCapacity.getInstance().getRestrictionsCapacity();
            List<Integer> indexes = IntStream.range(0, restrictionsCapacity.size())
                    .boxed()
                    .filter(r -> restrictionsCapacity.get(r).isActive())
                    .collect(Collectors.toList());

            return CustomRestrictionsResponseEntity.getResponseEntityWithRestrictions(indexes);
        } catch (Exception e) {
            e.printStackTrace();
            return CustomRestrictionsResponseEntity.getResponseEntityWhenError();
        }
    }

    @RequestMapping(path = "/data", method = RequestMethod.GET)
    public ResponseEntity getRestrictions() {
        try {
            return CustomRestrictionsResponseEntity.getResponseEntityWhenData();
        } catch (Exception e) {
            e.printStackTrace();
            return CustomRestrictionsResponseEntity.getResponseEntityWhenError();
        }
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity setRestriction(@RequestBody String data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(data);
            jsonNode = jsonNode.get("restrictions");

            List<Integer> restrictions = StreamSupport
                    .stream(jsonNode.spliterator(), false)
                    .map(JsonNode::asInt).collect(Collectors.toList());

            RestrictionsCapacity.getInstance().switchActive(restrictions);
            return CustomRestrictionsResponseEntity.getResponseEntityWithEmptyBody();
        } catch (Exception e) {
            e.printStackTrace();
            return CustomRestrictionsResponseEntity.getResponseEntityWhenError();
        }
    }

    @RequestMapping(path = "/update/CCF", method = RequestMethod.PUT)
    public ResponseEntity setPhysicalDensity(@RequestParam double CCFValue) {
        try {
            new PhysicalCapacityCalculator().changeDensity(1/CCFValue);

            return CustomRestrictionsResponseEntity.getResponseEntityWithEmptyBody();
        } catch (Exception e) {
            e.printStackTrace();
            return CustomRestrictionsResponseEntity.getResponseEntityWhenError();
        }
    }
}
