package data_scraper.mediciones;

import data_scraper.DataTidesExtractor;
import data_scraper.PostRequest;

public class TideMeasures {
    private static final int MAX_ROW = 4;
    private static final int TARGET_ROW = 1;
    private static final String[] EXPECT_CODE = {"12", "1"};
    private static final String DELIMITER = ",";
    private static final String LPCODE = "3450";
    private static final String TFCODE = "3471";
    private PostRequest request;

    public TideMeasures() {
        getRequest(LPCODE);
    }

    private void getRequest(String bodyNumber){
        this.request = new PostRequest("https://portus.puertos.es/Portus_RT/portusgwt/rpc",
                "https://portus.puertos.es/Portus_RT/portusgwt/",
                "1C515B081663E4BEA92E2671F2079F9A",
                "7|0|6|https://portus.puertos.es/Portus_RT/portusgwt/|4763486D406A92423D6F43998BCFC0EF|es.puertos.portus.main.client.service.PortusService|requestData|I|Z|1|2|3|4|4|5|5|5|6|"+ bodyNumber +"|41|0|1|");
    }

    public double getMeasures(String date){
        String response = request.getResponse();
        String[] lines = response.split(DELIMITER);
        double value = DataTidesExtractor.extract_data_tides(lines, EXPECT_CODE, MAX_ROW, TARGET_ROW, date);

        if(value == -40.) {
            getRequest(TFCODE);
            response = request.getResponse();
            lines = response.split(DELIMITER);
            value = DataTidesExtractor.extract_data_tides(lines, EXPECT_CODE, MAX_ROW, TARGET_ROW, date);
        }
        return value;
    }
}
