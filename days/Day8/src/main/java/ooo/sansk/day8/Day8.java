package ooo.sansk.day8;

public class Day8 {
    private int[][] readTreeGrid(String input) {
        final var rows = input.split("\\r?\\n");
        final var grid = new int[rows.length][rows[0].length()];

        for (int x = 0; x < rows.length; x++) {
            final var row = rows[x];
            for (int y = 0; y < row.length(); y++) {
                grid[x][y] = Integer.parseInt("" + row.charAt(y));
            }
        }

        return grid;
    }

    private int calculateAmountOfEdgeTrees(int[][] grid) {
        return (grid[0].length * 2) + ((grid.length - 2) * 2);
    }

    private int countVisibleTrees(int[][] grid) {
        int visible = 0;
        for (int x = 1; x < grid.length - 1; x++) {
            final var row = grid[x];
            for (int y = 1; y < row.length - 1; y++) {
                if (isVisible(x, y, grid)) {
                    visible++;
                }
            }
        }
        return visible + calculateAmountOfEdgeTrees(grid);
    }

    private boolean isVisible(int xPos, int yPos, int[][] grid) {
        final var ownHeight = grid[xPos][yPos];

        var northVisible = true;
        for (int x = xPos - 1; x >= 0; x--) {
            final var neighbourHeigth = grid[x][yPos];
            if (neighbourHeigth >= ownHeight) {
                northVisible = false;
                break;
            }
        }
        if (northVisible) {
            return true;
        }

        var southVisible = true;
        for (int x = xPos + 1; x < grid.length; x++) {
            final var neighbourHeigth = grid[x][yPos];
            if (neighbourHeigth >= ownHeight) {
                southVisible = false;
                break;
            }
        }
        if (southVisible) {
            return true;
        }

        var eastVisible = true;
        for (int y = yPos - 1; y >= 0; y--) {
            final var neighbourHeigth = grid[xPos][y];
            if (neighbourHeigth >= ownHeight) {
                eastVisible = false;
                break;
            }
        }
        if (eastVisible) {
            return true;
        }

        var westVisible = true;
        for (int y = yPos + 1; y < grid[xPos].length; y++) {
            final var neighbourHeigth = grid[xPos][y];
            if (neighbourHeigth >= ownHeight) {
                westVisible = false;
                break;
            }
        }
        if (westVisible) {
            return true;
        }

        return false;
    }

    private int findHighestScenicScore(int[][] grid) {
        int maxScenicScore = 0;
        for (int x = 1; x < grid.length - 1; x++) {
            final var row = grid[x];
            for (int y = 1; y < row.length - 1; y++) {
                final var score = calculateScenicScoreForCoordinate(x, y, grid);
                maxScenicScore = Math.max(score, maxScenicScore);
            }
        }
        return maxScenicScore;
    }

    private int calculateScenicScoreForCoordinate(int xPos, int yPos, int[][] grid) {
        final var ownHeight = grid[xPos][yPos];

        var northScore = 0;
        for (int x = xPos - 1; x >= 0; x--) {
            northScore++;
            final var neighbourHeigth = grid[x][yPos];
            if (neighbourHeigth >= ownHeight) {
                break;
            }
        }

        var southScore = 0;
        for (int x = xPos + 1; x < grid.length; x++) {
            southScore++;
            final var neighbourHeigth = grid[x][yPos];
            if (neighbourHeigth >= ownHeight) {
                break;
            }
        }

        var eastScore = 0;
        for (int y = yPos - 1; y >= 0; y--) {
            eastScore++;
            final var neighbourHeigth = grid[xPos][y];
            if (neighbourHeigth >= ownHeight) {
                break;
            }
        }

        var westScore = 0;
        for (int y = yPos + 1; y < grid[xPos].length; y++) {
            westScore++;
            final var neighbourHeigth = grid[xPos][y];
            if (neighbourHeigth >= ownHeight) {
                break;
            }
        }

        return northScore * eastScore * southScore * westScore;
    }

    public long part1(String inputFile) {
        final var grid = readTreeGrid(inputFile);
        return countVisibleTrees(grid);
    }

    public long part2(String inputFile) {
        final var grid = readTreeGrid(inputFile);
        return findHighestScenicScore(grid);
    }
}
