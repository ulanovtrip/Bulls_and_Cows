package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void printRange(int range) {
        String allPossible = "0123456789abcdefghijklmnopqrstuvwxyz";
        if (range <= 10) {
            System.out.print(" (0-" + allPossible.charAt(range - 1) + ").");
        } else {
            System.out.print(" (0-9, a-" + allPossible.charAt(range - 1) + ").");
        }
    }

    public static void generateStars(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("*");
        }
    }

    public static boolean findBullsAndCows(String[] input, String generatedCode) {
        List<String> list = new ArrayList<>();
        String secretCode = generatedCode;
        int cows = 0;
        int bulls = 0;

        for (int i = 0; i < input.length; i++) {
            String stepSecretCode = String.valueOf(secretCode.charAt(i));
            if (stepSecretCode.equals(input[i])) {
                bulls += 1;
            } else {
                list.add(input[i]);
            }
            if (bulls == secretCode.length()) {
                System.out.println("Grade: " + bulls + " bulls");
                return true;
            }
        }

        for (int i = 0; i < input.length; i++) {
            String stepSecretCode = String.valueOf(secretCode.charAt(i));
            if (list.contains(stepSecretCode)) {
                cows += 1;
            }
        }

        if (cows > 0 && bulls > 0) {
            System.out.println("Grade: " + bulls + " bull and " + cows + " cow");
        }

        if (cows == 0 && bulls == 0) {
            System.out.println("Grade: None");
        }

        if (bulls == 0 && cows > 0) {
            System.out.println("Grade: " + cows + " cows");
        }

        if (cows == 0 && bulls > 0) {
            System.out.println("Grade: " + bulls + " bulls");
        }
        return false;
    }

    public static String generatePseudoNumber(int inputNumber) {
        List<Long> list = new ArrayList<>();
        Random random = new Random();

        if (inputNumber > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + inputNumber
                    + " because there aren't enough unique digits.");
        } else {
            while (list.size() <= inputNumber - 1) {
                long number = random.nextInt(10);
                int size = list.size();
                if (size == 0 && number == 0) {
                    list.add(++number);
                } else if (!list.contains(number)) {
                    list.add(number);
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Long s : list) {
            stringBuilder.append(s);
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        int lengthCode = 0;
        StringBuilder builder = new StringBuilder();
        try {
            String codeInString = scanner.nextLine();
            builder.append(codeInString);
            lengthCode = Integer.parseInt(codeInString);
        } catch (Exception exception) {
            System.out.println("Error: " + "\"" + builder + "\"" + " isn't a valid number.");
            return;
        }

        if (lengthCode == 0) {
            System.out.println("Error: ");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int range = scanner.nextInt();

        if (range > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        if (lengthCode > range) {
            System.out.println("Error: it's not possible to generate a code with a length of " + lengthCode +
                    " with " + range + " unique symbols.");
            return;
        }

        System.out.print("The secret code is prepared: ");
        generateStars(lengthCode);
        printRange(range);
        String secretCode = generatePseudoNumber(lengthCode);
        System.out.println("Okay, let's start a game!");
        int turns = 0;
        while (true) {
            turns++;
            System.out.println("Turn " + turns + ":");
            String usersTry = scanner.next();
            String[] userInput = usersTry.split("");
            boolean status = findBullsAndCows(userInput, secretCode);
            if (status) {
                break;
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }
}
