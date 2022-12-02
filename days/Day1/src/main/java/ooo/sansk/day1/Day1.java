package ooo.sansk.day1;

import java.util.Arrays;
import java.util.Comparator;

import static java.util.function.Predicate.not;

public class Day1 {
public int part1(String inputFile) {
    return Arrays.stream(inputFile.split("(\r?\n){2}"))
        .map(this::sumCaloriesInInventory)
        .max(Integer::compare)
        .orElseThrow(() -> new RuntimeException("Could not get any calorie count"));
}

public int part2(String inputFile) {
    return Arrays.stream(inputFile.split("(\r?\n){2}"))
        .map(this::sumCaloriesInInventory)
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .mapToInt(num -> num)
        .sum();
}

public int sumCaloriesInInventory(String inventory) {
    return Arrays.stream(inventory.split("\n"))
        .map(String::strip)
        .filter(not(String::isEmpty))
        .mapToInt(Integer::valueOf)
        .sum();
}
}
