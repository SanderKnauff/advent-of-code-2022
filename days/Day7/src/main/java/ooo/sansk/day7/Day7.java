package ooo.sansk.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Day7 {
    interface FileSystemItem {
        String name();
        long size();
    }

    record File(String name, long size) implements FileSystemItem {}

    class Directory implements FileSystemItem {
        private final Map<String, FileSystemItem> items;
        private final String name;

        Directory(String name) {
            this.name = name;
            this.items = new HashMap<String, FileSystemItem>();
        }

        public Optional<FileSystemItem> getItem(String path) {
            return Optional.ofNullable(this.items.get(path));
        }

        public void createFile(FileSystemItem item) {
            if (this.items.containsKey(item.name())) {
                throw new IllegalArgumentException("An file or directory with name");
            }

            this.items.put(item.name(), item);
        }

        public String name() {
            return this.name;
        }

        public long size() {
            return items.values()
                .stream()
                .mapToLong(FileSystemItem::size)
                .sum();
        }

        public List<Directory> listAllDirectories() {
            final var ret = new ArrayList<Directory>();
            for (FileSystemItem value : items.values()) {
                if (value instanceof Directory directory) {
                    ret.add(directory);
                    ret.addAll(directory.listAllDirectories());
                }
            }
            return ret;
        }
    }

    class Shell {
        private final Map<String, Consumer<String>> commands = Map.of(
            "cd", this::doCd,
            "ls", this::readLs
        );

        private Stack<Directory> path;

        Shell() {
            this.path = new Stack<>();
            this.path.push(new Directory("/"));
        }

        public void handleCommand(String commandString) {
            final var parts = commandString.split("\\s+|(\\r?\\n)", 2);
            performCommand(parts[0], parts[1]);
        }

        public void performCommand(String commandName, String parameters) {
            final var command = commands.get(commandName);
            if (command == null) {
                throw new IllegalArgumentException("Unknown command " + commandName);
            }

            command.accept(parameters);
        }

        public Directory getRoot() {
            return path.firstElement();
        }

        private void readLs(String output) {
            for (String lines : output.split("\\r?\\n")) {
                final var data = lines.split(" ");
                if (data[0].equals("dir")) {
                    path.peek().createFile(new Directory(data[1]));
                    continue;
                }

                path.peek().createFile(new File(data[1], Integer.parseInt(data[0])));
            }
        }

        private void doCd(String parameter) {
            if (parameter.equals("/")) {
                final var root = path.firstElement();
                path.clear();
                path.push(root);
                return;
            }

            if (parameter.equals("..")) {
                path.pop();
                return;
            }

            final var currentDirectory = path.peek();
            var directory = currentDirectory.getItem(parameter).orElseThrow(() -> new IllegalArgumentException("Directory '%s' does not exist in '%s'. Found: '%s'".formatted(parameter, currentDirectory.name(), currentDirectory.items.values())));
            path.push((Directory) directory);
        }
    }

    public long part1(String inputFile) {
        final var shell = new Shell();
        final var commands = inputFile.split("\\$");

        Arrays.stream(commands)
            .filter(Predicate.not(String::isEmpty))
            .map(String::trim)
            .forEach(shell::handleCommand);

        return shell.getRoot().listAllDirectories()
            .stream()
            .mapToLong(Directory::size)
            .filter(l -> l <= 100_000)
            .sum();
    }

    public long part2(String inputFile) {
        final var shell = new Shell();
        final var commands = inputFile.split("\\$");

        Arrays.stream(commands)
            .filter(Predicate.not(String::isEmpty))
            .map(String::trim)
            .forEach(shell::handleCommand);

        final var freeSpace = 70_000_000L;
        final var updateSize = 30_000_000L;

        final var usedSpace = freeSpace - shell.getRoot().size();
        final var spaceToClean = updateSize - usedSpace;

        return shell.getRoot()
            .listAllDirectories()
            .stream()
            .filter(directory -> directory.size() >= spaceToClean)
            .mapToLong(Directory::size)
            .min()
            .orElse(-1);
    }
}
