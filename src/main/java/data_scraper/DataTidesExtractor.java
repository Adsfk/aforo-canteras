package data_scraper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataTidesExtractor {

    public static final int ERROR_CODE = -40;
    public static final double NUMBER_OF_DIGITS = 1000d;

    public static double extract_data_tides(String[] lines, String[] code, int maxRow, int targetRow, String date) {
        List<Double> data = new ArrayList<>();
        List<String> dates = getTimeStamps(lines);

        int codePosition = 0, row = 0;
        for (String line : lines) {
            if (isCodeApproved(codePosition) && !endOfRecord(maxRow, row)) {
                if (isSpecialValue(row, line)) codePosition = 0;
                else {
                    if (isTargetRow(targetRow, row)) data.add(parseDouble(line));
                    codePosition = 0;
                    row++;
                }
            } else if (endOfRecord(maxRow, row)) row = 0;
            else if (isPartOfCode(line, code[codePosition])) codePosition++;
            else codePosition = 0;
        }

        return round(getAverageForDate(date, data, dates));
    }

    public static double extract_data_tides_prediction(String[] lines, String[] code, int maxRow, int targetRow, String date) {
        List<Double> data = new ArrayList<>();
        List<String> dates = getTimeStamps(lines);
        int codePosition = 0, row = 0;

        for (String line : lines) {
            if (isCodeApproved(codePosition) && !endOfRecord(maxRow, row)) {
                if (isTargetRow(targetRow, row)) data.add(parseDouble(line));
                codePosition = 0;
                row++;
            } else if (endOfRecord(maxRow, row)) row = 0;
            else if (isPartOfCode(line, code[codePosition])) codePosition++;
            else codePosition = 0;
        }

        return round(getAverageForDate(date, data, dates));
    }

    private static List<String> getTimeStamps(String[] lines) {
        return Arrays.stream(lines)
                .map(DataTidesExtractor::formatDate)
                .filter(DataTidesExtractor::isDate)
                .collect(Collectors.toList());
    }

    private static double getAverageForDate(String date, List<Double> data, List<String> dates) {
        return getPairs(data, dates)
                .filter(record -> isRecordOfDate(date, record))
                .mapToDouble(Map.Entry::getValue)
                .average()
                .orElse(ERROR_CODE);
    }

    private static Stream<Map.Entry<String, Double>> getPairs(List<Double> data, List<String> dates) {
        return getMapOf(dates, data)
                .entrySet().stream();
    }

    private static Map<String, Double> getMapOf(List<String> dates, List<Double> data){
        Collections.reverse(data);
        return IntStream.range(0, data.size())
                .boxed()
                .collect(Collectors.toMap(dates::get, data::get));
    }

    private static boolean isRecordOfDate(String date, Map.Entry<String, Double> record) {
        return record.getKey().startsWith(date);
    }

    private static boolean isPartOfCode(String line, String numberOfCode) {
        return line.equals(numberOfCode);
    }

    private static boolean endOfRecord(int maxRow, int row) {
        return row == maxRow;
    }

    private static boolean isSpecialValue(int row, String line) {
        return row == 0 && !isPartOfCode(line, "13");
    }

    private static boolean isCodeApproved(int index) {
        return index == 2;
    }

    private static boolean isTargetRow(int targetRow, int row) {
        return row == targetRow;
    }

    private static boolean isDate(String line) {
        line = formatDate(line);
        return line.matches("\\d{1,2}-\\d{1,2}-\\d{4} \\d{2}:\\d{2}:00");
    }

    private static String formatDate(String line) {
        line = line.replace("\"", "");
        line = line.replace("]", "");
        line = line.replace("[", "");
        return line;
    }

    private static double parseDouble(String line) {
        return round(Double.parseDouble(line));
    }

    private static double round(double number) {
        return (double) Math.round(number * NUMBER_OF_DIGITS) / NUMBER_OF_DIGITS;
    }
}
