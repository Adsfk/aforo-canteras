package API.controllers;

import API.http_responses.CustomTidesResponseEntity;
import model.*;
import model.capacitycalculators.EffectiveCapacityCalculator;
import model.capacitycalculators.PhysicalCapacityCalculator;
import model.capacitycalculators.RealCapacityCalculator;
import model.interpolation.LasCanterasZonesModel;
import model.restrictions.RestrictionsCapacity;
import persistence.Datalake;
import persistence.DatalakePersistenceFile;
import data_scraper.predicciones.TidesPredictions;
import kong.unirest.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@CrossOrigin
@Controller
@RequestMapping("/tides")
public class TidesController {
    private static DatalakePersistence datalakePersistence = new DatalakePersistenceFile();

    @RequestMapping(path = "/historic", method = RequestMethod.GET)
    public ResponseEntity getHistoricInfo(@RequestParam String date, @RequestParam String hour) {
        try {
            Double tide = datalakePersistence.getHeight(date + " " + hour);
            return getResponseEntity(tide);
        } catch (Exception e) {
            return CustomTidesResponseEntity.getResponseEntityWhenError();
        }
    }

    @RequestMapping(path = "/measure", method = RequestMethod.GET)
    public ResponseEntity getMeasureInfo(@RequestParam String date, @RequestParam String hour) {
        try {
            Double tide = SeaHeightScrapper.getInstance().getLastTide();
            return getResponseEntity(tide);
        } catch (Exception e) {
            e.printStackTrace();
            return CustomTidesResponseEntity.getResponseEntityWhenError();
        }
    }

    @RequestMapping(path = "/prediction", method = RequestMethod.GET)
    public ResponseEntity getPredictionInfo(@RequestParam String date, @RequestParam String hour) {
        try {
            String[] dateFields = date.split("-");
            date = dateFields[2] + "-" + dateFields[1] + "-" + dateFields[0];
            Double tide = new TidesPredictions().getMeasures(date+" "+hour);
            return getResponseEntity(tide);
        } catch (Exception e) {
            return CustomTidesResponseEntity.getResponseEntityWhenError();
        }
    }

    private ResponseEntity getResponseEntity(Double tide) {
        if(noData(tide)) return CustomTidesResponseEntity.getResponseEntityWhenNoData();
        return buildResponseEntity(tide);
    }

    private boolean noData(Double tide) {
        return tide == -40.;
    }

    private ResponseEntity buildResponseEntity(Double tide) {
        List<Integer> surfaces = new LasCanterasZonesModel().calculateSurfaces(tide);
        Collections.reverse(surfaces);
        surfaces.add(0,surfaces.stream()
                .mapToInt(x->x)
                .sum());

        String normalizedTide = String.valueOf(tide);

        if(normalizedTide.split("\\.")[1].length()==2) normalizedTide += "0";


        RestrictionsCapacity restrictionsCapacity = RestrictionsCapacity.getInstance();
        List<Restriction> restrictionsCapacityList = restrictionsCapacity.getRestrictionsCapacity();

        List[] restrictions = new List[restrictionsCapacityList.size()];

        for (int i = 0; i < restrictionsCapacityList.size(); i++) {
            restrictions[i] = getCapacity(surfaces,i);
        }

        List<Integer> physical = getPhysicalCapacity(surfaces);
        List<Integer> realCapacity = getRealCapacity(surfaces);
        List<Integer> effective = getEffectiveCapacity(surfaces);

        return CustomTidesResponseEntity.getResponseEntityWhenData(normalizedTide, surfaces,
                physical, realCapacity, effective , restrictions);
    }

    private List<Integer> getEffectiveCapacity(List<Integer> surfaces) {
        RestrictionsCapacity restrictions = RestrictionsCapacity.getInstance();
        CapacityCalculator capacityCalculator = new EffectiveCapacityCalculator(restrictions.getRestrictionsCapacity());
        return capacityCalculator.calculate(surfaces);
    }

    private List<Integer> getPhysicalCapacity(List<Integer> surfaces) {
        CapacityCalculator capacityCalculator = new PhysicalCapacityCalculator();
        return capacityCalculator.calculate(surfaces);
    }

    private List<Integer> getRealCapacity(List<Integer> surfaces) {
        RestrictionsCapacity restrictions = RestrictionsCapacity.getInstance();
        CapacityCalculator capacityCalculator = new RealCapacityCalculator(restrictions.getRestrictionsCapacity());
        return capacityCalculator.calculate(surfaces);
    }

    private List<Integer> getCapacity(List<Integer> surfaces, int criteria) {
        RestrictionsCapacity restrictionsCapacity = RestrictionsCapacity.getInstance();
        List<Restriction> restrictionsCapacityList = restrictionsCapacity.getRestrictionsCapacity();
        return restrictionsCapacityList.get(criteria).getCapacityCalculator().calculate(surfaces);
    }

    public void persistTide(Tides tide) {
        JSONObject data = getDatalake(tide);
        datalakePersistence.write(data);
    }

    private JSONObject getDatalake(Tides tide) {
        return Datalake.getData(tide);
    }

}