package fifteen.puzzle;

import java.util.*;

public class Solution {
    private final GraphNode root;
    private GraphNode goal;
    private StringBuilder path = new StringBuilder();
    private Queue<GraphNode> queue = new LinkedList<>();
    private Set<GraphNode> set = new HashSet<>();
    private Stack<GraphNode> stack = new Stack<>();
    private long lso = 0; //liczba stanow odwiedzonych
    private long lsp = 0; //liczba stanow przetworzonych

    public Solution(GraphNode root) {
        this.root = root; //pierwszy element
        setGoal(); //ustawiamy docelowy uklad
    }

    public boolean dfs(GraphNode node, String operations) {
        if (Arrays.deepEquals(node.getBoard(), getGoal().getBoard()))
            return true;
        stack.push(node);
        while (!stack.isEmpty()) {
            lso++;
            GraphNode v = stack.pop();
            set.add(v);
            ArrayList<GraphNode> neigh = v.getNeighbours(operations);
            Collections.reverse(neigh);
            for (GraphNode el : neigh) {
                if (Arrays.deepEquals(el.getBoard(), getGoal().getBoard())) {
                    return true;
                } if (!set.contains(el) && !stack.contains(el)) {
                    if (Arrays.deepEquals(el.getBoard(), getGoal().getBoard())) {
                        return true;
                    }
                    if (v.getParent() != null && v.getParent().getOperation() != null) {
                        path.append(v.getParent().getOperation()); //poprawic bo zapisuje wszystkie kroki
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
        while (!queue.isEmpty()) { //dopoki kolejka nie bedzie pusta
            lso++;
            GraphNode v = queue.poll(); //bierzemy pierwszy element z kolejki
            for (GraphNode el : v.getNeighbours(operations)) { //sprawdzamy sasiadow
                if (Arrays.deepEquals(el.getBoard(), getGoal().getBoard())) { //czy boardy sie zgadzaja
                    Main.show(el.getBoard());
                    return true;
                } if (!set.contains(el)) { //zwraca pozycje w stosie, a gdy -1 to nie ma
                    if (v.getParent() != null && v.getParent().getOperation() != null) {
                        path.append(v.getParent().getOperation()); //poprawic bo zapisuje wszystkie kroki
                    }
                    queue.add(el); //usuwamy z kolejki
                    set.add(el); //dodajemy do stosu
                }
            }
        }
        set.clear();
        return false;
    }

    public String getPath() {
        path.reverse();
        return path.toString();
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

    public long getLso() {
        return lso;
    }

    public void setLso(long lso) {
        this.lso = lso;
    }

    public long getLsp() {
        return lsp;
    }

    public void setLsp(long lsp) {
        this.lsp = lsp;
    }
}
