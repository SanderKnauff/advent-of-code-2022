package ooo.sansk.day7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {
    private Day7 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day7();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part1(input);

        assertEquals(95437, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var size = subject.part1(input);

        System.out.printf("The sum of the size of the directories under 100000 large is %d%n", size);
    }

    @Test
    void testPart2WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = this.subject.part2(input);

        assertEquals(24933642, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var size = subject.part2(input);

        System.out.printf("The biggest directory that would free up enough space has a size of %d%n", size);
    }
}
