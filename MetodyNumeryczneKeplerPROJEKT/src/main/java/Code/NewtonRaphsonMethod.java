package Code;

public class NewtonRaphsonMethod {

    private double M;
    private double e;
    private double xi;
    private ScalarFunction f;

    public NewtonRaphsonMethod(double m, double e, double xi, ScalarFunction f) {
        M = m;
        this.e = e;
        this.xi = xi;
        this.f = f;
    }

    public double methodSolver(double er){
        double xiPlusOne = 0;
        double epsilonA = 1;
        int iteracja = 0;
        double epsilonT = 0;
        while (epsilonA > er){
            xiPlusOne = xi - (f.getF(M,e,xi)/(e*Math.cos(xi) - 1));
            epsilonA = Math.abs((xiPlusOne-xi)/xiPlusOne)*100;
            epsilonT = Math.abs(((0.56714329 - xiPlusOne)/ 0.56714329)*100);
            iteracja++;
            xi = xiPlusOne;
        }
        return xiPlusOne;

    }

}
