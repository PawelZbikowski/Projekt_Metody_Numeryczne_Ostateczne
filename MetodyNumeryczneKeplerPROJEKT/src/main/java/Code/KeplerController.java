package Code;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

public class KeplerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem menuItemSave;

    @FXML
    private MenuItem menuItemClear;

    @FXML
    private RadioMenuItem radioMenuBisection;

    @FXML
    private ToggleGroup tg;

    @FXML
    private RadioMenuItem radioMenuRegulaFalsi;

    @FXML
    private RadioMenuItem radioMenuFixedPointMethod;

    @FXML
    private RadioMenuItem radioMenuNewton;

    @FXML
    private RadioMenuItem radioMenuMetodaSiecznych;

    @FXML
    private TextField textFieldDistance;

    @FXML
    private TextField textFieldRevolution;

    @FXML
    private TextField textFieldEccentricity;

    @FXML
    private TextField textFieldSigmaA;

    @FXML
    private Button btnDrawTrajectories;

    @FXML
    private ScatterChart<Double,Double> ScatterChart;

    @FXML
    void buttonDrawPressed(ActionEvent event) {
        double distance = Double.parseDouble(textFieldDistance.getText());
        String revolution = textFieldRevolution.getText();
        double eccentricity = Double.parseDouble(textFieldEccentricity.getText());
        double sigmaA = Double.parseDouble(textFieldSigmaA.getText());
        double revolution1 = 0;

        if(revolution.contains("d")) {
            String revolutionString = (String) revolution.subSequence(0,revolution.length()-1);
            revolution1 = Double.parseDouble(revolutionString);
        }
        if(revolution.contains("y")) {
            String revolutionString = (String) revolution.subSequence(0,revolution.length()-1);
            revolution1 = Double.parseDouble(revolutionString);
            revolution1 *= 365;
        }

        PlanetTrajectory planet = new PlanetTrajectory(distance,revolution1,eccentricity, (Me,e,x) -> Me + e*Math.sin(x) - x);

        ArrayList<Double> M = planet.getM();
        ArrayList<Double> xTrajectory = new ArrayList<>();
        ArrayList<Double> yTrajectory = new ArrayList<>();
        for (int i = 0; i < planet.getPeriodT(); i++) {
            if(radioMenuBisection.isSelected()) {
                Bisection bisection = new Bisection(M.get(i),eccentricity,-100.,100., (Me,e,x) -> Me + e*Math.sin(x) - x);
                xTrajectory.add(planet.xTrajectory(bisection.methodSolver(sigmaA)));
                yTrajectory.add(planet.yTrajectory(bisection.methodSolver(sigmaA)));
            }
            else if(radioMenuRegulaFalsi.isSelected()) {
                RegulaFalsi regulaFalsi = new RegulaFalsi(M.get(i),eccentricity,-100.,100., (Me,e,x) -> Me + e*Math.sin(x) - x);
                xTrajectory.add(planet.xTrajectory(regulaFalsi.methodSolver(sigmaA)));
                yTrajectory.add(planet.yTrajectory(regulaFalsi.methodSolver(sigmaA)));
            }
            else if(radioMenuFixedPointMethod.isSelected()) {
                FixedPointMethod fixedPointMethod1 = new FixedPointMethod(M.get(i) , eccentricity,5, (Me,e,x) -> Me + e*Math.sin(x) - x);
                xTrajectory.add(planet.xTrajectory(fixedPointMethod1.methodSolver(sigmaA)));
                yTrajectory.add(planet.yTrajectory(fixedPointMethod1.methodSolver(sigmaA)));
            }
            else if(radioMenuNewton.isSelected()) {
                NewtonRaphsonMethod newtonRaphsonMethod = new NewtonRaphsonMethod(M.get(i), eccentricity, distance, (Me, e, x) -> Me + e * Math.sin(x) - x);
                xTrajectory.add(planet.xTrajectory(newtonRaphsonMethod.methodSolver(sigmaA)));
                yTrajectory.add(planet.yTrajectory(newtonRaphsonMethod.methodSolver(sigmaA)));
            }
            else if(radioMenuMetodaSiecznych.isSelected()) {
                MetodaSiecznych metodaSiecznych = new MetodaSiecznych(M.get(i), eccentricity, 2 * distance, distance, (Me, e, x) -> Me + e * Math.sin(x) - x);
                xTrajectory.add(planet.xTrajectory(metodaSiecznych.methodSolver(sigmaA)));
                yTrajectory.add(planet.yTrajectory(metodaSiecznych.methodSolver(sigmaA)));
            }
        }

        XYChart.Series<Double,Double> series = new XYChart.Series<>();
        for (int i = 0; i < xTrajectory.size(); i++)
            series.getData().add(new XYChart.Data(xTrajectory.get(i), yTrajectory.get(i)));

        ScatterChart.getData().add(series);
        if(distance >=0.37 && distance <= 0.4)
            series.setName("Merkury");
        else if (distance >= 0.7 && distance <= 0.8)
            series.setName("Wenus");
        else if (distance >= 0.9 && distance <= 1.1)
            series.setName("Ziemia");
        else if (distance >= 1.5 && distance <= 1.6)
            series.setName("Mars");
        else if (distance >= 5.2 && distance <= 5.3)
            series.setName("Jowisz");
        else if (distance >= 9.5 && distance <= 9.6)
            series.setName("Saturn");
        else if (distance >= 19.1 && distance <= 19.3)
            series.setName("Uran");
        else if (distance >= 30. && distance <= 30.1)
            series.setName("Neptun");
        else if (distance >= 39.4 && distance <= 39.5)
            series.setName("Pluton");

    }

    @FXML
    void menuItemCleanPressed(ActionEvent event) {
        ScatterChart.getData().clear();

    }

    @FXML
    void menuItemSavePressed(ActionEvent event) {
        saveAsPNG();

    }

    @FXML
    void saveAsPNG(){
        WritableImage image = ScatterChart.snapshot(new SnapshotParameters(),null);

        File file = new File("Trajectory.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null),"png",file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        assert menuItemSave != null : "fx:id=\"menuItemSave\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert menuItemClear != null : "fx:id=\"menuItemClear\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert radioMenuBisection != null : "fx:id=\"radioMenuBisection\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert tg != null : "fx:id=\"tg\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert radioMenuRegulaFalsi != null : "fx:id=\"radioMenuRegulaFalsi\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert radioMenuFixedPointMethod != null : "fx:id=\"radioMenuFixedPointMethod\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert radioMenuNewton != null : "fx:id=\"radioMenuNewton\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert radioMenuMetodaSiecznych != null : "fx:id=\"radioMenuMetodaSiecznych\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert textFieldDistance != null : "fx:id=\"textFieldDistance\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert textFieldRevolution != null : "fx:id=\"textFieldRevolution\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert textFieldEccentricity != null : "fx:id=\"textFieldEccentricity\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert textFieldSigmaA != null : "fx:id=\"textFieldSigmaA\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert btnDrawTrajectories != null : "fx:id=\"btnDrawTrajectories\" was not injected: check your FXML file 'Version1_2.fxml'.";
        assert ScatterChart != null : "fx:id=\"ScatterChart\" was not injected: check your FXML file 'Version1_2.fxml'.";

    }


}
