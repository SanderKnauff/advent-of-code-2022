package ooo.sansk.day13;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day13 {
    enum CompareResult {
        VALID,
        INVALID,
        UNDECIDED
    }

    interface Packet {
        CompareResult compareTo(Packet other);
    }

    record ListPacket(List<Packet> packets) implements Packet {
        public CompareResult compareTo(Packet other) {
            return switch (other) {
                case ListPacket lp -> compareLists(this, lp);
                case IntegerPacket ip -> compareLists(this, new ListPacket(List.of(ip)));
                case default ->
                        throw new IllegalArgumentException("Unknown packet type '%s'".formatted(other.getClass().getSimpleName()));
            };
        }

        CompareResult compareLists(ListPacket one, ListPacket other) {
            for (int i = 0; i < one.packets.size(); i++) {
                if (i >= other.packets.size()) {
                    return CompareResult.INVALID;
                }
                final var left = one.packets.get(i);
                final var right = other.packets.get(i);
                final var result = left.compareTo(right);
                if (result.equals(CompareResult.UNDECIDED)) {
                    continue;
                }
                return result;
            }
            if (one.packets.size() == other.packets.size()) {
                return CompareResult.UNDECIDED;
            }
            return CompareResult.VALID;
        }

        @Override
        public String toString() {
            return "[%s]".formatted(packets.stream().map(Objects::toString).collect(Collectors.joining(",")));
        }
    }

    record IntegerPacket(int value) implements Packet {
        public CompareResult compareTo(Packet right) {
            return switch (right) {
                case IntegerPacket rightIntegerPacket -> {
                    if (this.value == rightIntegerPacket.value) {
                        yield CompareResult.UNDECIDED;
                    }
                    yield this.value > rightIntegerPacket.value
                            ? CompareResult.INVALID
                            : CompareResult.VALID;
                }
                case ListPacket lp -> lp.compareLists(new ListPacket(List.of(this)), lp);
                case default ->
                        throw new IllegalArgumentException("Unknown packet type '%s'".formatted(right.getClass().getSimpleName()));
            };
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    record Signal(Packet first, Packet second) {
        boolean inCorrectOrder() {
            return first.compareTo(second).equals(CompareResult.VALID);
        }
    }

    public static class StringIterator {
        private final String toIterate;
        private int currentIndex;
        private Character currentToken;

        public StringIterator(String toIterate) {
            this.toIterate = toIterate;
            this.currentIndex = 0;
            this.currentToken = this.next();
        }

        public Character next() {
            if (this.currentIndex >= this.toIterate.length()) {
                this.currentToken = null;
                return null;
            }
            this.currentToken = this.toIterate.charAt(this.currentIndex++);
            return this.currentToken;
        }

        public Character current() {
            return currentToken;
        }
    }

    public Packet readPacket(StringIterator iterator) {
        while (iterator.current() == ',') {
            iterator.next();
        }
        return switch (iterator.current()) {
            case '[' -> readListPacket(iterator);
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> readIntegerPacket(iterator);
            default ->
                    throw new IllegalArgumentException("Invalid packet token start '%s'".formatted(iterator.current()));
        };
    }

    private ListPacket readListPacket(StringIterator iterator) {
        final var list = new ArrayList<Packet>();
        iterator.next(); // Skip the opening '['
        while (iterator.current() != null && iterator.current() != ']') {
            list.add(readPacket(iterator));
        }
        iterator.next(); // Skip the closing ']'
        return new ListPacket(list);
    }

    private IntegerPacket readIntegerPacket(StringIterator iterator) {
        final var intBuilder = new StringBuilder();
        while (iterator.current() != null && iterator.current() != ',' && iterator.current() != ']') {
            intBuilder.append(iterator.current());
            iterator.next();
        }
        return new IntegerPacket(Integer.parseInt(intBuilder.toString()));
    }

    private Signal readPair(String pair) {
        final var packets = pair.split("\\r?\\n");
        final var left = readPacket(new StringIterator(packets[0]));
        final var right = readPacket(new StringIterator(packets[1]));
        return new Signal(left, right);
    }

    public int part1(String inputFile) {
        final var pairs = inputFile.split("(\\r?\\n){2}");
        var sumOfIndices = 0;
        for (int i = 0; i < pairs.length; i++) {
            final var pair = readPair(pairs[i]);
            if (pair.inCorrectOrder()) {
                sumOfIndices += (i + 1);
            }
        }
        return sumOfIndices;
    }

    public int part2(String inputFile) {
        final var serializedPackets = inputFile.split("\\r?\\n");
        final var packets = new ArrayList<Packet>();

        final var divider1 = readPacket(new StringIterator("[[2]]"));
        final var divider2 = readPacket(new StringIterator("[[6]]"));
        packets.add(divider1);
        packets.add(divider2);

        for (final var packet : serializedPackets) {
            if (packet.isEmpty()) {
                continue;
            }
            packets.add(readPacket(new StringIterator(packet)));
        }

        packets.sort(Comparator.comparing(Function.identity(), (o1, o2) -> o1.compareTo(o2).equals(CompareResult.INVALID) ? 1 : -1));

        var decoderKey = 1;
        for (int i = 0; i < packets.size(); i++) {
            final var packet = packets.get(i);
            if (packet.equals(divider1) || packet.equals(divider2)) {
                decoderKey *= (i + 1);
            }
        }

        return decoderKey;
    }
}
