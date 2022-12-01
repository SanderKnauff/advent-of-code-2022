package ooo.sansk.day1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {
    private Day1 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day1();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part1(input);

        assertEquals(24000, result);
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var mostCalories = subject.part1(input);

        System.out.printf("The elf with the most calories is carrying %d calories%n", mostCalories);
    }

    @Test
    void testPart2WithExample() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part2(input);

        assertEquals(45000, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var mostCalories = subject.part2(input);

        System.out.printf("The three elves that are carrying the most calories, are carrying a combined amount of %d calories%n", mostCalories);
    }
}
