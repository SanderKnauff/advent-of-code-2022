package ooo.sansk.day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
    private Day4 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day4();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part1(input);

        assertEquals(2, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var overlaps = subject.part1(input);

        System.out.printf("There were %d assignments with full overlaps%n", overlaps);
    }

    @Test
    void testPart2WithExample() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part2(input);

        assertEquals(4, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var overlaps = subject.part2(input);

        System.out.printf("There were %d assignments with partial overlaps%n", overlaps);
    }
}
