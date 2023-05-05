package fifteen.puzzle;

import java.util.*;

public class Solution {
    private final GraphNode root;
    private GraphNode goal;
    private StringBuilder path = new StringBuilder();
    private Set<GraphNode> set = new HashSet<>();
    private int maxRecurDepth = 0;
    private long LSO = 0; //liczba stanow odwiedzonych
    private long LSP = 0; //liczba stanow przetworzonych

    public Solution(GraphNode root) {
        this.root = root; //pierwszy element
        setGoal(); //ustawiamy docelowy uklad
    }

    public boolean astr(GraphNode node, String heuristic) {
        if (!(Objects.equals(heuristic, "manh") || Objects.equals(heuristic, "hamm")) || node == null) {
            return false;
        }
        if (node.equals(getGoal())) {
            setLSO(1);
            setLSP(0);
            return true;
        }
        Set<GraphNode> set = new HashSet<>();
        Comparator<GraphNode> comparator; //odpowiada za umieszczanie na kolejce zgodnie z dwoma kryteriami
        if (Objects.equals(heuristic, "manh")) {
            comparator = new ManhattanComparator();
        } else {
            comparator = new HammingComparator();
        }
        PriorityQueue<GraphNode> priorityQueue = new PriorityQueue<>(comparator);
        priorityQueue.add(node);
        String oper = "LRUD";
        while (!priorityQueue.isEmpty()) {
            GraphNode v = priorityQueue.poll();
            if (v.equals(getGoal())) {
                setPath(v);
                setLSP(set.size());
                setLSO(priorityQueue.size() + set.size());
                return true;
            }
            if (!set.contains(v)) {
                set.add(v);
                for (int i = 0; i < 4; i++) {
                    try {
                        GraphNode el = v.createChild(oper.charAt(i));
                        if (!set.contains(el)) {
                            priorityQueue.add(el);
                        }
                    } catch (NullPointerException ignored) {}
                }
            }
        }
        return false;
    }

    public boolean dfs(GraphNode node, String operations, int maxDepth) {
        if (maxDepth == -1 || node == null || operations.length() != 4) {
            return false;
        }
        if (getMaxRecurDepth() < 20 - maxDepth) {
            maxRecurDepth = 20 - maxDepth;
        }
        if (node.equals(getGoal())) {
            setPath(node);
            setLSP(set.size());
            return true;
        }
        set.add(node);
        for (int i = 0; i < 4; i++) {
            try {
                GraphNode el = node.createChild(operations.charAt(i));
                LSO++;
                if (!set.contains(el)) {
                    if (dfs(el, operations, maxDepth - 1)) {
                        return true;
                    }
                }
            } catch (NullPointerException ignored) {}
        }
        setLSP(set.size());
        return false;
    }

    public boolean bfs(GraphNode node, String operations) {
        if (node == null || operations == null || operations.length() != 4) {
            return false;
        }
        if (node.equals(getGoal())) { //jesli element grafu jest juz stanem docelowym
            setLSO(1);
            setLSP(0);
            return true;
        }
        Set<GraphNode> set = new HashSet<>();
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(node); //dodanie do kolejki
        set.add(node); //dodanie do listy stanow zamknietych
        while (!queue.isEmpty()) { //dopoki kolejka nie bedzie pusta
            GraphNode v = queue.poll(); //bierzemy pierwszy element z kolejki
            for (int i = 0; i < 4; i++) { //sprawdzamy sasiadow
                try {
                    GraphNode el = v.createChild(operations.charAt(i));
                    if (el.equals(getGoal())) { //czy boardy sie zgadzaja
                        setPath(el);
                        setLSP(set.size());
                        setLSO(getLSP() + queue.size());
                        return true;
                    }
                    if (!set.contains(el)) { //zwraca pozycje w stosie, a gdy -1 to nie ma
                        queue.add(el); //usuwamy z kolejki
                        set.add(el); //dodajemy do listy stanow zamknietych
                    }
                }
                catch (NullPointerException ignored) {}
            }
        }
        return false;
    }

    public String getPath() {
        return path.toString();
    }

    public void setPath(GraphNode goal) {
        while (goal.getParent() != null) {
            path.append(goal.getOperation());
            goal = goal.getParent();
        }
        if (maxRecurDepth == 0)
            maxRecurDepth = path.length();
        path.reverse();
    }

    private void setGoal() { //ustawia docelowy uklad (tylko boarda)
        goal = new GraphNode(root.getRow(), root.getCol());
        byte n = (byte) (root.getCol() * goal.getRow());
        byte[] res = new byte[n];
        for (byte i = 0; i < n; i++) {
            res[i] = (byte) (i + 1);
        }
        res[n - 1] = 0;
        goal.setBoard(res);
    }

    public GraphNode getGoal() {
        return goal;
    }

    public long getLSO() {
        return LSO;
    }

    public void setLSO(long LSO) {
        this.LSO = LSO;
    }

    public long getLSP() {
        return LSP;
    }

    public void setLSP(long LSP) {
        this.LSP = LSP;
    }

    public int getMaxRecurDepth() {
        return maxRecurDepth;
    }
}
