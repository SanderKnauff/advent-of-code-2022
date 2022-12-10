package ooo.sansk.day10;

import java.util.ArrayList;
import java.util.List;

public class Day10 {
    static interface Instruction {
        void process(CathodeRayTube cathodeRayTube);
    }

    static class NoopInstruction implements Instruction {
        @Override
        public void process(CathodeRayTube cathodeRayTube) {
            cathodeRayTube.incrementCycle();
        }
    }

    static class AddXInstruction implements Instruction {
        private final int amount;

        public AddXInstruction(int amount) {
            this.amount = amount;
        }

        @Override
        public void process(CathodeRayTube cathodeRayTube) {
            cathodeRayTube.addToRegister(this.amount);
        }
    }

    class CathodeRayTube {
        private final int SCREEN_WIDTH = 40;
        private int cycle;
        private long registerX;
        private final List<Long> signalStrengths;
        private StringBuilder display;

        public CathodeRayTube() {
            this.cycle = 0;
            this.registerX = 1;
            this.signalStrengths = new ArrayList<>();
            this.display = new StringBuilder();
        }

        public void incrementCycle() {
            this.signalStrengths.add(this.registerX);
            this.drawToDisplay();
            this.cycle++;
        }

        public void addToRegister(int amount) {
            this.incrementCycle();
            this.incrementCycle();
            this.registerX += amount;
        }

        public long getStrengthAtCycle(int cycle) {
            final var strength = this.signalStrengths.get(cycle - 1);
            return cycle * strength;
        }

        public void drawToDisplay() {
            final var position = this.cycle;
            final var horizontalPosition = position % SCREEN_WIDTH;
            if (position % SCREEN_WIDTH == 0 && this.cycle != 0) {
                display.append("\n");
            }

            final var spitePosition = this.signalStrengths.get(this.cycle);
            if(horizontalPosition >= (spitePosition - 1) && horizontalPosition <= (spitePosition + 1)) {
                display.append("#");
                return;
            }

            display.append(".");
        }

        public String renderDisplay() {
            return this.display.toString();
        }
    }

    public Instruction readInstructions(String instructionText) {
        final var parts = instructionText.split(" ");
        return switch (parts[0]) {
            case "noop" -> new NoopInstruction();
            case "addx" -> new AddXInstruction(Integer.parseInt(parts[1]));
            default -> throw new IllegalArgumentException("Instruction '%s' did not contain a known operation".formatted(instructionText));
        };
    }

    public long part1(String inputFile) {
        final var cathodeRayTube = new CathodeRayTube();
        inputFile.lines()
            .map(this::readInstructions)
            .forEach(instruction -> instruction.process(cathodeRayTube));

        return cathodeRayTube.getStrengthAtCycle(20)
            + cathodeRayTube.getStrengthAtCycle(60)
            + cathodeRayTube.getStrengthAtCycle(100)
            + cathodeRayTube.getStrengthAtCycle(140)
            + cathodeRayTube.getStrengthAtCycle(180)
            + cathodeRayTube.getStrengthAtCycle(220);
    }

    public String part2(String inputFile) {
        final var cathodeRayTube = new CathodeRayTube();
        inputFile.lines()
            .map(this::readInstructions)
            .forEach(instruction -> instruction.process(cathodeRayTube));

        return cathodeRayTube.renderDisplay();
    }
}
