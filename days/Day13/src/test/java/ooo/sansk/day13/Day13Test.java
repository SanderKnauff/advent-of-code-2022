package ooo.sansk.day13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {
    private Day13 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day13();
    }

    @Test
    void testStringIterator() {
        final var toTest = "Hello World!";
        final var iterator = new Day13.StringIterator(toTest);

        for (int i = 0; i < toTest.length(); i++) {
            assertEquals(toTest.charAt(i), iterator.current());
            iterator.next();
        }

        assertNull(iterator.next());
    }

    @Test
    void testReadIntegerPacket_singleNumber() {
        final var result = this.subject.readPacket(new Day13.StringIterator("1337"));

        assertEquals(new Day13.IntegerPacket(1337), result);
    }

    @Test
    void testReadListPacket_empty() {
        final var result = this.subject.readPacket(new Day13.StringIterator("[]"));

        assertEquals(new Day13.ListPacket(List.of()), result);
    }

    @Test
    void testReadListPacket_singleValue() {
        final var result = this.subject.readPacket(new Day13.StringIterator("[1]"));

        assertEquals(new Day13.ListPacket(List.of(new Day13.IntegerPacket(1))), result);
    }

    @Test
    void testReadListPacket_multipleValues() {
        final var result = this.subject.readPacket(new Day13.StringIterator("[1,2]"));

        assertEquals(new Day13.ListPacket(List.of(new Day13.IntegerPacket(1), new Day13.IntegerPacket(2))), result);
    }

    @Test
    void testReadListPacket_nestedArrays() {
        final var result = this.subject.readPacket(new Day13.StringIterator("[[10]]"));

        assertEquals(new Day13.ListPacket(List.of(new Day13.ListPacket(List.of(new Day13.IntegerPacket(10))))), result);
    }

    @Test
    void testReadListPacket_complicatedMix() {
        final var result = this.subject.readPacket(new Day13.StringIterator("[[1],2,50,[1,[2,3]]]"));

        assertEquals(new Day13.ListPacket(List.of(
                new Day13.ListPacket(List.of(new Day13.IntegerPacket(1))),
                new Day13.IntegerPacket(2),
                new Day13.IntegerPacket(50),
                new Day13.ListPacket(List.of(
                        new Day13.IntegerPacket(1),
                        new Day13.ListPacket(List.of(
                                new Day13.IntegerPacket(2),
                                new Day13.IntegerPacket(3)
                        ))
                ))
        )), result);
    }

    @Test
    void testCompareIntegerPackets_lowShouldComeBeforeHigh() {
        final var low = new Day13.IntegerPacket(1);
        final var high = new Day13.IntegerPacket(2);

        assertEquals(low.compareTo(high), Day13.CompareResult.VALID);
        assertEquals(high.compareTo(low), Day13.CompareResult.INVALID);
        assertEquals(low.compareTo(low), Day13.CompareResult.UNDECIDED);
    }

    @Test
    void testCompareListPackets_leftShouldBeEmptyFirst() {
        final var empty = new Day13.ListPacket(List.of());
        final var oneElement = new Day13.ListPacket(List.of(new Day13.IntegerPacket(1)));

        assertEquals(empty.compareTo(oneElement), Day13.CompareResult.VALID);
        assertEquals(oneElement.compareTo(empty), Day13.CompareResult.INVALID);
        assertEquals(empty.compareTo(empty), Day13.CompareResult.VALID);
        assertEquals(oneElement.compareTo(oneElement), Day13.CompareResult.VALID);
    }

    @Test
    void testCompareListPackets_shouldCompareInts() {
        final var low = new Day13.ListPacket(List.of(new Day13.IntegerPacket(1)));
        final var high = new Day13.ListPacket(List.of(new Day13.IntegerPacket(2)));

        assertEquals(low.compareTo(high), Day13.CompareResult.VALID);
        assertEquals(high.compareTo(low), Day13.CompareResult.INVALID);
        assertEquals(low.compareTo(low), Day13.CompareResult.VALID);
        assertEquals(high.compareTo(high), Day13.CompareResult.VALID);
    }

    @Test
    void testComparePackets_intShouldBeTurnedIntoSingletonLists() {
        final var integer = new Day13.IntegerPacket(1);
        final var list = new Day13.ListPacket(List.of(new Day13.IntegerPacket(1)));

        assertEquals(list.compareTo(integer), Day13.CompareResult.VALID);
        assertEquals(integer.compareTo(list), Day13.CompareResult.VALID);
        assertEquals(list.compareTo(list), Day13.CompareResult.VALID);
    }

    @Test
    void testExamplesInSinglePairs() throws IOException {
        final var left1 = this.subject.readPacket(new Day13.StringIterator("[1,1,3,1,1]"));
        final var right1 = this.subject.readPacket(new Day13.StringIterator("[1,1,5,1,1]"));

        assertEquals(left1.compareTo(right1), Day13.CompareResult.VALID);

        final var left2 = this.subject.readPacket(new Day13.StringIterator("[[1],[2,3,4]]"));
        final var right2 = this.subject.readPacket(new Day13.StringIterator("[[1],4]"));

        assertEquals(left2.compareTo(right2), Day13.CompareResult.VALID);

        final var left3 = this.subject.readPacket(new Day13.StringIterator("[9]"));
        final var right3 = this.subject.readPacket(new Day13.StringIterator("[[8,7,6]]"));

        assertEquals(left3.compareTo(right3), Day13.CompareResult.INVALID);

        final var left4 = this.subject.readPacket(new Day13.StringIterator("[[4,4],4,4]"));
        final var right4 = this.subject.readPacket(new Day13.StringIterator("[[4,4],4,4,4]"));

        assertEquals(left4.compareTo(right4), Day13.CompareResult.VALID);

        final var left5 = this.subject.readPacket(new Day13.StringIterator("[7,7,7,7]"));
        final var right5 = this.subject.readPacket(new Day13.StringIterator("[7,7,7]"));

        assertEquals(left5.compareTo(right5), Day13.CompareResult.INVALID);

        final var left6 = this.subject.readPacket(new Day13.StringIterator("[]"));
        final var right6 = this.subject.readPacket(new Day13.StringIterator("[3]"));

        assertEquals(left6.compareTo(right6), Day13.CompareResult.VALID);

        final var left7 = this.subject.readPacket(new Day13.StringIterator("[[[]]]"));
        final var right7 = this.subject.readPacket(new Day13.StringIterator("[[]]"));

        assertEquals(left7.compareTo(right7), Day13.CompareResult.INVALID);

        final var left8 = this.subject.readPacket(new Day13.StringIterator("[1,[2,[3,[4,[5,6,7]]]],8,9]"));
        final var right8 = this.subject.readPacket(new Day13.StringIterator("[1,[2,[3,[4,[5,6,0]]]],8,9]"));

        assertEquals(left8.compareTo(right8), Day13.CompareResult.INVALID);
    }

    @Test
    void testListCompare_shouldShortCircuitAfterFirstCorrectResult() {
        final var left = this.subject.readPacket(new Day13.StringIterator("[2,3,4]"));
        final var right = this.subject.readPacket(new Day13.StringIterator("4"));

        assertEquals(left.compareTo(right), Day13.CompareResult.VALID);
    }

    @Test
    void testSerialize() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));
        final var pairs = input.split("(\\r?\\n){2}");

        for (String pair : pairs) {
            final var packets = pair.split("\\r?\\n");
            final var leftString = packets[0];
            final var left = this.subject.readPacket(new Day13.StringIterator(leftString));
            final var rightString = packets[1];
            final var right = this.subject.readPacket(new Day13.StringIterator(rightString));

            assertEquals(leftString, left.toString());
            assertEquals(rightString, right.toString());
        }
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part1(input);

        assertEquals(13, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var sumOfCorrectIndices = subject.part1(input);

        System.out.printf("The sum of the correct indices is '%d'%n", sumOfCorrectIndices);
    }

    @Disabled
    @Test
    void testPart2WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part2(input);

        assertEquals(140, result);
    }

    @Disabled
    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var decoderKey = subject.part2(input);

        System.out.printf("The decoderKey is %d%n", decoderKey);
    }
}
