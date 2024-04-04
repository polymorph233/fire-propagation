import org.example.core.Game;
import org.example.core.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestBoardGame {

    @org.junit.Test
    public void testInCaseAlwaysSetOnFire() {
        List<Pair<Integer, Integer>> initFires = new ArrayList<>();
        initFires.add(new Pair<>(1, 2));

        var game = new Game(5, 5, 1.0, initFires);

        var step1 = new StringBuilder();
        step1.append("OOOOO\n");
        step1.append("OOXOO\n");
        step1.append("OOOOO\n");
        step1.append("OOOOO\n");
        step1.append("OOOOO\n");
        assertEquals(game.peek(), step1.toString());

        game.nextTurn();

        var step2 = new StringBuilder();
        step2.append("OOXOO\n");
        step2.append("OX_XO\n");
        step2.append("OOXOO\n");
        step2.append("OOOOO\n");
        step2.append("OOOOO\n");
        assertEquals(game.peek(), step2.toString());

        game.nextTurn();

        var step3 = new StringBuilder();
        step3.append("OX_XO\n");
        step3.append("X___X\n");
        step3.append("OX_XO\n");
        step3.append("OOXOO\n");
        step3.append("OOOOO\n");
        assertEquals(game.peek(), step3.toString());
    }
}

