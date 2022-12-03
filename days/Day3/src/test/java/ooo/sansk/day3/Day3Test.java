package ooo.sansk.day3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {
    private Day3 subject;

    @BeforeEach
    void setUp() {
        this.subject = new Day3();
    }

    @Test
    void testPart1WithExample() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part1(input);

        assertEquals(157, result);
    }

    @Test
    void getPriority_a_shouldReturn1() {
        assertEquals(1, subject.getPriority('a'));
    }

    @Test
    void getPriority_z_shouldReturn26() {
        assertEquals(26, subject.getPriority('z'));
    }

    @Test
    void getPriority_A_shouldReturn27() {
        assertEquals(27, subject.getPriority('A'));
    }

    @Test
    void getPriority_Z_shouldReturn52() {
        assertEquals(52, subject.getPriority('Z'));
    }

    @Test
    void runPart1() throws IOException {
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var duplicatePriorities = subject.part1(input);

        System.out.printf("The sum of priorities of the duplicate items is %d%n", duplicatePriorities);
    }

    @Test
    void testPart2WithExample() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.example.input"));

        final var result = subject.part2(input);

        assertEquals(70, result);
    }

    @Test
    void runPart2() throws IOException {
        // This uses the same data as part 1
        final var input = Files.readString(Path.of("src/test/resources/part1.input"));

        final var totalBadgePriorities = subject.part2(input);

        System.out.printf("The total priorities of the badges was %d%n", totalBadgePriorities);
    }
}
