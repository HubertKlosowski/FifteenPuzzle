package fifteen.puzzle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Main {
    private static byte row = 4;
    private static byte col = 4;
    public static void main(String[] args) {
        /*
            strategia                           args[0]
            kolejnosc lub kryterium heurystyki  args[1]
            plik z ukladem poczatkowym          args[2]
            plik z rozwiazaniem                 args[3]
            plik z statystykami                 args[4]
        */

        byte[] board;
        try {
            board = loadFromFile(args[2]);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        GraphNode root = new GraphNode(row, col);
        root.setBoard(board);
        Solution sol = new Solution(root);
        double sec = System.currentTimeMillis();
        if (Objects.equals(args[0], "bfs") && sol.bfs(root, args[1])) {
            double x = (System.currentTimeMillis() - sec) / 1000.0;
            String[] stats = new String[]{ //sprawdzic stany
                    String.valueOf(sol.getPath().length()),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    String.valueOf(Integer.valueOf(args[2].substring(4,6))),
                    String.format("%.3f", x)
            };
            String[] solution = {
                    String.valueOf(sol.getPath().length()),
                    sol.getPath()
            };
            try {
                saveToFile(args[4], stats);
                saveToFile(args[3], solution);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else if (Objects.equals(args[0], "dfs") && sol.dfs(root, args[1], 20)) {
            double x = (System.currentTimeMillis() - sec) / 1000.0;
            String[] stats = new String[]{ //sprawdzic stany
                    String.valueOf(sol.getPath().length()),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    String.valueOf(sol.getMaxRecurDepth()),
                    String.format("%.3f", x)
            };
            String[] solution = {
                    String.valueOf(sol.getPath().length()),
                    sol.getPath()
            };
            try {
                saveToFile(args[4], stats);
                saveToFile(args[3], solution);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else if (Objects.equals(args[0], "astr") && sol.astr(root, args[1])) {
            double x = (System.currentTimeMillis() - sec) / 1000.0;
            String[] stats = new String[]{ //sprawdzic stany
                    String.valueOf(sol.getPath().length()),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    String.valueOf(sol.getMaxRecurDepth()),
                    String.format("%.3f", x)
            };
            String[] solution = {
                    String.valueOf(sol.getPath().length()),
                    sol.getPath()
            };
            try {
                saveToFile(args[4], stats);
                saveToFile(args[3], solution);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            double x = (System.currentTimeMillis() - sec) / 1000.0;
            String[] stats = new String[]{ //sprawdzic stany
                    String.valueOf(-1),
                    String.valueOf(sol.getLSO()),
                    String.valueOf(sol.getLSP()),
                    String.valueOf(sol.getMaxRecurDepth()),
                    String.format("%.3f", x)
            };
            if (Objects.equals(args[0], "astr")) {
                System.out.println("Blad!! Algorytm A* nie znalazl zadnych rozwiazan.");
            } else {
                System.out.println("Blad!! Algorytm " + args[0].toUpperCase() + " nie znalazl zadnych rozwiazan.");
            }
            try {
                saveToFile(args[4], stats);
                saveToFile(args[3], new String[]{"-1"});
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

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
}