package ooo.sansk.day8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    private Day8 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day8();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part1(input);

        assertEquals(21, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var amountVisible = subject.part1(input);

        System.out.printf("There are %d trees visible from the edges of the forrest%n", amountVisible);
    }

    @Test
    void testPart2WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part2(input);

        assertEquals(8, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var scenicScore = subject.part2(input);

        System.out.printf("The highest scenic score out of all trees is %d%n", scenicScore);
    }
}
