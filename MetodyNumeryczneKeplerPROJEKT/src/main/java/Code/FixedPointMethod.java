package Code;

public class FixedPointMethod {
    private double M;
    private double e;
    private double x0;
    private ScalarFunction f;

    public FixedPointMethod(double M, double e, double x0, ScalarFunction f) {
        this.x0 = x0;
        this.f = f;
        this.M = M;
        this.e = e;
    }

    public double methodSolver(double er){
        double x1 = 0;
        double epsilonA = 1;
        double epsilonT = 0;
        int iteracja = 0;
        while (epsilonA > er) {
            x1 = f.getF(M, e, x0) + x0;
            epsilonA = Math.abs((x1-x0)/x1)*100;
            epsilonT = Math.abs(((0.56714329 - x1)/ 0.56714329)*100);
            iteracja++;
            //System.out.println("Iteracja: " + iteracja + ", x(iteracja): " + x0 + ", x(iteracja + 1): " + x1 + ", epsilonA: " + epsilonA + " %, epsilonT: " + epsilonT + " %");
            //System.out.println();
            x0 = x1;
        }
        return x1;
    }
}
