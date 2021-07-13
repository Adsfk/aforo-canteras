package data_scraper.predicciones;

import data_scraper.DataTidesExtractor;
import data_scraper.PostRequest;

public class TidesPredictions {
    public static final String DELIMITER = ",";
    public static final String[] EXPECT = {"11", "1"};
    public static final int MAX_ROW = 3;
    public static final int TARGET_ROW = 1;
    private PostRequest request;

    public TidesPredictions() {
        this.request = new PostRequest("https://portus.puertos.es/Portus_RT/portusgwt/nivmar",
                "https://portus.puertos.es/Portus_RT/viz/",
                "C9E6BD356A34B9CCC1D638B419348A96",
                "7|0|12|https://portus.puertos.es/Portus_RT/viz/|5A4B536623EF8D4FE8DE82C59F711E87|es.puertos.portus.nivmar.client.service.NivmarService|getTidePresureResidual|es.puertos.portus.entities.client.Ubicacion/4135695088|I|java.lang.String/2004016611|null|es.puertos.portus.entities.client.Ubicacion$TipoModelo/3743855414|Las Canteras (Las Palmas de G. Canaria)|es.puertos.portus.entities.client.Ubicacion$TipoUbicacion/3781871145|es|1|2|3|4|4|5|6|6|7|5|34200|1120040|0|0|28.136999130249023|-15.437999725341797|8|3450|9|0|10|0|0|11|0|0|0|13|1|12|");
    }

    public double getMeasures(String date){
        String response = request.getResponse();
        String[] lines = response.split(DELIMITER);
        return DataTidesExtractor.extract_data_tides_prediction(lines, EXPECT,MAX_ROW, TARGET_ROW, date);
    }
}
