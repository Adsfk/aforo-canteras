package persistence;

import model.CapacityCalculatorFactory;
import model.Restriction;
import model.RestrictionLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileRestrictionLoader implements RestrictionLoader {

    private final File file;

    public FileRestrictionLoader(File file) {
        this.file = file;
    }

    @Override
    public List<Restriction> load() {
        List<Restriction> restrictions = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            for (String l : bufferedReader.lines().collect(Collectors.toList())) {
                if (l.equals("")) continue;
                String[] line = l.split(";");
                String[] capacityCalculator = line[4].split(":");
                restrictions.add(new Restriction(line[0], line[1], line[2], Boolean.parseBoolean(line[3]),
                        Double.parseDouble(capacityCalculator[1]), capacityCalculator[0],
                        new CapacityCalculatorFactory().build(capacityCalculator[0], Double.parseDouble(capacityCalculator[1]))));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return restrictions;
    }

}
