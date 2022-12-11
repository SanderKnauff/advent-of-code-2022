package ooo.sansk.day11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
    private Day11 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day11();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part1(input);

        assertEquals(BigInteger.valueOf(10605), result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var sumOfStrengths = subject.part1(input);

        System.out.printf("After 20 rounds the level of monkey business is %d%n", sumOfStrengths);
    }

    @Test
    void testPart2WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part2(input);

        assertEquals(BigInteger.valueOf(2713310158L), result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var sumOfStrengths = subject.part2(input);

        System.out.printf("After 20 rounds the level of monkey business is %d%n", sumOfStrengths);
    }
}
