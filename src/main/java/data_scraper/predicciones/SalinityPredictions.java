package data_scraper.predicciones;

import data_scraper.DataExtractor;
import data_scraper.PostRequest;

public class SalinityPredictions {
    public static final String DELIMITER = ",";
    public static final String[] EXPECT_FIRST = {"72", "0", "-88", "0", "1"};
    public static final String[] EXPECT = {"1", "9", "5", "2", "1"};
    public static final int TARGET_ROW = 5;
    private PostRequest request;

    public SalinityPredictions() {
        this.request = new PostRequest("https://portus.puertos.es/Portus_RT/portusgwt/cirana",
                "https://portus.puertos.es/Portus_RT/viz/",
                "C9E6BD356A34B9CCC1D638B419348A96",
                "7|0|5|https://portus.puertos.es/Portus_RT/viz/|144D6E39F03957CAABBF10B1806D1753|es.puertos.portus.cirana.client.service.CiranaService|getPred|I|1|2|3|4|3|5|5|5|4036011|13|10|");
    }

    public double getMeasures(){
        String response = request.getResponse();
        String[] lines = response.split(DELIMITER);
        return DataExtractor.extract_data(lines, EXPECT_FIRST, EXPECT, TARGET_ROW);
    }
}
