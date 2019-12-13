package Code;

public class MetodaSiecznych {

    private double M;
    private double e;
    private double xiMinusOne;
    private double xi;
    private ScalarFunction f;

    public MetodaSiecznych(double m, double e, double xiMinusOne, double xi, ScalarFunction f) {
        M = m;
        this.e = e;
        this.xiMinusOne = xiMinusOne;
        this.xi = xi;
        this.f = f;
    }

    public double methodSolver(double er){
        double xiPlusOne = 0;
        double epsilonA = 1;
        double epsilonT = 0;
        int iteracja = 0;
        while (epsilonA > er){
            xiPlusOne = xi - ((f.getF(M,e,xi)*(xiMinusOne - xi))/(f.getF(M,e,xiMinusOne) - f.getF(M,e,xi)));
            epsilonA = Math.abs((xiPlusOne-xi)/xiPlusOne)*100;
            epsilonT = Math.abs(((0.56714329 - xiPlusOne)/ 0.56714329)*100);
            iteracja++;
            xiMinusOne = xi;
            xi = xiPlusOne;
        }
        return xiPlusOne;
    }
}
