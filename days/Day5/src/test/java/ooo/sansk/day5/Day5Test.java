package ooo.sansk.day5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {
    private Day5 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day5();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part1(input);

        assertEquals("CMZ", result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var topCrates = subject.part1(input);

        System.out.printf("The top crates of the stacks are: [%s]%n", topCrates);
    }

    @Test
    void testPart2WithExample() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part2(input);

        assertEquals("MCD", result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var topCrates = subject.part2(input);

        System.out.printf("The top crates of the stacks are: [%s]%n", topCrates);
    }
}
