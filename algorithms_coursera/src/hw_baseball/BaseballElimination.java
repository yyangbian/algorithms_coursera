package hw_baseball;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class BaseballElimination {

    private int n;  // number of teams
    private SeparateChainingHashST<String, Integer> teams;
    private int[] w, l, r; // stores number of wins, losses and remaining
    private int[][] g; // remaining games vs. other team
    //private int source, target;
    private FordFulkerson ff;
    private double capacity; // max capacity from the source node
    private String checkedTeam;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = in.readInt();
        in.readLine(); // move to the next line
        teams = new SeparateChainingHashST<String, Integer>(n);
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];
        for (int i = 0; i < n; i++) {
            String line = in.readLine().trim();
            String[] fields = line.split("\\s+");
            teams.put(fields[0], i);
            w[i] = Integer.parseInt(fields[1]);
            l[i] = Integer.parseInt(fields[2]);
            r[i] = Integer.parseInt(fields[3]);
            for (int j = 4; j < fields.length; j++) {
                g[i][j - 4] = Integer.parseInt(fields[j]);
            }
        }
        checkedTeam = "";
    }
    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keys();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teams.contains(team)) {
            throw new IllegalArgumentException(team + " does not exist.");
        }
        return w[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teams.contains(team)) {
            throw new IllegalArgumentException(team + " does not exist.");
        }
        return l[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teams.contains(team)) {
            throw new IllegalArgumentException(team + " does not exist.");
        }
        return r[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teams.contains(team1)) {
            throw new IllegalArgumentException(team1 + " does not exist.");
        }
        if (!teams.contains(team2)) {
            throw new IllegalArgumentException(team2 + " does not exist.");
        }
        return g[teams.get(team1)][teams.get(team2)];
    }

    // Build the flow network based on input data
    // assumes n > 2
    // Number of vertex in the FlowNetwork is target + 1
    // target is the last vertex in the graph
    // source corresponds to the team index to check
    // side effect: calculate the capacity from the source node
    private FlowNetwork buildFlowNetwork(int source, int target) {
        FlowNetwork fn = new FlowNetwork(target + 1);
        int v = n; // first vertex index of (teami vs teamj) node
        capacity = 0;
        for (int i = 0; i < n; i++) {
            if (i == source) {
                continue;
            }
            for (int j = i + 1; j < n; j++) {
                if (j == source) {
                    continue;
                }
                fn.addEdge(new FlowEdge(source, v, g[i][j]));
                fn.addEdge(new FlowEdge(v, i, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(v, j, Double.POSITIVE_INFINITY));
                capacity += g[i][j];
                v += 1;
            }
            fn.addEdge(new FlowEdge(i, target, w[source] + r[source] - w[i]));
        }
        return fn;
    }

    // check if team is trivially eliminated w[x] + r[x] < w[i]
    // assumes team is valid
    private Queue<String> trivialEliminationCertificate(String team) {
        int x = teams.get(team);
        double maxwins = w[x] + r[x];
        for (String t : teams()) {
            int i = teams.get(t);
            if (maxwins < w[i]) {
                Queue<String> certificate = new Queue<String>();
                certificate.enqueue(t);
                return certificate;
            }
        }
        return null;
    }

    // check if team is trivially eliminated w[x] + r[x] < w[i]
    // assumes team is valid
    private boolean isTriviallyEliminated(String team) {
        int x = teams.get(team);
        double maxwins = w[x] + r[x];
        for (String t : teams()) {
            int i = teams.get(t);
            if (maxwins < w[i]) {
                return true;
            }
        }
        return false;
    }

    // check if team is trivially eliminated w[x] + r[x] < w[i]
    // assumes team is valid
    private boolean isNonTriviallyEliminated(String team) {
        if (checkedTeam.equals(team)) {
            return ff.value() < capacity;
        }
        checkedTeam = team;
        int source = teams.get(team);
        int target = (n - 1) * (n - 2) / 2 + n;
        // build the flownetwork and calculate capacity
        FlowNetwork fn = buildFlowNetwork(source, target);
        ff = new FordFulkerson(fn, source, target);
        return ff.value() < capacity;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teams.contains(team)) {
            throw new IllegalArgumentException(team + " does not exist.");
        }
        if (isTriviallyEliminated(team)) {
            return true;
        }
        return isNonTriviallyEliminated(team);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.contains(team)) {
            throw new IllegalArgumentException(team + " does not exist.");
        }
        Queue<String> certificate = trivialEliminationCertificate(team);
        if (certificate != null) {
            return certificate;
        }
        if (isNonTriviallyEliminated(team)) {
            checkedTeam = team;
            certificate = new Queue<String>();
            for (String t : teams()) {
                int i = teams.get(t);
                if (!t.equals(team) && ff.inCut(i)) {
                    certificate.enqueue(t);
                }
            }
            return certificate;
        }
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(
                "src\\tests\\baseball\\teams5.txt");
        //division.isEliminated("New_York");
        //BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                //for (String t : division.certificateOfElimination(team)) {
                    //StdOut.print(t + " ");
                //}
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
