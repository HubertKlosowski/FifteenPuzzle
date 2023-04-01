package fifteen.puzzle;

import java.util.Comparator;

public class ManhattanComparator implements Comparator<GraphNode> {
    public ManhattanComparator() {

    }

    @Override
    public int compare(GraphNode o1, GraphNode o2) {
        int m1 = ManhattanMetric(o1);
        int m2 = ManhattanMetric(o2);
        return m1 - m2;
    }

    private int ManhattanMetric(GraphNode node) { //dystans miedzy wspolrzednymi stanu doc. od aktualnie sprawdzanego
        int res = 0;
        byte[] board = node.getBoard();
        int row = node.getRow();
        int col = node.getCol();
        for (int i = 0; i < board.length; i++) {
            if (board[i] != i + 1 && board[i] != 0) {
                res += Math.abs((i / row) - (i % col)) + Math.abs(((board[i] - 1) / row) - ((board[i] - 1) % col));
            } else if (board[i] == 0 && i != board.length - 1) {
                res += Math.abs((i / row) - (i % col)) + Math.abs(((board.length - 2) / row) - ((board.length - 2) % col));
            }
        }
        return res;
    }
}
