import java.util.Scanner;

public class Main {
    //Проверка на число
    static boolean isNumeric(String numString) {
        try {
            Integer.parseInt(numString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Перевод в римские числа
    static String ToRoman(String numString) {
        Roman[] romanNumbers = Roman.values();
        int numInt = Integer.parseInt(numString), i = 13;
        numString = "";

        while (numInt > 0) {

            while (romanNumbers[i].getArabic() > numInt) {
                i--;
            }
            numString += romanNumbers[i];
            numInt -= romanNumbers[i].getArabic();
        }

        return numString;
    }

    public static String calc(String input) throws MyIOException {
        //Нет ввода - нет смысла нагружать
        if (input.isEmpty())
            throw new MyIOException("Пустое выражение");


        //Все переменные
        String argString1, argString2, result = "0";
        String[] temp;
        int argInt1, argInt2;
        char operator = ' ';
        boolean arabOrRoman; //Арабские - true, Римские - false
        //___________________________________________________________________

        //Обработка входящей строки
        temp = input.split("[+-/*]");
        if (temp.length == 1)
            throw new MyIOException("Cтрока не является математической операцией");
        else if (temp.length != 2)
            throw new MyIOException("Формат математической операции (выражения) не удовлетворяет заданию - два целочисленных операнда и один оператор (+, -, /, *)");

        argString1=temp[0].replaceAll(" ","");
        argString2=temp[1].replaceAll(" ","");
        if(argString1.length()>5||argString2.length()>5) throw new MyIOException("Неверный ввод чисел, используйте числа от 1 до 10 или от I до X");
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '/' || input.charAt(i) == '*')
                operator = input.charAt(i);
        }
        if(operator == ' ')
            throw new MyIOException("Формат математической операции (выражения) не удовлетворяет заданию - два целочисленных операнда и один оператор (+, -, /, *)");

        //Флаг вычисления в арабском или римском формате
        arabOrRoman = isNumeric(argString1) && isNumeric(argString2);

        //Вычисление с заданными операндами и оператором
        if (isNumeric(argString1) == isNumeric(argString2)) {
            if (arabOrRoman) {
                argInt1 = Integer.parseInt(argString1);
                argInt2 = Integer.parseInt(argString2);

                if (argInt1 > 10 || argInt1 < 1 || argInt2 > 10 || argInt2 < 1)
                    throw new MyIOException("Неверный ввод чисел, используйте числа от 1 до 10");

                switch (operator) {
                    case '+':
                        result = String.valueOf(argInt1 + argInt2);
                        break;
                    case '-':
                        result = String.valueOf(argInt1 - argInt2);
                        break;
                    case '/': {
                        result = String.valueOf(argInt1 / argInt2);
                        break;
                    }
                    case '*':
                        result = String.valueOf(argInt1 * argInt2);
                        break;
                }
            } else {
                Roman romanNumber1,romanNumber2;
                try {
                    romanNumber1 = Roman.valueOf(argString1);
                    romanNumber2 = Roman.valueOf(argString2);
                } catch (IllegalArgumentException e) {
                    throw new MyIOException("Неверный ввод римских чисел, используйте числа от I до X");
                }
                argInt1 = romanNumber1.getArabic();
                argInt2 = romanNumber2.getArabic();
                if (argInt1 > 10 || argInt1 < 1 || argInt2 > 10 || argInt2 < 1)
                    throw new MyIOException("Неверный ввод римских чисел, используйте числа от I до X");

                switch (operator) {
                    case '+':
                        result = String.valueOf(argInt1 + argInt2);
                        result = ToRoman(result);
                        break;
                    case '-':
                        result = String.valueOf(argInt1 - argInt2);
                        if (Integer.parseInt(result) < 1)
                            throw new MyIOException("В римской системе нет чисел меньше 1 (единицы)");
                        result = ToRoman(result);
                        break;
                    case '/': {
                        result = String.valueOf(argInt1 / argInt2);
                        if (Integer.parseInt(result) < 1)
                            throw new MyIOException("В римской системе нет чисел меньше 1 (единицы)");
                        result = ToRoman(result);
                        break;
                    }
                    case '*':
                        result = String.valueOf(argInt1 * argInt2);
                        result = ToRoman(result);
                        break;
                }
            }

        } else
            throw new MyIOException("Используются одновременно разные системы счисления");

        return String.valueOf(result);

    }


    public static void main(String[] args) throws MyIOException {
        while (true) {
            System.out.println("Введите выражение:");
            Scanner sc = new Scanner(System.in);
            System.out.println(calc(sc.nextLine()));
        }
    }
}