package ooo.sansk.day6;

import java.lang.reflect.Array;
import java.util.Objects;

public class Day6 {
    static class CircularBuffer<T> {
        private final T[] items;
        private int pointer;

        @SuppressWarnings("unchecked")
        public CircularBuffer(Class<T> clazz, int size) {
            this.items = (T[]) Array.newInstance(clazz, size);
            this.pointer = 0;
        }

        void append(T item) {
            items[pointer % items.length] = item;
            pointer++;
        }

        boolean allUnique() {
            var unique = true;
            for (int i = 0; i < items.length && unique; i++) {
                for (int j = i; j < items.length && unique; j++) {
                    if (i != j && Objects.equals(items[i], items[j])) {
                        unique = false;
                    }
                }
            }
            return unique;
        }

        boolean hasNull() {
            return pointer <= items.length;
        }
    }


    public int part1(String inputFile) {
        final var buffer = new CircularBuffer<>(Byte.class, 4);

        for (byte item : inputFile.getBytes()) {
            if (buffer.allUnique() && !buffer.hasNull()) {
                break;
            }
            buffer.append(item);
        }

        return buffer.pointer;
    }

    public int part2(String inputFile) {
        final var buffer = new CircularBuffer<>(Byte.class, 14);

        for (byte item : inputFile.getBytes()) {
            if (buffer.allUnique() && !buffer.hasNull()) {
                break;
            }
            buffer.append(item);
        }

        return buffer.pointer;
    }
}
