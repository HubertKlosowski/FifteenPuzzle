package fifteen.puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) { //flagi

        }
        System.out.println("Podaj nazwe pliku z ukladem poczatkowym: ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        byte[][] board;
        try {
            board = loadFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Blad!! Nie mozna odczytac danych z pliku.");
            board = generateBoard();
        }
        GraphNode root = new GraphNode((byte) board.length, (byte) board[0].length);
        root.setBoard(board);
    }

    private static byte[][] loadFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.isFile() || !file.canRead() || !file.exists()) {
            throw new FileNotFoundException(); //jesli takiego pliku nie ma
        }
        Scanner scanner = new Scanner(file); //do odczytu pliku
        ArrayList<Byte> tmp = new ArrayList<>(); //miejsce zapisu pojedynczych bajtow z pliku
        while (scanner.hasNextLine()) {
            tmp.add(Byte.parseByte(scanner.next())); //zapis
        }
        byte[][] res = new byte[tmp.get(0)][tmp.get(1)];
        int iter = 2; //pomijam ilosc wierszy i kolumn
        for (int i = 0; i < tmp.get(0); i++) {
            for (int j = 0; j < tmp.get(1); j++) {
                res[i][j] = tmp.get(iter++);
            }
        }
        scanner.close();
        return res;
    }

    private static byte[][] generateBoard() {
        byte[] tmp = new byte[16];
        for (byte i = 0; i < 16; i++) {
            tmp[i] = i;
        }
        Collections.shuffle(Arrays.asList(tmp));
        byte[][] res = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(tmp, (i * 4), res[i], 0, 4);
        }
        return res;
    }

    public static void show(byte[][] board) { //pomocniczo do wyrzucenia
        for (byte[] bytes : board) {
            for (byte aByte : bytes) {
                System.out.print(aByte + ", ");
            }
            System.out.println();
        }
    }
}