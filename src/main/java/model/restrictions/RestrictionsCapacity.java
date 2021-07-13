package model.restrictions;

import model.Restriction;
import persistence.FileRestrictionLoader;

import java.io.File;
import java.util.List;

public class RestrictionsCapacity {

    private List<Restriction> restrictions;
    private static RestrictionsCapacity restrictionsCapacity;

    public static RestrictionsCapacity getInstance() {
        if (restrictionsCapacity == null)
            restrictionsCapacity = new RestrictionsCapacity();
        return restrictionsCapacity;
    }

    private RestrictionsCapacity() {
        String home = System.getProperty("user.home")+"/data/restrictions.csv";
        FileRestrictionLoader loader = new FileRestrictionLoader(new File(home));
        restrictions = loader.load();
    }

    public void switchActive(List<Integer> statesIndex) {
        restrictions.forEach(r->r.setActive(false));
        statesIndex.forEach(index->restrictions.get(index).setActive(true));
    }

    public List<Restriction> getRestrictionsCapacity() {
        return restrictions;
    }

}
