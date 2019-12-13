package Code;

public class RegulaFalsi {

    private double M;
    private double e;
    private double xl;
    private double xu;
    private ScalarFunction f;

    public RegulaFalsi(double m, double e, double xl, double xu, ScalarFunction f) {
        M = m;
        this.e = e;
        this.xl = xl;
        this.xu = xu;
        this.f = f;
    }

    public double methodSolver( double er) {
        double xr = 0;
        double functionXr = 1;
        double xrOld = 0;
        double epsilonA = 1;
        double epsilonT = 0;
        int iteracja = 0;
        while (epsilonA > er) {
            if (f.getF(M, e, xl) * f.getF(M, e, xu) < 0) {
                iteracja++;
                xr = xu - (f.getF(M, e, xu) * (xl - xu)) / (f.getF(M, e, xl) - f.getF(M, e, xu));
                if (xrOld != 0) {
                    epsilonA = Math.abs((xr - xrOld) / xr) * 100;
                }
                epsilonT = Math.abs(((0.56714329 - xr) / 0.56714329) * 100);
                functionXr = f.getF(M, e, xr);
                if (functionXr == 0) {
                    return xr;
                }
                if (functionXr != 0) {
                    if (f.getF(M, e, xl) * functionXr < 0)
                        xu = xr;
                    else if (functionXr * f.getF(M, e, xu) < 0)
                        xl = xr;
                }
                xrOld = xr;
            }
        }

        return xr;

    }
}
