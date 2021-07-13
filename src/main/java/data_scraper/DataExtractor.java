package data_scraper;

import java.util.*;

public class DataExtractor {

    public static double extract_data(String[] lines, String[] expect, int maxRow, int targetRow) {
        List<Double> data = new ArrayList<>();
        int index = 0, row = 0;
        for (String line : lines) {
            if (index == 2 && row < maxRow) {
                if (row == targetRow)
                    data.add(parseDouble(line));
                index = 0;
                row++;
            } else if (row == maxRow) row = 0;
            else if (line.equals(expect[index])) index++;
            else {
                index = 0;
            }
        }
        return getAverageOf(data);
    }

    public static double extract_data(String[] lines, String[] expect_first, String[] expect, int targetRow) {
        List<Double> doubles = new ArrayList<>();
        int index = 0;
        for (String line : lines) {
            if (index == targetRow) {
                doubles.add(parseDouble(line));
                index = 0;
            } else if (line.equals(expect[index]) || line.equals(expect_first[index])) index++;
            else index = 0;
        }
        return getAverageOf(doubles);
    }

    public static double extract_max_data(String[] lines, String[] expect, int maxColumn, int column) {
        List<Double> data = new ArrayList<>();
        int index = 0, row = 0, numberOfPredictions = 0;
        for (String line : lines) {
            if (index == 2 && row < maxColumn) {
                if (row == column) {
                    data.add(parseDouble(line));
                    numberOfPredictions++;
                }
                index = 0;
                row++;
            } else if (row == maxColumn) row = 0;
            else if (line.equals(expect[index])) index++;
            else index = 0;

            if (numberOfPredictions == 73) break;
        }
        return getAverageOf(data);
    }

    private static double parseDouble(String line) {
        return round(Double.parseDouble(line));
    }

    private static double round(double number) {
        return (double) Math.round(number * 1000d) / 1000d;
    }

    private static double getAverageOf(List<Double> values) {
        double av = values.stream()
                .mapToDouble(value -> value)
                .average()
                .orElse(0.0);
        return round(av);
    }
}
