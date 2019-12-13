package Lista5;

import Code.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Kepler {
    public static void main(String[] args) throws IOException {
        final Double [] mercury = {0.387,88.,0.2056};
        final Double [] venus = {0.723, 225.,0.0068};
        final Double [] earth = {1.,365.,0.0167};
        final Double [] mars = {1.524,687.,0.0934};
        final Double [] jupiter = {5.203,11.862*365,0.0484};
        final Double [] saturn = {9.537,29.457*365,0.0542};
        final Double [] uranus = {19.191,84.011*365,0.0472};
        final Double [] neptune = {30.069,164.79*365,0.0086};
        final Double [] pluto = {39.482,247.68*365,0.2488};

        Double [] chosenPlanet = saturn;

        PlanetTrajectory planet = new PlanetTrajectory(chosenPlanet[0], chosenPlanet[1], chosenPlanet[2], new EccentricAnomaly());
        ArrayList<Double> M = planet.getM();
        ArrayList<Double> xTrajectory = new ArrayList<>();
        ArrayList<Double> yTrajectory = new ArrayList<>();
        for (int i = 0; i < planet.getPeriodT(); i++) {
            FixedPointMethod fixedPointMethod1 = new FixedPointMethod(M.get(i) , chosenPlanet[2],5, new EccentricAnomaly());
            Bisection bisection1 = new Bisection(M.get(i),chosenPlanet[2],-100.,100., (Me, e, x) -> Me + e*Math.sin(x) - x);
            RegulaFalsi regulaFalsi = new RegulaFalsi(M.get(i),chosenPlanet[2],-100.,100., (Me, e, x) -> Me + e*Math.sin(x) - x);
            NewtonRaphsonMethod newtonRaphsonMethod = new NewtonRaphsonMethod(M.get(i),chosenPlanet[2],chosenPlanet[0],(Me,e,x) -> Me + e*Math.sin(x) - x);
            MetodaSiecznych metodaSiecznych = new MetodaSiecznych(M.get(i),chosenPlanet[2], 2*chosenPlanet[0], chosenPlanet[0], (Me,e,x) -> Me + e*Math.sin(x) - x);
            xTrajectory.add(planet.xTrajectory(metodaSiecznych.methodSolver(0.0001)));
            yTrajectory.add(planet.yTrajectory(newtonRaphsonMethod.methodSolver(0.5)));
        }

        FileWriter writer = new FileWriter("planetXTrajectory.txt");
        for (Double num: xTrajectory) {
            writer.write(num + System.lineSeparator());
        }

        FileWriter writer1 = new FileWriter("planetYTrajectory.txt");
        for (Double num1: yTrajectory) {
            writer1.write(num1 + System.lineSeparator());
        }
        writer.close();
        writer1.close();
    }


}
