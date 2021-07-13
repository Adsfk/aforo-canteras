package data_scraper.predicciones;

import data_scraper.DataExtractor;
import data_scraper.PostRequest;

public class WaterTempPredictions {
    public static final String DELIMITER = ",";
    public static final String[] EXPECT_CODE = {"11", "1"};
    public static final int MAX_ROW = 3;
    public static final int TARGET_ROW = 0;
    private PostRequest request;

    public WaterTempPredictions() {
        this.request = new PostRequest("https://portus.puertos.es/Portus_RT/portusgwt/cirana",
                "https://portus.puertos.es/Portus_RT/viz/",
                "C9E6BD356A34B9CCC1D638B419348A96",
                "7|0|5|https://portus.puertos.es/Portus_RT/viz/|144D6E39F03957CAABBF10B1806D1753|es.puertos.portus.cirana.client.service.CiranaService|getPred|I|1|2|3|4|3|5|5|5|4036011|13|10|");
    }

    public double getMeasures(){
        String response = request.getResponse();
        String[] lines = response.split(DELIMITER);
        return DataExtractor.extract_data(lines, EXPECT_CODE, MAX_ROW, TARGET_ROW);
    }
}
