package ooo.sansk.day9;

import java.util.HashSet;
import java.util.Set;

public class Day9 {
    record Coordinate(int x, int y) {
    }

    class Rope {
        private final Set<Coordinate> visited;
        private Coordinate head;
        private Coordinate[] tail;

        public Rope(int length) {
            this.visited = new HashSet<>();
            this.head = new Coordinate(0, 0);
            this.tail = new Coordinate[length];
            for (int i = 0; i < this.tail.length; i++) {
                this.tail[i] = this.head;
            }
            visited.add(this.head);
        }

        void move(String move) {
            final var parts = move.split(" ");
            final var direction = parts[0];
            final var amount = Integer.parseInt(parts[1]);

            for (int i = 0; i < amount; i++) {
                switch (direction) {
                    case "U" -> updateRope(new Coordinate(head.x(), head.y() + 1));
                    case "D" -> updateRope(new Coordinate(head.x(), head.y() - 1));
                    case "L" -> updateRope(new Coordinate(head.x() - 1, head.y()));
                    case "R" -> updateRope(new Coordinate(head.x() + 1, head.y()));
                    default -> throw new IllegalArgumentException("Unknown direction '%s'".formatted(direction));
                }
            }
        }

        public int getUniqueSpots() {
            return visited.size();
        }

        private void updateRope(Coordinate newHead) {
            var previous = newHead;
            head = newHead;
            for (int i = 0; i < this.tail.length; i++) {
                final var current = this.tail[i];
                final var newPosition = calculateSegementPosition(i, previous, current);
                previous = newPosition;
                this.tail[i] = newPosition;
            }
            visited.add(this.tail[this.tail.length - 1]);
        }

        private Coordinate calculateSegementPosition(int index, Coordinate previous, Coordinate current) {
            // Check if previous and current are touching
            final var xDistance = previous.x() - current.x();
            final var yDistance = previous.y() - current.y();
            if (Math.abs(xDistance) <= 1 && Math.abs(yDistance) <= 1) {
                return current;
            }

            return new Coordinate(
                current.x() + Math.max(-1, Math.min(xDistance, 1)),
                current.y() + Math.max(-1, Math.min(yDistance, 1))
            );
        }
    }


    public int part1(String inputFile) {
        final var rope = new Rope(1);
        inputFile.lines().forEach(rope::move);
        return rope.getUniqueSpots();
    }

    public int part2(String inputFile) {
        final var rope = new Rope(9);
        inputFile.lines().forEach(rope::move);
        return rope.getUniqueSpots();
    }
}
