package fifteen.puzzle;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Objects;

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

    public GraphNode createChild(Character operation) {
        GraphNode child = new GraphNode(this.getRow(), this.getCol(), operation);
        byte[] kid_board = SerializationUtils.clone(this.getBoard());
        int[] whereZero = getPosition(kid_board); //pozycja zera
        if (whereZero[0] == -1) {
            throw new NullPointerException();
        }
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

    public byte getRow() {
        return row;
    }

    public byte getCol() {
        return col;
    }

    public Character getOperation() {
        return operation;
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

    private int Metric(String choice) { //iloma elementami rozni sie stan docelowy od aktualnie sprawdzanego
        int res = 0;
        byte[] board = this.getBoard();
        if (Objects.equals(choice, "hamm")) {
            for (int i = 0; i < board.length; i++) {
                if (board[i] != i + 1) {
                    res++;
                }
            }
        } else if (Objects.equals(choice, "manh")) {
            int row = this.getRow();
            int col = this.getCol();
            for (int i = 0; i < board.length; i++) {
                if (board[i] != i + 1 && board[i] != 0) {
                    res += Math.abs((i / row) - (i % col));
                    if (board[i] == 0 && i != board.length - 1) {
                        res += Math.abs(((board[i] - 1) / row) - ((board[i] - 1) % col));
                    } else {
                        res += Math.abs(((board.length - 2) / row) - ((board.length - 2) % col));
                    }
                }
            }
        }
        return res;
    }
}
