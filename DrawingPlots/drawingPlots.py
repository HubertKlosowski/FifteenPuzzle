import matplotlib.pyplot as plt
import numpy as np
import os

criteria = ["Średnia długość rozwiązania", "Liczba stanów odwiedzonych", "Liczba stanów przetworzonych",
                "Maksymalna osiągnięta głębokość rekursji", "Czas trwania procesu obliczeniowego"]
criteriaShort = ["avgLen", "visited", "processed", "maxDepth", "time"]
colors = ["blue", "orange", "green", "red", "purple", "brown", "pink", "gray"]
bins = [1, 2, 3, 4, 5, 6, 7]


def getData(statsFileName):
    os.getcwd()
    if type(statsFileName) is not str:
        raise TypeError
    data = np.loadtxt(os.getcwd() + '/' + statsFileName, dtype=str)
    fileData = []
    for i in range(len(data) - 1):
        fileData.append(int(data[i]))
    fileData.append(float(data[len(data) - 1].replace(',', '.')))
    tmp = statsFileName.split("_")
    fileData.append(int(tmp[1]))
    fileData.append(tmp[3])
    fileData.append(tmp[4])
    return fileData


def drawForAll(data):
    for m in range(5):
        splitData = [[[] for _ in range(3)] for _ in range(7)]
        for el in data:
            if el[6] == "bfs":
                splitData[el[5] - 1][0].append(el[m])
            if el[6] == "dfs" and el[m] != -1:
                splitData[el[5] - 1][1].append(el[m])
            if el[6] == "astr":
                splitData[el[5] - 1][2].append(el[m])
        avgs = [[np.mean(splitData[i][j]) for j in range(3)] for i in range(7)]
        for i in range(7):
            x = 0.7
            for j in range(3):
                plt.bar(i + x, avgs[i][j], color=colors[j], edgecolor="black", align="center", width=0.3)
                x += 0.3
        plt.xticks(bins)
        if m == 3:
            plt.yticks([2 * i for i in range(11)])
        plt.ylabel(criteria[m], fontsize=13)
        plt.xlabel("Głębokość", fontsize=13)
        plt.title("Ogółem")
        plt.legend(["BFS", "DFS", "A*"])
        if m == 1 or m == 2:
            plt.yscale("log")
        plt.savefig(os.path.join(os.getcwd(), "all " + criteriaShort[m] + ".png"))
        plt.show()


def drawForStrategy(data, strategy):
    if type(strategy) is not str:
        raise TypeError
    plt.xticks(bins)
    if strategy == "astr":
        param = ["hamm", "manh"]
        for m in range(5):
            splitData = [[[] for _ in range(2)] for _ in range(7)]
            for el in data:
                for j in range(2):
                    if el[6] == strategy and el[7] == param[j]:
                        splitData[el[5] - 1][j].append(el[m])
            avgs = [[np.mean(splitData[i][j]) for j in range(2)] for i in range(7)]
            for i in range(7):
                x = 0.85
                for j in range(2):
                    plt.bar(i + x, avgs[i][j], color=colors[j], edgecolor="black", align="center", width=0.3)
                    x += 0.3
            plt.ylabel(criteria[m], fontsize=13)
            plt.xlabel("Głębokość", fontsize=13)
            plt.title("A*")
            plt.legend(["Hamming", "Manhattan"])
            plt.savefig(os.path.join(os.getcwd(), strategy + " " + criteriaShort[m] + ".png"))
            plt.show()
    if strategy == "dfs" or strategy == "bfs":
        param = ["rdul", "rdlu", "drul", "drlu", "ludr", "lurd", "uldr", "ulrd"]
        for m in range(5):
            splitData = [[[] for _ in range(8)] for _ in range(7)]
            for el in data:
                for j in range(8):
                    if el[6] == strategy and el[7] == param[j] and el[m] != -1:
                        splitData[el[5] - 1][j].append(el[m])
            avgs = [[np.mean(splitData[i][j]) for j in range(8)] for i in range(7)]
            for i in range(7):
                x = 0.65
                for j in range(8):
                    plt.bar(i + x, avgs[i][j], color=colors[j], edgecolor="black", align="center", width=0.1)
                    x += 0.1
            if strategy == "dfs" and m == 3:
                plt.yticks([2 * i for i in range(11)])
            plt.ylabel(criteria[m], fontsize=13)
            plt.xlabel("Głębokość", fontsize=13)
            plt.title(strategy.upper())
            plt.legend([el.upper() for el in param])
            if m == 1 or m == 2:
                plt.yscale("log")
            plt.savefig(os.path.join(os.getcwd(), strategy + " " + criteriaShort[m] + ".png"))
            plt.show()


def main():
    os.chdir("..")
    os.chdir("Testowanie")
    fileNames = [fileName for fileName in os.listdir(os.getcwd())]
    filesData = []
    for el in fileNames:
        if el.find("_stats.txt") != -1:
            filesData.append(getData(el))
    os.chdir("..")
    os.chdir("Wykresy")
    # drawForAll(filesData)
    drawForStrategy(filesData, "astr")
    # drawForStrategy(filesData, "dfs")
    # drawForStrategy(filesData, "bfs")
    return 0


if __name__ == "__main__":
    main()
