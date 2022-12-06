package ooo.sansk.day6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {
    private Day6 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day6();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readAllLines(Path.of("src/test/resources/part1.example.input"));

        input.forEach(line -> {
            final var testInput = line.split(" ");
            final var result = subject.part1(testInput[0]);
            assertEquals(Integer.parseInt(testInput[1]), result);
        });
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var messageStart = subject.part1(input);

        System.out.printf("The message starts at index %d%n", messageStart);
    }

    @Test
    void testPart2WithExample() throws IOException {
        // This uses the same data as part 1
        final var input = Files.lines(Path.of("src/test/resources/part1.example.input"));

        input.forEach(line -> {
            final var testInput = line.split(" ");
            final var result = subject.part2(testInput[0]);
            assertEquals(Integer.parseInt(testInput[2]), result);
        });
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var messageStart = subject.part2(input);

        System.out.printf("The message starts at index %d%n", messageStart);
    }
}
