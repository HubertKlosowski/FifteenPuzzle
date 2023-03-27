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
    private String path = "";
    private Queue<GraphNode> queue = new LinkedList<>();
    private Set<GraphNode> set = new HashSet<>();
    private Stack<GraphNode> stack = new Stack<>();
    private long iterations = 0;

    public Solution(GraphNode root) {
        this.root = root; //pierwszy element
        setGoal(); //ustawiamy docelowy uklad
    }

    public boolean bfs(GraphNode node, String operations) {
        if (Arrays.deepEquals(node.getBoard(), getGoal().getBoard())) //jesli element grafu jest juz stanem docelowym
            return true;
        queue.add(node); //dodanie do kolejki
        set.add(node); //dodanie do stosu
        while (!queue.isEmpty()) { //dopoki kolejka nie bedzie pusta
            iterations++;
            GraphNode v = queue.poll(); //bierzemy pierwszy element z kolejki
            //Main.show(v.getBoard());
            if (v.getOperation() != null) {
                path += v.getOperation(); //dodajemy do sciezki operator
            }
            for (GraphNode el : v.getNeighbours(operations)) { //sprawdzamy sasiadow
                if (Arrays.deepEquals(el.getBoard(), getGoal().getBoard())) { //czy boardy sie zgadzaja
                    return true;
                } if (!set.contains(el)) { //zwraca pozycje w stosie, a gdy -1 to nie ma
                    queue.add(el); //usuwamy z kolejki
                    set.add(el); //dodajemy do stosu
                }
            }
        }
        set.clear();
        return false;
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder(path);
        sb.reverse();
        return sb.toString();
    }

    public void setGoal() { //ustawia docelowy uklad (tylko boarda)
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

    public long getIterations() {
        return iterations;
    }
}
