package ooo.sansk.day12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {
    private Day12 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day12();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part1(input);

        assertEquals(31, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var steps = subject.part1(input);

        System.out.printf("The shortest route to the top is %d steps%n", steps);
    }

    @Test
    void testPart2WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part2(input);

        assertEquals(29, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var steps = subject.part2(input);

        System.out.printf("The shortest path from any lowest elevation to the top is %d steps%n", steps);
    }
}
