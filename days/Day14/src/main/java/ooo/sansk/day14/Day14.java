package ooo.sansk.day14;

import java.util.*;

public class Day14 {
    record Coordinate(int x, int y) {}

    enum DropResult {
        SETTLED,
        DROPPED
    }

    enum Tile {
        ROCK,
        SAND,
        AIR
    }

    private Map<Coordinate, Tile> loadEnvironment(String serializedWalls) {
        return serializedWalls.lines()
                .map(this::readSegement)
                .reduce(new HashMap<Coordinate, Tile>(), (acc, curr) -> { 
                    curr.forEach((k, v) -> acc.put(k, v));
                    return acc;
                });
    }

    private Map<Coordinate, Tile> readSegement(String line) {
        final var points = line.split(" -> ");
        final var walls = new HashMap<Coordinate, Tile>();
        for (int i = 0; i < points.length - 1; i++) {
            final var current = readCoordinate(points[i]);
            final var next = readCoordinate(points[i+1]);
            for (int x = Math.min(current.x(), next.x()); x <= Math.max(current.x(), next.x()); x++) {
                for (int y = Math.min(current.y(), next.y()); y <= Math.max(current.y(), next.y()); y++) {
                    walls.put(new Coordinate(x, y), Tile.ROCK);
                }
            }
        }
        return walls;
    }

    private Coordinate readCoordinate(String coordinate) {
        final var xy = coordinate.split(",");
        return new Coordinate(
            Integer.parseInt(xy[0]),
            Integer.parseInt(xy[1])
        );
    }

    private void printEnvironment(Map<Coordinate, Tile> environment) {
        var minX = 500;
        var maxX = 500;
        var minY = 0;
        var maxY = 0;
        for (final var coordinate : environment.keySet()) {
            minX = Math.min(coordinate.x(), minX);
            maxX = Math.max(coordinate.x(), maxX);
            minY = Math.min(coordinate.y(), minY);
            maxY = Math.max(coordinate.y(), maxY);
        }

        final var maxSandY = environment.entrySet().stream().filter((e) -> e.getValue().equals(Tile.ROCK)).mapToInt(e -> e.getKey().y).max().orElse(0);
        for (var y = minY; y <= maxY; y++) {
            for (var x = minX; x <= maxX; x++) {
                final var tile = Optional.ofNullable(calculateTile(new Coordinate(x, y), environment, maxSandY + 2)).orElse(Tile.AIR);
                System.out.printf("%s", switch (tile) {
                    case SAND -> "o";
                    case ROCK -> "#";
                    default -> ".";
                });
            }
            System.out.println();
        }
    }

    private int runSimulationForBottomlessPit(Map<Coordinate, Tile> environment) {
        DropResult lastResult = null;
        var sandDropped = 0;
        while (lastResult != DropResult.DROPPED) {
            sandDropped++;
            lastResult = dropSandIntoBottomlessPit(environment);
        }
        return sandDropped - 1;
    }

    private DropResult dropSandIntoBottomlessPit(Map<Coordinate, Tile> environment) {
        var sandX = 500;
        var sandY = 0;
        final var maxSandY = environment.keySet().stream().mapToInt(Coordinate::y).max().orElse(0);
        boolean settled = false;

        while (!settled) {
            final var down = environment.get(new Coordinate(sandX, sandY + 1));
            if (down == null) {
                sandY++;

                if (sandY > maxSandY) {
                    return DropResult.DROPPED; 
                }
                continue;
            }

            final var downLeft = environment.get(new Coordinate(sandX - 1, sandY + 1));
            if (downLeft == null) {
                sandX -= 1;
                sandY++;
                continue;
            }

            final var downRight = environment.get(new Coordinate(sandX + 1, sandY + 1));
            if (downRight == null) {
                sandX += 1;
                sandY++;
                continue;
            }

            settled = true;
        }
        environment.put(new Coordinate(sandX, sandY), Tile.SAND);
        return DropResult.SETTLED;
    }    
    
    private int runSimulationForPitWithBottom(Map<Coordinate, Tile> environment) {
        Coordinate lastResult = null;
        var sandDropped = 0;
        while (!new Coordinate(500, 0).equals(lastResult)) {
            sandDropped++;
            lastResult = dropSandIntoPitWithBottom(environment);
        }
        return sandDropped;
    }

    private Coordinate dropSandIntoPitWithBottom(Map<Coordinate, Tile> environment) {
        var sandX = 500;
        var sandY = 0;
        final var maxSandY = environment.entrySet()
            .stream()
            .filter(e -> e.getValue().equals(Tile.ROCK))
            .mapToInt(e -> e.getKey().y())
            .max()
            .orElse(0) + 1;
        boolean settled = false;

        while (!settled) {
            final var down = calculateTile(new Coordinate(sandX, sandY + 1), environment, maxSandY);
            if (down == null) {
                sandY++;

                if (sandY > maxSandY) {
                    settled = true;
                }
                continue;
            }

            final var downLeft = calculateTile(new Coordinate(sandX - 1, sandY + 1), environment, maxSandY);
            if (downLeft == null) {
                sandX -= 1;
                sandY++;

                if (sandY > maxSandY) {
                    settled = true;
                }
                continue;
            }

            final var downRight = calculateTile(new Coordinate(sandX + 1, sandY + 1), environment, maxSandY);
            if (downRight == null) {
                sandX += 1;
                sandY++;

                if (sandY > maxSandY) {
                    settled = true;
                }
                continue;
            }

            settled = true;
        }
        final var settledLocation = new Coordinate(sandX, sandY);
        environment.put(settledLocation, Tile.SAND);
        return settledLocation;
    }

    private Tile calculateTile(Coordinate coordinate, Map<Coordinate, Tile> environment, int maxEnvironment) {
        if (coordinate.y() > maxEnvironment) {
            return Tile.ROCK;
        }

        return environment.get(coordinate);
    }

    public int part1(String inputFile) {
        final var environment = loadEnvironment(inputFile);
        printEnvironment(environment);
        final var droppedSand = runSimulationForBottomlessPit(environment);
        printEnvironment(environment);
        return droppedSand;
    }

    public int part2(String inputFile) {
        final var environment = loadEnvironment(inputFile);
        final var droppedSand = runSimulationForPitWithBottom(environment);
        printEnvironment(environment);
        return droppedSand;
    }
}
