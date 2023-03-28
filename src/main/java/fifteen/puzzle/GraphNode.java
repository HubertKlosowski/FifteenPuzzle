package fifteen.puzzle;

import java.util.ArrayList;

public class GraphNode {
    private final byte row;
    private final byte col;
    private Character operation;
    private byte[][] board;
    private GraphNode parent = null;

    public GraphNode(byte row, byte col) {
        this.row = row;
        this.col = col;
        this.board = new byte[row][col];
    }

    public GraphNode(byte row, byte col, Character operation) {
        this.row = row;
        this.col = col;
        this.operation = operation;
        this.board = new byte[row][col];
    }

    private int[] getPosition(byte[][] board) { //znajduje wspolrzedne zera
        int[] res = new int[]{-1, -1};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return res;
    }

    public GraphNode getFirstNode(GraphNode node, int depth) throws Exception {
        while (depth != 0) {
            if (node.getParent() == null)
                throw new Exception();
            node = node.getParent();
            depth--;
        }
        return node;
    }

    private static byte[][] copy(byte[][] old) {
        if (old == null) {
            return null;
        }
        byte[][] copy = new byte[old.length][old[0].length];
        for (int i = 0; i < old.length; i++) {
            System.arraycopy(old[i], 0, copy[i], 0, old[0].length);
        }
        return copy;
    }


    private GraphNode createChild(GraphNode parent, Character operation) { //tworzy dziecko xd
        GraphNode child = new GraphNode(parent.getRow(), parent.getCol(), operation);
        byte[][] kid_board = copy(parent.getBoard());
        int[] pos0 = getPosition(kid_board); //pozycja zera
        if (operation == 'L' && pos0[1] != 0) { //warunki dobrane tak aby nie wskazywal na element z poza tablicy
            kid_board[pos0[0]][pos0[1]] = kid_board[pos0[0]][pos0[1] - 1];
            kid_board[pos0[0]][pos0[1] - 1] = 0;
        } else if (operation == 'R' && pos0[1] != child.getCol() - 1) {
            kid_board[pos0[0]][pos0[1]] = kid_board[pos0[0]][pos0[1] + 1];
            kid_board[pos0[0]][pos0[1] + 1] = 0;
        } else if (operation == 'U' && pos0[0] != 0) {
            kid_board[pos0[0]][pos0[1]] = kid_board[pos0[0] - 1][pos0[1]];
            kid_board[pos0[0] - 1][pos0[1]] = 0;
        } else if (operation == 'D' && pos0[0] != child.getRow() - 1) {
            kid_board[pos0[0]][pos0[1]] = kid_board[pos0[0] + 1][pos0[1]];
            kid_board[pos0[0] + 1][pos0[1]] = 0;
        } else {
            return null;
        }
        child.setBoard(kid_board); //ustawiamy plansze taka jak mial rodzic
        return child;
    }

    public ArrayList<GraphNode> getNeighbours(String operations) { //zbior sasiadow
        ArrayList<GraphNode> neigh = new ArrayList<>();
        if (this.getParent() != null) {
            neigh.add(this.getParent()); //rodzic to tez sasiad
        }
        for (int i = 0; i < 4; i++) {
            if (createChild(this, operations.charAt(i)) != null) { //jesli sasiad nie jest nullem
                GraphNode child = createChild(this, operations.charAt(i));
                assert child != null;
                child.setParent(this);
                neigh.add(child);
            }
        }
        return neigh;
    }

    public byte getRow() {
        return row;
    }

    public byte getCol() {
        return col;
    }

    public Character getOperation() {
        return operation;
    }

    public void setOperation(Character operation) {
        this.operation = operation;
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
