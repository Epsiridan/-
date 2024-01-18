import java.util.Scanner;

public class Main {
    private static final char[] romanNumeralsGet = {'I', 'V', 'X'};
    private static final int[] romanValuesGet = {1, 5, 10};

    private static final String[] romanNumeralsSet = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
    private static final int[] romanValuesSet = {1, 4, 5, 9, 10, 40, 50, 90, 100};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение в формате: a оператор b (например, 1 + 9):");

        try {
            String input = scanner.nextLine();
            String result = calc(input);

            System.out.println("Результат: " + result);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String calc(String input) {
        try {
            String[] parts = input.split(" ");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат ввода");
            }

            String num1 = parts[0];
            String num2 = parts[2];
            char operator = parts[1].charAt(0);

            if (isRoman(num1) && isRoman(num2)) {
                return calculateRoman(num1, num2, operator);
            } else if (isRoman(num1) || isRoman(num2)) {
                throw new IllegalArgumentException("используются одновременно разные системы счисления");
            } else {
                int arabicResult = calculateArabic(Integer.parseInt(num1), Integer.parseInt(num2), operator);
                return String.valueOf(arabicResult);
            }

        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVX]+");
    }

    private static String calculateRoman(String roman1, String roman2, char operator) {
        try {
            int num1 = fromRoman(roman1);
            int num2 = fromRoman(roman2);

            int result = calculateArabic(num1, num2, operator);

            return toRoman(result);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при выполнении операции над римскими числами");
        }
    }

    private static int calculateArabic(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }
    }

    private static int fromRoman(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = getValue(roman.charAt(i));

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }


    private static String toRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("в римской системе нет отрицательных чисел или нуля");
        }

        StringBuilder result = new StringBuilder();
        int remaining = number;

        for (int i = romanNumeralsSet.length - 1; i >= 0; i--) {
            while (remaining >= romanValuesSet[i]) {
                result.append(romanNumeralsSet[i]);
                remaining -= romanValuesSet[i];
            }
        }
        return result.toString();
    }

    private static int getValue(char romanChar) {
        for (int i = 0; i < romanNumeralsGet.length; i++) {
            if (romanNumeralsGet[i] == romanChar) {
                return romanValuesGet[i];
            }
        }
        throw new IllegalArgumentException("Неверный символ римской цифры");
    }
}