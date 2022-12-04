package ooo.sansk.day4;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {
    record Assignments(Set<Integer> firstRange, Set<Integer> secondRange) {
        boolean hasFullOverlap() {
            return firstRange.containsAll(secondRange)
                || secondRange.containsAll(firstRange);
        }

        boolean hasPartialOverlap() {
            final var firstOverlap = new HashSet<>(firstRange);
            firstOverlap.retainAll(secondRange);
            if (firstOverlap.size() > 0) {
                return true;
            }

            final var secondOverlap = new HashSet<>(secondRange);
            secondOverlap.retainAll(firstRange);
            if (secondOverlap.size() > 0) {
                return true;
            }

            // This could be a direct return of the boolean expression, however keeping the same form as the first check feels more elegant.
            return false;
        }
    }

    private Set<Integer> readRange(String range) {
        final var startEnd = range.split("-");
        if (startEnd.length != 2) {
            throw new IllegalArgumentException("A range should contain a start and end number, split by a '-' (1-2)");
        }

        return IntStream.rangeClosed(Integer.parseInt(startEnd[0]), Integer.parseInt(startEnd[1]))
            .boxed()
            .collect(Collectors.toSet());
    }

    private Assignments readAssignments(String assignment) {
        final var assignments = assignment.split(",");
        if (assignments.length != 2) {
            throw new IllegalArgumentException("An assignment definition must exactly contain two assignments. Found " + assignments.length);
        }

        final var firstRange = readRange(assignments[0]);
        final var secondRange = readRange(assignments[1]);

        return new Assignments(firstRange, secondRange);
    }

    public long part1(String inputFile) {
        return inputFile.lines()
            .map(this::readAssignments)
            .filter(Assignments::hasFullOverlap)
            .count();
    }

    public long part2(String inputFile) {
        return inputFile.lines()
            .map(this::readAssignments)
            .filter(Assignments::hasPartialOverlap)
            .count();
    }
}
