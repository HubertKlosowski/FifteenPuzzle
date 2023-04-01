package fifteen.puzzle;

import java.util.Comparator;

public class HammingComparator implements Comparator<GraphNode> {
    public HammingComparator() {

    }

    @Override
    public int compare(GraphNode o1, GraphNode o2) {
        int h1 = HammingMetric(o1);
        int h2 = HammingMetric(o2);
        return h1 - h2;
    }

    private int HammingMetric(GraphNode node) { //iloma elementami rozni sie stan docelowy od aktualnie sprawdzanego
        int res = 0;
        byte[] board = node.getBoard();
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != i + 1) {
                res++;
            }
        }
        if (board[board.length - 1] != 0) {
            res++;
        }
        return res;
    }
}
