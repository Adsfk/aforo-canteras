package persistence;

import model.DatalakePersistence;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class DatalakePersistenceFile implements DatalakePersistence {
    private static final String TIMESTAMP = "timestamp";
    private static final String HEIGHT = "height";
    private static final double ERROR_CODE = -40.;
    private final String route;

    public DatalakePersistenceFile() {
        String home = System.getProperty("user.home") + "/data/seaheight" + currentDateToString(getCurrentTime()) + ".txt";
        this.route = home;
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    private String currentDateToString(LocalDateTime currentTime) {
        return currentTime.getYear() + formatNumberOfDate(currentTime.getMonthValue());
    }

    private String formatNumberOfDate(int field) {
        return field < 10 ? "0" + field : field + "";
    }

    @Override
    public void write(JSONObject datalake) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(route, true))) {
            bufferedWriter.append(datalake.toString());
            bufferedWriter.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double getHeight(String date) {
        String formatedDate = date.split("-")[0] + date.split("-")[1];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/data/seaheight" + formatedDate + ".txt"))) {
            JsonObject record = getRecordFor(date, bufferedReader);
            if (record != null) return record.get(HEIGHT).getAsDouble();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    private JsonObject getRecordFor(String date, BufferedReader bufferedReader) {
        return bufferedReader.lines()
                .map(this::getAsJson)
                .filter(record -> isFromDate(date, record))
                .findFirst()
                .orElse(null);
    }

    private boolean isFromDate(String date, JsonObject json) {
        return json.get(TIMESTAMP).getAsString().equals(date);
    }

    private JsonObject getAsJson(String line) {
        return JsonParser.parseString(line).getAsJsonObject();
    }
}

