package Code;

public class EccentricAnomaly implements ScalarFunction {

    @Override
    public double getF(double M, double e, double x) {
        return M + e*Math.sin(x) - x;
    }
}
