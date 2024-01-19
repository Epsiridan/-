import java.util.Scanner;

public class Main {
    private static final char[] romanNumeralsGet = {'I', 'V', 'X', 'L', 'C'};
    private static final int[] romanValuesGet = {1, 5, 10, 50, 100};

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

    private static String calc(String input) throws IllegalArgumentException {
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
                try {
                    int arabicResult = calculateArabic(Integer.parseInt(num1), Integer.parseInt(num2), operator);
                    return String.valueOf(arabicResult);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVXLC]+");
    }

    private static String calculateRoman(String roman1, String roman2, char operator) throws IllegalArgumentException {
        try {
            int num1 = fromRoman(roman1);
            int num2 = fromRoman(roman2);

            int result = calculateArabic(num1, num2, operator);
            return toRoman(result);

        } catch (Exception e) {
            throw new IllegalArgumentException("ошибка при работе с римскими числами, " + e.getMessage());
        }
    }

    private static int calculateArabic(int num1, int num2, char operator) throws IllegalArgumentException, ArithmeticException {

        if ((num1 <= 10 && num2 <= 10) && (num1 > 0 && num2 > 0)) {
            switch (operator) {
                case '+':
                    return num1 + num2;
                case '-':
                    return num1 - num2;
                case '*':
                    return num1 * num2;
                case '/':
                    if (num2 == 0) {
                        throw new ArithmeticException("деление на ноль");
                    }
                    return num1 / num2;
                default:
                    throw new IllegalArgumentException("неверный оператор");
            }
        } else if ((num1 < 1 || num2 < 1)) {
            throw new IllegalArgumentException("одно или оба введённые вами числа меньше 1");
        } else {
            throw new IllegalArgumentException("одно или оба введённые вами числа больше 10");
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


    private static String toRoman(int number) throws IllegalArgumentException {
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

    private static int getValue(char romanChar) throws IllegalArgumentException {
        for (int i = 0; i < romanNumeralsGet.length; i++) {
            if (romanNumeralsGet[i] == romanChar) {
                return romanValuesGet[i];
            }
        }
        throw new IllegalArgumentException("неверный символ римской цифры");
    }
}