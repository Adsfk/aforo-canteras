import model.interpolation.Interpolation;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class InterpolationTest {
    @Test
    public void should_interpolate_two_points_on_minus_1_and_get_3_905() {
        double x1 = -1.434, x2 = -0.896;
        double y1 = 4.206, y2 = 3.833;
        Interpolation inter = new Interpolation(x1,x2,y1,y2);
        double x = -1;
        double y = 3.905;
        assertThat(inter.calculate().apply(x)).isEqualTo(y);
    }

    @Test
    public void should_interpolate_two_points_on_minus_1_245_and_get_4_075() {
        double x1 = -1.434, x2 = -0.896;
        double y1 = 4.206, y2 = 3.833;
        Interpolation inter = new Interpolation(x1,x2,y1,y2);
        double x = -1.245;
        double y = 4.075;
        assertThat(inter.calculate().apply(x)).isEqualTo(y);
    }

}
