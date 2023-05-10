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


def drawForAll(ax, data, m):
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
            ax[0, 0].bar(i + x, avgs[i][j], color=colors[j], edgecolor="black", align="center", width=0.3)
            x += 0.3
    ax[0, 0].set_xticks(bins)
    if m == 3:
        ax[0, 0].set_yticks([2 * i for i in range(11)])
    ax[0, 0].set_ylabel(criteria[m], fontsize=13)
    ax[0, 0].set_title("Ogółem")
    ax[0, 0].legend(["BFS", "DFS", "A*"])
    if m == 1 or m == 2:
        ax[0, 0].set_yscale("log")


def drawForStrategy(ax, data, strategy, m):
    if type(strategy) is not str:
        raise TypeError
    plt.xticks(bins)
    if strategy == "astr":
        param = ["hamm", "manh"]
        splitData = [[[] for _ in range(2)] for _ in range(7)]
        for el in data:
            for j in range(2):
                if el[6] == strategy and el[7] == param[j]:
                    splitData[el[5] - 1][j].append(el[m])
        avgs = [[np.mean(splitData[i][j]) for j in range(2)] for i in range(7)]
        for i in range(7):
            x = 0.85
            for j in range(2):
                ax[0, 1].bar(i + x, avgs[i][j], color=colors[j], edgecolor="black", align="center", width=0.3)
                x += 0.3
        ax[0, 1].set_title("A*")
        ax[0, 1].legend(["Hamming", "Manhattan"])
    if strategy == "dfs" or strategy == "bfs":
        param = ["rdul", "rdlu", "drul", "drlu", "ludr", "lurd", "uldr", "ulrd"]
        splitData = [[[] for _ in range(8)] for _ in range(7)]
        for el in data:
            for j in range(8):
                if el[6] == strategy and el[7] == param[j] and el[m] != -1:
                    splitData[el[5] - 1][j].append(el[m])
        avgs = [[np.mean(splitData[i][j]) for j in range(8)] for i in range(7)]
        for i in range(7):
            x = 0.65
            for j in range(8):
                if strategy == "bfs":
                    ax[1, 0].bar(i + x, avgs[i][j], color=colors[j], edgecolor="black", align="center", width=0.1)
                if strategy == "dfs":
                    ax[1, 1].bar(i + x, avgs[i][j], color=colors[j], edgecolor="black", align="center", width=0.1)
                x += 0.1
        if strategy == "dfs" and m == 3:
            ax[1, 1].set_yticks([2 * i for i in range(11)])
        ax[1, 0].set_xlabel("Głębokość", fontsize=13)
        ax[1, 1].set_xlabel("Głębokość", fontsize=13)
        if strategy == "bfs":
            ax[1, 0].set_ylabel(criteria[m], fontsize=13)
            ax[1, 0].set_title(strategy.upper())
        if strategy == "dfs":
            ax[1, 1].set_title(strategy.upper())
        ax[1, 0].legend([el.upper() for el in param])
        ax[1, 1].legend([el.upper() for el in param])
        if m == 1 or m == 2:
            ax[1, 0].set_yscale("log")
            ax[1, 1].set_yscale("log")


def main():
    os.chdir("..")
    os.chdir("..")
    os.chdir("Testowanie")
    fileNames = [fileName for fileName in os.listdir(os.getcwd())]
    filesData = []
    for el in fileNames:
        if el.find("_stats.txt") != -1:
            filesData.append(getData(el))
    os.chdir("..")
    os.chdir("Wykresy")
    for i in range(5):
        fig, ax = plt.subplots(nrows=2, ncols=2, figsize=(10, 10))
        drawForAll(ax, filesData, i)
        drawForStrategy(ax, filesData, "astr", i)
        drawForStrategy(ax, filesData, "dfs", i)
        drawForStrategy(ax, filesData, "bfs", i)
        plt.savefig(criteriaShort[i] + ".png")
        plt.show()
        plt.close()
    return 0


if __name__ == "__main__":
    main()
