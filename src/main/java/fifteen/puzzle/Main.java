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
    private static byte row = 4;
    private static byte col = 4;
    public static void main(String[] args) {
        String strategy = args[0];
        String oper = args[1];
        String fileName = args[2];
        String solutionFile = args[3];
        String statsFile = args[4];
        byte[] board;
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
        GraphNode root = new GraphNode(row, col);
        root.setBoard(board);
        Solution sol = new Solution(root);
        double sec = System.currentTimeMillis(), sec1;
        if (Objects.equals(strategy, "bfs") && sol.bfs(root, oper, 3)) {
            sec1 = System.currentTimeMillis();
            double x = (sec1 - sec) / 1000;
            String[] stats = new String[]{ //jeszcze jakies stany
                    String.valueOf(sol.getPath().length()),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    "0",
                    String.format("%.3f", x)
            };
            String[] solution = {
                    String.valueOf(sol.getPath().length()),
                    sol.getPath()
            };
            try {
                saveToFile(statsFile, stats);
                saveToFile(solutionFile, solution);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (Objects.equals(strategy, "dfs") && sol.dfs(root, oper)) {
            sec1 = System.currentTimeMillis();
            double x = (sec1 - sec) / 1000;
            String[] stats = new String[]{ //jeszcze jakies stany
                    String.valueOf(sol.getPath().length()),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    String.valueOf(sol.getMaxRecur()),
                    String.format("%.3f", x)
            };
            String[] solution = {
                    String.valueOf(sol.getPath().length()),
                    sol.getPath()
            };
            try {
                saveToFile(statsFile, stats);
                saveToFile(solutionFile, solution);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Blad!! Algorytm " + strategy.toUpperCase() + " nie znalazl zadnych rozwiazan.");
            try {
                saveToFile(solutionFile, new String[]{"-1"});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        sol.setLSO(0);
        sol.setLSP(0);
    }

    private static void saveToFile(String fileName, String[] data) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        for (String el : data) {
            fw.write(el);
            fw.write('\n');
        }
        fw.close();
    }

    private static byte[] loadFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.isFile() || !file.canRead() || !file.exists()) {
            throw new IOException(); //jesli takiego pliku nie ma
        }
        Scanner scanner = new Scanner(file);
        ArrayList<Byte> load = new ArrayList<>();
        while (scanner.hasNext()) {
            load.add(Byte.parseByte(scanner.next()));
        }
        row = load.get(0);
        col = load.get(1);
        int n = load.get(0) * load.get(1);
        byte[] res = new byte[n];
        for (int i = 0; i < n; i++) {
            res[i] = load.get(i + 2);
        }
        return res;
    }

    private static byte[] generateBoard() {
        byte[] res = new byte[16];
        for (byte i = 0; i < 16; i++) {
            res[i] = i;
        }
        Collections.shuffle(Arrays.asList(res));
        return res;
    }

    public static void show(byte[] board) { //pomocniczo do wyrzucenia
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(board[i * row + j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}