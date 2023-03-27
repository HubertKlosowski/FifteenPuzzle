package fifteen.puzzle;

public class GraphNode {
    private final byte row;
    private final byte col;
    private String oper;
    private byte[][] board;
    private GraphNode parent = null;

    public GraphNode(byte row, byte col) {
        this.row = row;
        this.col = col;
    }

    public GraphNode(byte row, byte col, String oper) {
        this.row = row;
        this.col = col;
        this.oper = oper;
    }

    public byte getRow() {
        return row;
    }

    public byte getCol() {
        return col;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public byte[][] getBoard() {
        return board;
    }

    public void setBoard(byte[][] board) {
        this.board = board;
    }

    public GraphNode getParent() {
        return parent;
    }

    public void setParent(GraphNode parent) {
        this.parent = parent;
    }
}
