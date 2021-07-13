package data_scraper.mediciones;

import data_scraper.DataExtractor;
import data_scraper.PostRequest;

import java.util.HashMap;
import java.util.Map;

public class SwellMeasures {
    public static final String DELIMITER = ",";
    public static final String[] EXPECT_1 = {"12", "1"};
    public static final String[] EXPECT_2 = {"10", "6", "2", "1"};
    public static final String[] EXPECT_FIRST = {"0", "0", "0", "1"};
    public static final int MAX_ROW = 4;
    public static final int TARGET_ROW_1 = 3;
    public static final int TARGET_ROW_2 = 4;
    private PostRequest request;

    public SwellMeasures() {
        this.request = new PostRequest("https://portus.puertos.es/Portus_RT/portusgwt/rpc",
                "https://portus.puertos.es/Portus_RT/portusgwt/",
                "1C515B081663E4BEA92E2671F2079F9A",
                "7|0|6|https://portus.puertos.es/Portus_RT/portusgwt/|4763486D406A92423D6F43998BCFC0EF|es.puertos.portus.main.client.service.PortusService|requestData|I|Z|1|2|3|4|4|5|5|5|6|2442|11|0|1|");
    }

    public Map<String, Double> getMeasures(){
        String response = request.getResponse();
        Map<String, Double> tides = new HashMap<>();
        String[] lines = response.split(DELIMITER);
       // tides.put("altura-significativa", DataExtractor.extract_data(lines, EXPECT_1, MAX_ROW, TARGET_ROW_1));
        tides.put("periodo-medio-tm02", DataExtractor.extract_data(lines, EXPECT_FIRST, EXPECT_2, TARGET_ROW_2));
        return tides;
    }
}
