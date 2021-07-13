package model.interpolation;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class LasCanterasSurfaceModel {
    private static double[] heights;
    private static Map<Integer, double[]> sectors = new HashMap<>();
    private final double[] sector;

    public LasCanterasSurfaceModel(int sector) {
        this.sector = LasCanterasSurfaceModel.sectors.get(sector);
    }

    public double surface(double seaHeight) {
        return calculateWith(seaHeight);
    }

    private double calculateWith(double seaHeight) {
        return getInterpolation(definedBy(seaHeight)).calculate().apply(seaHeight);
    }

    private Interpolation getInterpolation(int index) {
        index = fixIndex(index);
        return new Interpolation(getHeightFromModel(index), getHeightFromModel(index + 1),
                sector[index], sector[index + 1]);
    }

    private int definedBy(double seaHeight) {
        OptionalInt indexOpt = findReferentialHeightIndex(seaHeight);
        return (indexOpt.isPresent()) ? indexOpt.getAsInt() : 4;
    }

    private double getHeightFromModel(int index) {
        return LasCanterasSurfaceModel.heights[index];
    }

    private int fixIndex(int index) {
        return (index > 0) ? index - 1 : index;
    }

    private OptionalInt findReferentialHeightIndex(double seaHeight) {
        return IntStream.range(0, heights.length)
                .filter(i -> seaHeight < heights[i])
                .findFirst();
    }

    static {
        LasCanterasSurfaceModel.heights = new double[]{-1.434, -0.896, 0, 0.843, 1.443};
        LasCanterasSurfaceModel.sectors.put(1, new double[]{4206, 3833, 3457, 2954, 2443});
        LasCanterasSurfaceModel.sectors.put(2, new double[]{6140, 5704, 5257, 4796, 4316});
        LasCanterasSurfaceModel.sectors.put(3, new double[]{4831, 4389, 3928, 3434, 2919});
        LasCanterasSurfaceModel.sectors.put(4, new double[]{5260, 4602, 3918, 3206, 2460});
        LasCanterasSurfaceModel.sectors.put(5, new double[]{6271, 5647, 5003, 4338, 3644});
        LasCanterasSurfaceModel.sectors.put(6, new double[]{6606, 5945, 5257, 4541, 3794});
        LasCanterasSurfaceModel.sectors.put(7, new double[]{5496, 4794, 4079, 3347, 2594});
        LasCanterasSurfaceModel.sectors.put(8, new double[]{7667, 6687, 5711, 4735, 3751});
        LasCanterasSurfaceModel.sectors.put(9, new double[]{5491, 4793, 4086, 3367, 2625});
        LasCanterasSurfaceModel.sectors.put(10, new double[]{3636, 3118, 2598, 2079, 1552});
        LasCanterasSurfaceModel.sectors.put(11, new double[]{2500, 2041, 1572, 1092, 607});
        LasCanterasSurfaceModel.sectors.put(12, new double[]{2563, 2037, 1514, 991, 467});
        LasCanterasSurfaceModel.sectors.put(13, new double[]{5939, 4575, 3227, 1891, 574});
        LasCanterasSurfaceModel.sectors.put(14, new double[]{2763, 2252, 1732, 1206, 676});
        LasCanterasSurfaceModel.sectors.put(15, new double[]{1675, 1482, 1091, 738, 253});
        LasCanterasSurfaceModel.sectors.put(16, new double[]{52, 52, 52, 50, 7});
        LasCanterasSurfaceModel.sectors.put(17, new double[]{2316, 1753, 1211, 692, 183});
        LasCanterasSurfaceModel.sectors.put(18, new double[]{4337, 3547, 2709, 1824, 901});
        LasCanterasSurfaceModel.sectors.put(19, new double[]{6062, 5013, 3939, 2821, 1642});
        LasCanterasSurfaceModel.sectors.put(20, new double[]{5631, 4443, 3248, 2049, 859});
        LasCanterasSurfaceModel.sectors.put(21, new double[]{3051, 2317, 1575, 825, 78});
        LasCanterasSurfaceModel.sectors.put(22, new double[]{1178, 849, 549, 276, 20});
        LasCanterasSurfaceModel.sectors.put(23, new double[]{4663, 3288, 2061, 988, 95});
        LasCanterasSurfaceModel.sectors.put(24, new double[]{6680, 5373, 4010, 2541, 938});
        LasCanterasSurfaceModel.sectors.put(25, new double[]{9236, 7582, 5895, 4149, 2352});
        LasCanterasSurfaceModel.sectors.put(26, new double[]{5201, 4106, 2996, 1870, 728});
        LasCanterasSurfaceModel.sectors.put(27, new double[]{3733, 2859, 1997, 1143, 290});
        LasCanterasSurfaceModel.sectors.put(28, new double[]{7591, 5865, 4134, 2393, 639});
        LasCanterasSurfaceModel.sectors.put(29, new double[]{13597, 10726, 7760, 4683, 1488});
        LasCanterasSurfaceModel.sectors.put(30, new double[]{6191, 4687, 3181, 1673, 175});
        LasCanterasSurfaceModel.sectors.put(31, new double[]{11517, 8836, 6047, 3134, 133});
        LasCanterasSurfaceModel.sectors.put(32, new double[]{6823, 5414, 3891, 2252, 507});
    }

}
