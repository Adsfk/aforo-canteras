import model.interpolation.LasCanterasSurfaceModel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LasCanterasSurfaceModel_ {

    @Test
    public void should_calculate_surface_with_1_3_and_return_2_565() {
        LasCanterasSurfaceModel model = new LasCanterasSurfaceModel(1);
        double y = 2564.788;
        assertThat(model.surface(1.3)).isEqualTo(y);
    }
}
