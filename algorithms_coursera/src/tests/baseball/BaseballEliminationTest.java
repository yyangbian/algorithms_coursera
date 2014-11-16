package tests.baseball;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import hw_baseball.BaseballElimination;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.PolynomialRegression;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stopwatch;

public class BaseballEliminationTest {

    private BaseballElimination be;

    @Before
    public void setUp() throws Exception {
        be = new BaseballElimination(
                "src\\tests\\baseball\\teams4.txt");
    }

    @Test
    public void testNumberOfTeams() {
        assertEquals(4, be.numberOfTeams());
    }

    @Test
    public void testTeams() {
        SET<String> expected = new SET<String>();
        expected.add("Atlanta");
        expected.add("Philadelphia");
        expected.add("New_York");
        expected.add("Montreal");
        Iterable<String> teams = be.teams();
        for (String team : teams) {
            //System.out.println(team);
            assertTrue(expected.contains(team));
        }
    }

    @Test
    public void testWins() {
        int wins = 83;
        assertEquals(wins, be.wins("Atlanta"));
        wins = 80;
        assertEquals(wins, be.wins("Philadelphia"));
        wins = 78;
        assertEquals(wins, be.wins("New_York"));
        wins = 77;
        assertEquals(wins, be.wins("Montreal"));
    }

    @Test
    public void testLosses() {
        int losses = 71;
        assertEquals(losses, be.losses("Atlanta"));
        losses = 79;
        assertEquals(losses, be.losses("Philadelphia"));
        losses = 78;
        assertEquals(losses, be.losses("New_York"));
        losses = 82;
        assertEquals(losses, be.losses("Montreal"));
    }

    @Test
    public void testRemaining() {
        int remaining = 8;
        assertEquals(remaining, be.remaining("Atlanta"));
        remaining = 3;
        assertEquals(remaining, be.remaining("Philadelphia"));
        remaining = 6;
        assertEquals(remaining, be.remaining("New_York"));
        remaining = 3;
        assertEquals(remaining, be.remaining("Montreal"));
    }

    @Test
    public void testAgainst() {
        String[] teams = new String[]{"Atlanta", "Philadelphia",
            "New_York", "Montreal"};
        int[][] g = new int[be.numberOfTeams()][];
        g[0] = new int[]{0, 1, 6 ,1};
        g[1] = new int[]{1, 0, 0 ,2};
        g[2] = new int[]{6, 0, 0 ,0};
        g[3] = new int[]{1, 2, 0 ,0};
        for (int i = 0; i < be.numberOfTeams(); i++) {
            for (int j = 0; j < be.numberOfTeams(); j++) {
                assertEquals(g[i][j], be.against(teams[i], teams[j]));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam4() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams4.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Philadelphia");
        eliminated.add("Montreal"); // trivial elimination
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated",
                        division.isEliminated(team));
            } else {
                assertFalse(team + " should not be eliminated",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam4a() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams4a.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Ghaddafi");
        eliminated.add("Bin_Ladin"); // trivial elimination
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated",
                        division.isEliminated(team));
            } else {
                assertFalse(team + " should not be eliminated",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam5() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams5.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Detroit");
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated",
                        division.isEliminated(team));
            } else {
                assertFalse(team + " should not be eliminated",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam7() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams7.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Ireland");
        eliminated.add("China"); // trivial elimination
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated",
                        division.isEliminated(team));
            } else {
                assertFalse(team + " should not be eliminated",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam24() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams24.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team13");
        eliminated.add("Team4"); // trivial elimination
        eliminated.add("Team5"); // trivial elimination
        eliminated.add("Team7"); // trivial elimination
        eliminated.add("Team9"); // trivial elimination
        eliminated.add("Team11"); // trivial elimination
        eliminated.add("Team12"); // trivial elimination
        eliminated.add("Team16"); // trivial elimination
        eliminated.add("Team19"); // trivial elimination
        eliminated.add("Team23"); // trivial elimination
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated",
                        division.isEliminated(team));
            } else {
                assertFalse(team + " should not be eliminated",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam32() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams32.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team25");
        eliminated.add("Team29");
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated non trivially",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam36() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams36.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team21");
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated non trivially",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam42() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams42.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team6");
        eliminated.add("Team15");
        eliminated.add("Team25");
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated non trivially",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam48() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams42.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team6");
        eliminated.add("Team23");
        eliminated.add("Team47");
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated non trivially",
                        division.isEliminated(team));
            }
        }
    }

    @Test
    public void testIsEliminatedTeam54() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams54.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team3");
        eliminated.add("Team29");
        eliminated.add("Team37");
        eliminated.add("Team50");
        for (String team : division.teams()) {
            if (eliminated.contains(team)) {
                assertTrue(team + " should be eliminated non trivially",
                        division.isEliminated(team));
            }
        }
    }

    private double certificateWins(BaseballElimination division,
            Iterable<String> certificate, String eliminated) {
        SET<String> R = new SET<String>();
        for (String team : certificate) {
            R.add(team);
        }
        double result = 0;
        SET<String> checked = new SET<String>();
        for (String team1 : R) {
            result += division.wins(team1);
            checked.add(team1);
            for (String team2 : R) {
                if (checked.contains(team2)) {
                    continue;
                }
                result += division.against(team1, team2);
            }
        }
        result /= R.size();
        return result;
    }

    private String certificateToString(Iterable<String> certificate) {
        if (certificate == null) {
            return null;
        }
        StringBuilder msg = new StringBuilder("{ ");
        for(String t : certificate) {
            msg.append(t);
            msg.append(" ");
        }
        msg.append("}");
        return msg.toString();
    }

    @Test
    public void testCertificateOfEliminationTeam4() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams4.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Philadelphia");
        eliminated.add("Montreal"); // trivial elimination
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam4ReturnNull() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams4.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Atlanta");
        eliminated.add("New_York"); // trivial elimination
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            assertTrue(certificateToString(certificate) + " should be null for "
                    + t, certificate == null);
        }
    }


    @Test
    public void testCertificateOfEliminationTeam5() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams5.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Detroit");
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam7() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams7.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Ireland");
        eliminated.add("China"); // trivial elimination
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam24() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams24.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team13");
        eliminated.add("Team4"); // trivial elimination
        eliminated.add("Team5"); // trivial elimination
        eliminated.add("Team7"); // trivial elimination
        eliminated.add("Team9"); // trivial elimination
        eliminated.add("Team11"); // trivial elimination
        eliminated.add("Team12"); // trivial elimination
        eliminated.add("Team16"); // trivial elimination
        eliminated.add("Team19"); // trivial elimination
        eliminated.add("Team23"); // trivial elimination
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam32() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams32.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team25");
        eliminated.add("Team29");
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam36() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams36.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team21");
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam42() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams42.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team6");
        eliminated.add("Team15");
        eliminated.add("Team25");
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam48() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams48.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team6");
        eliminated.add("Team23");
        eliminated.add("Team47");
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testCertificateOfEliminationTeam54() {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams54.txt");
        SET<String> eliminated = new SET<String>();
        eliminated.add("Team3");
        eliminated.add("Team29");
        eliminated.add("Team37");
        eliminated.add("Team50");
        for (String t : eliminated) {
            Iterable<String> certificate = division.certificateOfElimination(t);
            double wins = certificateWins(division, certificate, t);
            double maxwins = division.wins(t) + division.remaining(t);
            assertTrue(certificateToString(certificate) + " is not right for "
                    + t, wins > maxwins);
        }
    }

    @Test
    public void testTiming() {
        String[] filename = new String[]{"teams30.txt", "teams36.txt",
            "teams42.txt", "teams48.txt", "teams54.txt", "teams60.txt"};
        int n = filename.length;
        double[] t = new double[n];
        double[] sz = new double[]{30, 36, 42, 48, 54, 60};
        String path = "src\\tests\\baseball\\";
        for (int i = 0; i < n; i++) {
            BaseballElimination division = new BaseballElimination(
                    path + filename[i]);
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < 10; j++) {
                for (String team : division.teams()) {
                    division.isEliminated(team);
                    division.certificateOfElimination(team);
                }
            }
            t[i] = sw.elapsedTime();
            System.out.println(division.numberOfTeams() + ": " + t[i]);
        }
        PolynomialRegression regression = new PolynomialRegression(sz, t, 1);
        int order = 1;
        while (Math.abs(regression.R2() - 1) > 0.01) {
            regression = new PolynomialRegression(sz, t, ++order);
        }
        System.out.println(regression);
        System.out.println(regression.beta(order) + " * N^" + order);
    }
}
