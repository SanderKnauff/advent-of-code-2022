package ooo.sansk.day11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day11 {
    class Monkey {
        private static BigInteger lcm = BigInteger.ONE;

        private final List<BigInteger> items;
        private final Function<BigInteger, BigInteger> operation;
        private final Function<BigInteger, Integer> test;
        private BigInteger timesInspected;

        public Monkey(List<BigInteger> items, Function<BigInteger, BigInteger> operation, Function<BigInteger, Integer> test) {
            this.items = items;
            this.operation = operation;
            this.test = test;
            this.timesInspected = BigInteger.ZERO;
        }

        void doRound(List<Monkey> otherMonkeys, boolean isPanicking) {
            for(int i = 0; i < items.size(); i++) {
                var item = items.get(i);
                item = this.operation.apply(item);
                this.timesInspected = this.timesInspected.add(BigInteger.ONE);
                if (!isPanicking) {
                    item = item.divide(BigInteger.valueOf(3L));
                }
                final var toThrowTo = test.apply(item);
                otherMonkeys.get(toThrowTo).getItems().add(item.mod(lcm));
            }
            items.clear();
        }

        public BigInteger getTimesInspected() {
            return timesInspected;
        }

        public List<BigInteger> getItems() {
            return items;
        }
    }

    private List<Monkey> readMonkeys(String serializedMonkeys) {
        final var monkeys = serializedMonkeys.split("\\r?\\n\\r?\\n");

        return Arrays.stream(monkeys)
            .map(this::deserializeMonkey)
            .toList();
    }

    private Monkey deserializeMonkey(String serializedMonkey) {
        final var lines = serializedMonkey.split("\\r?\\n");
        final var startingItems = readStartingItems(lines[1].split(":")[1]);
        final var operation = readOperation(lines[2].split("Operation: new = old\s")[1]);
        final var toThrow = readDestination(lines[3], lines[4], lines[5]);

        return new Monkey(startingItems, operation, toThrow);
    }

    private List<BigInteger> readStartingItems(String items) {
        final var itemListString = items.trim();
        final var itemsSplit = itemListString.split(",\\s");

        final var itemsList = new ArrayList<BigInteger>();
        for (String item : itemsSplit) {
            itemsList.add(new BigInteger(item));
        }
        return itemsList;
    }

    private Function<BigInteger, BigInteger> readOperation(final String next) {
        final var operant = next.charAt(0);
        final BiFunction<BigInteger, BigInteger, BigInteger> operate = switch (operant) {
            case '+' -> new BiFunction<BigInteger, BigInteger, BigInteger>() {
                public BigInteger apply(BigInteger a, BigInteger b) { 
                    return a.add(b); 
                };
            };
            case '*' -> new BiFunction<BigInteger, BigInteger, BigInteger>() {
                public BigInteger apply(BigInteger a, BigInteger b) { return a.multiply(b); };
            };
            default -> throw new IllegalArgumentException("Unknown operator " + operant);
        };

        final var valueToOperateOn = next.substring(2);
        return (old) -> {
            return operate.apply(old, valueToOperateOn.equals("old") ? old : new BigInteger(valueToOperateOn));
        };
    }

    private Function<BigInteger, Integer> readDestination(String condition, String trueDestination, String falseDestination) {
        final var conditionWords = condition.split(" ");
        final var modulo = new BigInteger(conditionWords[conditionWords.length - 1]);

        Monkey.lcm = Monkey.lcm.multiply(modulo);

        final var targetIfTrueWords = trueDestination.split(" ");
        final var trueTarget = Integer.parseInt(targetIfTrueWords[targetIfTrueWords.length - 1]);

        final var targetIfFalseWords = falseDestination.split(" ");
        final var falseTarget = Integer.parseInt(targetIfFalseWords[targetIfFalseWords.length - 1]);
        
        return (value) -> value.mod(modulo).equals(BigInteger.ZERO)
            ? trueTarget
            : falseTarget;
    }

    public BigInteger part1(String inputFile) {
        final var monkeys = readMonkeys(inputFile);
        for (int i = 0; i < 20; i++) {
            monkeys.forEach(monkey -> { 
                monkey.doRound(monkeys, false);
            });
        }

        final var worries = new ArrayList<>(monkeys.stream().map(Monkey::getTimesInspected).toList());
        worries.sort(BigInteger::compareTo);
        worries.sort(Comparator.reverseOrder());
            
        return worries.get(0).multiply(worries.get(1));
    }

    public BigInteger part2(String inputFile) {
        final var monkeys = readMonkeys(inputFile);
        for (int i = 0; i < 10000; i++) {
            monkeys.forEach(monkey -> { 
                monkey.doRound(monkeys, true);
            });
        }

        final var worries = new ArrayList<>(monkeys.stream().map(Monkey::getTimesInspected).toList());
        worries.sort(BigInteger::compareTo);
        worries.sort(Comparator.reverseOrder());

        return worries.get(0).multiply(worries.get(1));
    }
}
