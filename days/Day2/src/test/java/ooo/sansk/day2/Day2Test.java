package ooo.sansk.day2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {
    private Day2 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day2();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part1(input);

        assertEquals(15, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var totalPoints = subject.part1(input);

        System.out.printf("Using the guide, you would end up with %d points%n", totalPoints);
    }

    @Test
    void testPart2WithExample() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part2(input);

        assertEquals(12, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var totalPoints = subject.part2(input);

        System.out.printf("Using the (correctly decrypted) guide, you would end up with %d points%n", totalPoints);
    }
}
