package pl.edu.ug;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimulationTest {

    Rule fpRule = new FullProbRule();
    Rule ppRule = new PartiallyProbRule();
    byte[][] diagonalImg = Simulation.buildDiagonalImg(100, 100);


    @Test
    public void simulationTest() {


        List<Rule> rules = new ArrayList<>();
        rules.add(fpRule);
        //rules.add(ppRule);

        Simulation simulation = new Simulation(diagonalImg, rules, 1, 99);
        simulation.run();


    }

}
