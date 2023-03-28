package fifteen.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
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
    private PriorityQueue<GraphNode> priorityQueue = new PriorityQueue<>();
    private long LSO = 0; //liczba stanow odwiedzonych
    private long LSP = 0; //liczba stanow przetworzonych

    public Solution(GraphNode root) {
        this.root = root; //pierwszy element
        setGoal(); //ustawiamy docelowy uklad
    }

    public boolean dfs(GraphNode node, String operations) {
        if (Arrays.deepEquals(node.getBoard(), getGoal().getBoard()))
            return true;
        stack.push(node);
        while (!stack.isEmpty()) {
            LSO++;
            GraphNode v = stack.pop();
            set.add(v);
            ArrayList<GraphNode> neigh = v.getNeighbours(operations);
            Collections.reverse(neigh);
            for (GraphNode el : neigh) {
                if (Arrays.deepEquals(el.getBoard(), getGoal().getBoard())) {
                    setPath(el);
                    return true;
                } if (!set.contains(el) && !stack.contains(el)) {
                    if (Arrays.deepEquals(el.getBoard(), getGoal().getBoard())) {
                        return true;
                    }
                    stack.push(el);
                }
            }
        }
        set.clear();
        return false;
    }

    public boolean bfs(GraphNode node, String operations) {
        if (Arrays.deepEquals(node.getBoard(), getGoal().getBoard())) //jesli element grafu jest juz stanem docelowym
            return true;
        queue.add(node); //dodanie do kolejki
        set.add(node); //dodanie do stosu
        int depth = 0;
        while (!queue.isEmpty()) { //dopoki kolejka nie bedzie pusta
            LSO++;
            GraphNode v = queue.poll(); //bierzemy pierwszy element z kolejki
            for (GraphNode el : v.getNeighbours(operations)) { //sprawdzamy sasiadow
                if (Arrays.deepEquals(el.getBoard(), getGoal().getBoard())) { //czy boardy sie zgadzaja
                    setPath(el);
                    return true;
                } if (!set.contains(el)) { //zwraca pozycje w stosie, a gdy -1 to nie ma
                    depth++;
                    queue.add(el); //usuwamy z kolejki
                    set.add(el); //dodajemy do stosu
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
        byte[][] board = new byte[goal.getRow()][goal.getCol()];
        byte iter = 1;
        for (byte i = 0; i < goal.getRow(); i++) {
            for (byte j = 0; j < goal.getCol(); j++) {
                board[i][j] = iter++;
            }
        }
        board[goal.getRow() - 1][goal.getCol() - 1] = 0;
        goal.setBoard(board);
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
