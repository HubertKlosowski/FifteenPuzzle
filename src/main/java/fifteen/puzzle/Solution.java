package fifteen.puzzle;

public class Solution {

    private final GraphNode root;
    private GraphNode goal;

    public GraphNode getGoal() throws NullPointerException {
        return goal;
    }

    public Solution(GraphNode root) {
        this.root = root;
        setGoal();
    }

    public String bfs(GraphNode node) {
        String path = "";
        return path;
    }

    public void setGoal() { //ustawia docelowy uklad (tylko boarda)
        this.goal = new GraphNode(this.root.getRow(), this.root.getCol());
        byte[][] board = new byte[this.goal.getRow()][this.goal.getCol()];
        for (byte i = 0; i < this.goal.getRow(); i++) {
            for (byte j = 0; j < this.goal.getCol(); j++) {
                board[i][j] = (byte) (i * this.goal.getRow() + j);
            }
        }
        board[this.goal.getRow()][this.goal.getCol()] = 0;
    }
}
