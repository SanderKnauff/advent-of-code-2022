package ooo.sansk.day5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {
    record Instruction(int amount, int source, int destination) {
        void performCrateMover9000(List<Stack<Character>> crates) {
            for (int i = 0; i < amount; i++) {
                final var crate = crates.get(source - 1).pop();
                crates.get(destination - 1).push(crate);
            }
        }

        void performCrateMover9001(List<Stack<Character>> crates) {
            final var pickedUpStack = new LinkedList<Character>();
            for (int i = 0; i < amount; i++) {
                pickedUpStack.addFirst(crates.get(source - 1).pop());
            }

            for (Character crate : pickedUpStack) {
                crates.get(destination - 1).push(crate); 
            }
        }
    }

    private List<Character> readRow(String rowString) {
        final var crates = new ArrayList<Character>();
        for (int i = 1; i < rowString.length(); i += 4) {
            crates.add(rowString.charAt(i));
        }
        return crates;
    }

    private List<Stack<Character>> readStacks(String stackDefinition) {
        final var stacks = new ArrayList<Stack<Character>>();
        final var rows = stackDefinition.split("\n");

        for (int i = rows.length - 2; i >= 0; i--) {
            final var crates = readRow(rows[i]);
            for (int stackIndex = 0; stackIndex < crates.size(); stackIndex++) {
                final var crate = crates.get(stackIndex);
                Stack<Character> stack;
                if (stacks.size() <= stackIndex) {
                    stack = new Stack<>();
                    stacks.add(stack);
                } else {
                    stack = stacks.get(stackIndex);
                }

                if(crate == ' ') {
                    continue;
                }

                stack.push(crate);
            }
        }

        return stacks;
    }

    private Instruction readInstruction(String instruction) {
        final var pattern = Pattern.compile("move (?<amount>\\d+) from (?<source>\\d+) to (?<destination>\\d+)");
        final var matcher = pattern.matcher(instruction);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Expected instruction to match format 'move {x} from {y} to {z}', but was " + instruction);
        }
        return new Instruction(
            Integer.parseInt(matcher.group("amount")),
            Integer.parseInt(matcher.group("source")),
            Integer.parseInt(matcher.group("destination"))
        );
    }

    private List<Instruction> readInstructions(String instructions) {
        return instructions.lines()
            .map(this::readInstruction)
            .toList();
    }


    public String part1(String inputFile) {
        final var sections = inputFile.split("(\r?\n){2}");
        final var stacks = readStacks(sections[0]);

        readInstructions(sections[1]).forEach(instruction -> instruction.performCrateMover9000(stacks));

        return stacks.stream()
            .map(stack -> stack.peek())
            .map(crate -> crate + "")
            .collect(Collectors.joining());
    }

    public String part2(String inputFile) {
        final var sections = inputFile.split("(\r?\n){2}");
        final var stacks = readStacks(sections[0]);

        readInstructions(sections[1]).forEach(instruction -> instruction.performCrateMover9001(stacks));

        return stacks.stream()
            .map(stack -> stack.peek())
            .map(crate -> crate + "")
            .collect(Collectors.joining());
    }
}
