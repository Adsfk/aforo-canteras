import model.interpolation.LasCanterasZonesModel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LasCanterasZonesModel_ {
    @Test
    public void should_calculate_zone_1_surface_with_given_tide() {
        LasCanterasZonesModel lcz = new LasCanterasZonesModel();
        assertThat(lcz.calculateSurfaces(1.5).get(0)).isEqualTo(19225.165);
    }
}
