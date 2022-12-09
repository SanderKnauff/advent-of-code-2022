package ooo.sansk.day9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {
    private Day9 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day9();
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

        final var uniquePlaces = subject.part1(input);

        System.out.printf("The tail has been in '%d' unique places%n", uniquePlaces);
    }

    @Test
    void testPart2WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part2.example.input"));

        final var result = this.subject.part2(input);

        assertEquals(36, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var uniquePlaces = subject.part2(input);

        System.out.printf("The end of the rope has been in '%d' unique places%n", uniquePlaces);
    }
}
