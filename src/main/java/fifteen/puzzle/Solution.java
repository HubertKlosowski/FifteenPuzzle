package fifteen.puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Solution {
    private final GraphNode root;
    private GraphNode goal;
    private StringBuilder path = new StringBuilder();
    private Queue<GraphNode> queue = new LinkedList<>();
    private Set<GraphNode> set = new HashSet<>();
    private Stack<GraphNode> stack = new Stack<>();
    private long LSO = 0; //liczba stanow odwiedzonych
    private long LSP = 0; //liczba stanow przetworzonych
    private int maxRecur = 0; //maksymalna glebokosc rekursji

    public Solution(GraphNode root) {
        this.root = root; //pierwszy element
        setGoal(); //ustawiamy docelowy uklad
    }

    public boolean dfs(GraphNode node, String operations) {
        if (node == null || operations == null || operations.length() != 4)
            return false;
        if (Arrays.equals(node.getBoard(), getGoal().getBoard()))
            return true;
        stack.push(node);
        int depth = 0;
        while (!stack.isEmpty()) {
            setMaxRecur(depth);
            LSO++;
            GraphNode v = stack.pop();
            set.add(v);
            /*ArrayList<GraphNode> neigh = v.getNeighbours(operations);
            for (GraphNode el : neigh) {
                if (Arrays.equals(el.getBoard(), getGoal().getBoard())) {
                    setPath(el);
                    return true;
                } if (!set.contains(el) && !stack.contains(el)) {
                    stack.push(el);
                }
            }*/
        }
        set.clear();
        return false;
    }

    public boolean bfs(GraphNode node, String operations) {
        if (node == null || operations == null || operations.length() != 4)
            return false;
        if (Arrays.equals(node.getBoard(), getGoal().getBoard())) //jesli element grafu jest juz stanem docelowym
            return true;
        queue.add(node); //dodanie do kolejki
        set.add(node); //dodanie do stosu
        while (!queue.isEmpty()) { //dopoki kolejka nie bedzie pusta
            LSO++;
            GraphNode v = queue.poll(); //bierzemy pierwszy element z kolejki
            for (int i = 0; i < 4; i++) { //sprawdzamy sasiadow
                try {
                    GraphNode el = v.createChild(operations.charAt(i));
                    if (Arrays.equals(el.getBoard(), getGoal().getBoard())) { //czy boardy sie zgadzaja
                        setPath(el);
                        return true;
                    } if (!set.contains(el)) { //zwraca pozycje w stosie, a gdy -1 to nie ma
                        queue.add(el); //usuwamy z kolejki
                        set.add(el); //dodajemy do stosu
                    }
                }
                catch (NullPointerException ignored) {

                }
            }
        }
        set.clear();
        return false;
    }

    public String getPath() {
        return path.toString();
    }

    public void setPath(GraphNode goal) {
        while (goal.getParent() != null) {
            LSP++;
            path.append(goal.getOperation());
            goal = goal.getParent();
        }
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

    public int getMaxRecur() {
        return maxRecur;
    }

    public void setMaxRecur(int depth) {
        if (this.maxRecur < depth) {
            this.maxRecur = depth;
        }
    }
}
