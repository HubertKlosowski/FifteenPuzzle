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
    private long LSO = 0; //liczba stanow odwiedzonych
    private long LSP = 0; //liczba stanow przetworzonych

    public Solution(GraphNode root) {
        this.root = root; //pierwszy element
        setGoal(); //ustawiamy docelowy uklad
    }

    public boolean dfs(GraphNode node, String operations, int maxDepth) {
        if (node == null || operations == null || operations.length() != 4)
            return false;
        if (Arrays.equals(node.getBoard(), getGoal().getBoard()))
            return true;
        Set<GraphNode> set = new HashSet<>();
        Stack<GraphNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            LSO++;
            GraphNode v = stack.pop();
            set.add(v);
            for (int i = 0; i < 4; i++) {
                try {
                    GraphNode el = v.createChild(Character.valueOf(operations.charAt(i)));
                    if (Arrays.equals(el.getBoard(), getGoal().getBoard())) {
                        setPath(el);
                        return true;
                    } if (!set.contains(el) && !stack.contains(el)) {
                        stack.push(el);
                    }
                }
                catch (NullPointerException ignored) {

                }
            }
        }
        return false;
    }

    public boolean bfs(GraphNode node, String operations) {
        if (node == null || operations == null || operations.length() != 4)
            return false;
        if (Arrays.equals(node.getBoard(), getGoal().getBoard())) //jesli element grafu jest juz stanem docelowym
            return true;
        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> set = new HashSet<>();
        queue.add(node); //dodanie do kolejki
        set.add(node); //dodanie do stosu
        while (!queue.isEmpty()) { //dopoki kolejka nie bedzie pusta
            LSO++;
            GraphNode v = queue.poll(); //bierzemy pierwszy element z kolejki
            for (int i = 0; i < 4; i++) { //sprawdzamy sasiadow
                try {
                    GraphNode el = v.createChild(Character.valueOf(operations.charAt(i)));
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
}
