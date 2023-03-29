package fifteen.puzzle;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class GraphNode implements Serializable {
    private final byte row;
    private final byte col;
    private Character operation;
    private byte[] board;
    private GraphNode parent = null;

    public GraphNode(byte row, byte col) {
        this.row = row;
        this.col = col;
        this.board = new byte[row * col];
    }

    public GraphNode(byte row, byte col, Character operation) {
        this.row = row;
        this.col = col;
        this.operation = operation;
        this.board = new byte[row * col];
    }

    private int[] getPosition(byte[] board) { //znajduje wspolrzedne zera
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0)
                return new int[]{i / getRow(), i % getCol()};
        }
        return new int[]{-1, -1};
    }

    public static GraphNode goBack(GraphNode node, int depth) {
        GraphNode copy = SerializationUtils.clone(node);
        while (depth != 0) {
            copy = copy.getParent();
            depth--;
        }
        return copy;
    }

    public GraphNode createChild(Character operation) { //tworzy dziecko xd
        GraphNode child = new GraphNode(this.getRow(), this.getCol(), operation);
        byte[] kid_board = SerializationUtils.clone(this.getBoard());
        int[] whereZero = getPosition(kid_board); //pozycja zera
        if (operation == 'L' && whereZero[1] != 0) {
            kid_board[(whereZero[0] * getRow()) + whereZero[1]] = kid_board[(whereZero[0] * getRow()) + whereZero[1] - 1];
            kid_board[(whereZero[0] * getRow()) + whereZero[1] - 1] = 0;
        } else if (operation == 'R' && whereZero[1] != child.getCol() - 1) {
            kid_board[(whereZero[0] * getRow()) + whereZero[1]] = kid_board[(whereZero[0] * getRow()) + whereZero[1] + 1];
            kid_board[(whereZero[0] * getRow()) + whereZero[1] + 1] = 0;
        } else if (operation == 'U' && whereZero[0] != 0) {
            kid_board[(whereZero[0] * getRow()) + whereZero[1]] = kid_board[(whereZero[0] * getRow()) + whereZero[1] - getCol()];
            kid_board[(whereZero[0] * getRow()) + whereZero[1] - getCol()] = 0;
        } else if (operation == 'D' && whereZero[0] != child.getRow() - 1) {
            kid_board[(whereZero[0] * getRow()) + whereZero[1]] = kid_board[(whereZero[0] * getRow()) + whereZero[1] + getCol()];
            kid_board[(whereZero[0] * getRow()) + whereZero[1] + getCol()] = 0;
        } else {
            throw new NullPointerException();
        }
        child.setBoard(kid_board); //ustawiamy plansze taka jak mial rodzic
        child.setParent(this);
        return child;
    }

    /*public ArrayList<GraphNode> getNeighbours(String operations) { //zbior sasiadow
        ArrayList<GraphNode> neigh = new ArrayList<>();
        if (this.getParent() != null) {
            neigh.add(this.getParent()); //rodzic to tez sasiad
        }
        for (int i = 0; i < 4; i++) {
            if (createChild(this, operations.charAt(i)) != null) { //jesli sasiad nie jest nullem
                GraphNode child = createChild(this, operations.charAt(i));
                child.setParent(this);
                neigh.add(child);
            }
        }
        return neigh;
    }*/

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

    public byte[] getBoard() {
        return board;
    }

    public void setBoard(byte[] board) {
        this.board = board;
    }

    public GraphNode getParent() {
        return parent;
    }

    public void setParent(GraphNode parent) {
        this.parent = parent;
    }
}
