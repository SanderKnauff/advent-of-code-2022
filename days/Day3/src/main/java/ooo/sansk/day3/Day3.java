package ooo.sansk.day3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 {
    record Backpack(
        Set<Character> firstCompartment,
        Set<Character> secondCompartment
    ) {
    }

    public int part1(String inputFile) {
        return inputFile.lines()
            .map(this::readBackpack)
            .map(this::findDuplicate)
            .mapToInt(this::getPriority)
            .sum();
    }

    public int part2(String inputFile) {
        final var lines = inputFile.lines().toList();
        List<Integer> badgePriorities = new ArrayList<>();
        Set<Character> duplicates = new HashSet<>();
        for (int i = 0; i < lines.size(); i++) {
            final var backpack = lines.get(i);
            final var items = stringToCharacterSet(backpack);
            if (i % 3 == 0) {
                duplicates.clear();
                duplicates.addAll(items);
            } else {
                duplicates.retainAll(items);
            }

            if (i % 3 != 2) {
                continue;
            }

            final var badge = duplicates.stream().toList().get(0);
            badgePriorities.add(getPriority(badge));
        }

        return badgePriorities.stream().mapToInt(a -> a).sum();
    }

    public Backpack readBackpack(String backpack) {
        final var items = backpack.length() / 2;

        final var firstCompartmentItems = stringToCharacterSet(backpack.substring(0, items));
        final var secondCompartmentItems = stringToCharacterSet(backpack.substring(items));

        return new Backpack(firstCompartmentItems, secondCompartmentItems);
    }

    public Set<Character> stringToCharacterSet(String string) {
        final var result = new HashSet<Character>();
        for (char c : string.toCharArray()) {
            result.add(c);
        }
        return Set.copyOf(result);
    }

    public Character findDuplicate(Backpack backpack) {
        final var duplicates = new HashSet<>(backpack.firstCompartment);
        duplicates.retainAll(backpack.secondCompartment);
        if (duplicates.size() != 1) {
            throw new IllegalArgumentException("Backpack contained more than 1 duplicate");
        }
        return duplicates.stream().toList().get(0);
    }

    public int getPriority(Character character) {
        if (character >= 'a' && character <= 'z') {
            return (character - 'a') + 1;
        }

        if (character >= 'A' && character <= 'Z') {
            return (character - 'A') + 27;
        }

        throw new IllegalArgumentException("'%s' does not have a priority".formatted(character));
    }
}
