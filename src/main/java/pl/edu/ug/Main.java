package pl.edu.ug;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule();
        Rule ppRule = new PartiallyProbRule();
        byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);

        List<Rule> rules = new ArrayList<>();
        //rules.add(fpRule);
        rules.add(ppRule);

        Simulation simulation = new Simulation(diagonalImg, rules, 1, 99);
        simulation.run();

    }
}
