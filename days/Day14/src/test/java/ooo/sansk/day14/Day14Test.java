package ooo.sansk.day14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    private Day14 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day14();
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part1(input);

        assertEquals(24, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var sandDropped = subject.part1(input);

        System.out.printf("After %d sand particles, the sand starts dropping into the great nothingness of the bottomless void%n", sandDropped);
    }

    @Test
    void testPart2WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part2(input);

        assertEquals(93, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var sandDropped = subject.part2(input);

        System.out.printf("After %d sand particles, the sand starts has piled up into the great sky of sandparticle sources%n", sandDropped);
    }
}
