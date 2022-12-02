package ooo.sansk.day2;

public class Day2 {
    private static final int POINTS_LOSS = 0;
    private static final int POINTS_DRAW = 3;
    private static final int POINTS_WIN = 6;

    enum Shape {
        ROCK(1), PAPER(2), SCISSORS(3);

        final int points;

        Shape(int points) {
            this.points = points;
        }

        static Shape parse(String name) {
            return switch (name) {
                case "A", "X" -> ROCK;
                case "B", "Y" ->  PAPER;
                case "C", "Z" ->  SCISSORS;
                default -> throw new IllegalArgumentException(name + " is not a valid choice");
            };
        }
    }

    enum Outcome {
        LOSE(0), DRAW(3), WIN(6);

        final int points;

        Outcome(int points) {
            this.points = points;
        }

        static Outcome parse(String name) {
            return switch (name) {
                case "X" -> LOSE;
                case "Y" ->  DRAW;
                case "Z" ->  WIN;
                default -> throw new IllegalArgumentException(name + " is not a valid choice");
            };
        }
    }

    int points(Shape opponent, Shape yours) {
        if(yours.equals(opponent)) {
            return POINTS_DRAW + yours.points;
        }
        int outcome = switch (yours) {
            case ROCK -> opponent == Shape.PAPER ? POINTS_LOSS : POINTS_WIN;
            case PAPER -> opponent == Shape.SCISSORS ? POINTS_LOSS : POINTS_WIN;
            case SCISSORS -> opponent == Shape.ROCK ? POINTS_LOSS : POINTS_WIN;
        };
        return outcome + yours.points;
    }

    public int part1(String inputFile) {
        return inputFile.lines()
            .mapToInt(s -> points(Shape.parse(s.substring(0, 1)), Shape.parse(s.substring(2,3))))
            .sum();
    }

    int forceOutcome(Shape opponent, Outcome outcome) {
        return switch (opponent) {
            case ROCK -> switch (outcome) {
                case LOSE -> Shape.SCISSORS.points + outcome.points;
                case DRAW -> Shape.ROCK.points + outcome.points;
                case WIN -> Shape.PAPER.points + outcome.points;
            };
            case PAPER -> switch (outcome) {
                case LOSE -> Shape.ROCK.points + outcome.points;
                case DRAW -> Shape.PAPER.points + outcome.points;
                case WIN -> Shape.SCISSORS.points + outcome.points;
            };
            case SCISSORS -> switch (outcome) {
                case LOSE -> Shape.PAPER.points + outcome.points;
                case DRAW -> Shape.SCISSORS.points + outcome.points;
                case WIN -> Shape.ROCK.points + outcome.points;
            };
        };
    }

    public int part2(String inputFile) {
        return inputFile.lines()
            .mapToInt(s -> forceOutcome(Shape.parse(s.substring(0, 1)), Outcome.parse(s.substring(2,3))))
            .sum();
    }
}
