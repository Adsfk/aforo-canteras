package data_scraper.predicciones;

import data_scraper.DataExtractor;
import data_scraper.PostRequest;

import java.util.HashMap;
import java.util.Map;

public class SwellPredictions {
    public static final String[] EXPECT_1 = {"22", "1"};
    public static final String[] EXPECT_2 = {"21", "1"};
    public static final int MAX_ROW_1 = 4;
    public static final int MAX_ROW_2 = 10;
    public static final int ROW_HS_M = 3;
    public static final int ROW_DIRECTION = 6;
    public static final int ROW_TP_S = 5;
    public static final int ROW_TZ_S = 4;
    public static final String DELIMITER = ",";
    private PostRequest request;

    public SwellPredictions() {
        this.request = new PostRequest("https://portus.puertos.es/Portus_RT/portusgwt/wana",
                "https://portus.puertos.es/Portus_RT/viz/",
                "C9E6BD356A34B9CCC1D638B419348A96",
                "7|0|5|https://portus.puertos.es/Portus_RT/viz/|73487A2D350643620F1AB5E3F8EE9F87|es.puertos.portus.wana.client.service.WanaService|getPred|I|1|2|3|4|2|5|5|4036011|13|");
    }

    public Map<String, Double> getMeasures(){
        String response = request.getResponse();
        Map<String, Double> data = new HashMap<>();
        String[] lines = response.split(DELIMITER);
        data.put("hs_m", DataExtractor.extract_max_data(lines, EXPECT_1, MAX_ROW_1, ROW_HS_M));
        data.put("direccion", DataExtractor.extract_max_data(lines, EXPECT_2, MAX_ROW_2, ROW_DIRECTION));
        data.put("tp_s", DataExtractor.extract_max_data(lines, EXPECT_2, MAX_ROW_2, ROW_TP_S));
        data.put("tz_s", DataExtractor.extract_max_data(lines, EXPECT_2, MAX_ROW_2, ROW_TZ_S));
        return data;
    }
}
