package fifteen.puzzle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String strategy = args[0];
        String oper = args[1];
        String fileName = args[2];
        String solutionFile = args[3];
        String statsFile = args[4];
        byte[][] board;
        try {
            board = loadFromFile(fileName);
        } catch (IOException e) {
            System.out.println("Blad!! Nie mozna odczytac danych z pliku lub plik nie istnieje.");
            System.out.println("Wygenerowano losowa tablice 4x4.");
            board = generateBoard();
        } catch (IllegalArgumentException e) {
            System.out.println("Blad!! Niepoprawny wymiar.");
            System.out.println("Wygenerowano losowa tablice 4x4.");
            board = generateBoard();
        }
        GraphNode root = new GraphNode((byte) board.length, (byte) board[0].length);
        root.setBoard(board);
        Solution sol = new Solution(root);
        double sec = System.currentTimeMillis(), sec1;
        if (Objects.equals(strategy, "bfs") && sol.bfs(root, oper)) {
            sec1 = System.currentTimeMillis();
            double x = (sec1 - sec) / 1000;
            String[] stats = { //jeszcze jakies stany
                    String.valueOf(sol.getPath().length()),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    String.format("%.3f", x)
            };
            String[] solution = {
                    String.valueOf(sol.getPath().length()),
                    sol.getPath()
            };
            try {
                saveStatsToFile(statsFile, stats);
                saveSolutionToFile(solutionFile, solution);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (Objects.equals(strategy, "dfs") && sol.dfs(root, oper)) {
            sec1 = System.currentTimeMillis();
            double x = (sec1 - sec) / 1000;
            String[] stats = { //jeszcze jakies stany
                    String.valueOf(sol.getPath().length()),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    String.format("%.3f", x)
            };
            String[] solution = {
                    String.valueOf(sol.getPath().length()),
                    sol.getPath()
            };
            try {
                saveStatsToFile(statsFile, stats);
                saveSolutionToFile(solutionFile, solution);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Blad!! Algorytm " + strategy.toUpperCase() + " nie znalazl zadnych rozwiazan.");
            try {
                saveSolutionToFile(solutionFile, new String[]{"-1"});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        sol.setLSO(0);
        sol.setLSP(0);
    }

    private static void saveSolutionToFile(String fileName, String[] data) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        for (String el : data) {
            fw.write(el);
            fw.write('\n');
        }
        fw.close();
    }

    private static void saveStatsToFile(String fileName, String[] data) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        for (String el : data) {
            fw.write(el);
            fw.write('\n');
        }
        fw.close();
    }

    private static byte[][] loadFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.isFile() || !file.canRead() || !file.exists()) {
            throw new IOException(); //jesli takiego pliku nie ma
        }
        Scanner scanner = new Scanner(file); //do odczytu pliku
        ArrayList<Byte> tmp = new ArrayList<>(); //miejsce zapisu pojedynczych bajtow z pliku
        while (scanner.hasNextLine()) {
            tmp.add(Byte.parseByte(scanner.next())); //zapis
        }
        if (tmp.get(0) <= 0 || tmp.get(1) <= 0)
            throw new IllegalArgumentException();
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
        System.out.println();
    }
}