package pl.edu.ug;

import org.junit.Test;
import pl.edu.ug.rule.FullProbRule;
import pl.edu.ug.rule.PartiallyProbRule;
import pl.edu.ug.rule.Rule;

public class SimulationTest {

    Rule fpRule = new FullProbRule();
    Rule ppRule = new PartiallyProbRule();
    byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);


    @Test
    public void simulationTest() {


    }

}
