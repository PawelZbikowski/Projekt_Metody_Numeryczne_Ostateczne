package Code;

import java.util.ArrayList;

public class PlanetTrajectory {

    private double a;
    private double periodT;
    private double eccentricity;
    private ScalarFunction f;

    public PlanetTrajectory(double a, double periodT, double eccentricity, ScalarFunction f) {
        this.a = a;
        this.periodT = periodT;
        this.eccentricity = eccentricity;
        this.f = f;
    }

    public double getPeriodT() {
        return periodT;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public double getA() {
        return a;
    }

    public ArrayList<Double> getM() {
        ArrayList<Double> meanAnomaly = new ArrayList<>();
        for (int i = 0; i < periodT; i++) {
            meanAnomaly.add(((2*Math.PI)/periodT)*i);
        }
        return meanAnomaly;
    }

    public double xTrajectory(double E){
        return a*Math.cos(E - eccentricity);
    }

    public double yTrajectory(double E){
        return a*Math.sqrt(1 - Math.pow(eccentricity,2))*Math.sin(E);
    }


}
