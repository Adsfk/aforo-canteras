package data_scraper.mediciones;

import data_scraper.DataExtractor;
import data_scraper.PostRequest;

public class WaterTempMeasures {
    public static final String DELIMITER = ",";
    public static final String[] EXPECT_FIRST = {"48", "0", "0", "0", "1"};
    public static final String[] EXPECT = {"1", "9", "5", "2", "1"};
    public static final int TARGET_ROW = 5;
    private PostRequest request;

    public WaterTempMeasures() {
        request = new PostRequest("https://portus.puertos.es/Portus_RT/portusgwt/rpc",
                "https://portus.puertos.es/Portus_RT/portusgwt/",
                "1C515B081663E4BEA92E2671F2079F9A",
                "7|0|6|https://portus.puertos.es/Portus_RT/portusgwt/|4763486D406A92423D6F43998BCFC0EF|es.puertos.portus.main.client.service.PortusService|requestData|I|Z|1|2|3|4|4|5|5|5|6|2442|52|0|1|");
    }

    public double getMeasures(){
        String response = request.getResponse();
        String[] lines = response.split(DELIMITER);
        return DataExtractor.extract_data(lines, EXPECT_FIRST, EXPECT, TARGET_ROW);
    }
}
