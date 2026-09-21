package edu.vilniustech.hello;

import java.util.Optional;
import java.util.Scanner;

public class CommandReader {

    private final Scanner scanner;

    public CommandReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public Optional<Character> readKey() {
        System.out.print("> ");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Character.toUpperCase(line.charAt(0)));
    }
}
