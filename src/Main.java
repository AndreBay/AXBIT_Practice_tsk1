import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\nTask 0:");
        Task0();
        System.out.println("\n\nTask 1:");
        Task1();
        System.out.println("\n\nTask 2:");
        Task2();
        System.out.println("\n\nTask 5:");
        task5();
        System.out.println("\n\nTask 7:");
        task7();
        System.out.println("\n\nTask 8:");
        task8();
        System.out.println("\n\nTask 9:");
        task9();

        //10-е недоделал
        System.out.println("\n\nTask 10:");
        //task10();
    }

    /*#0
    Напишите программу, которая выводит на экран числа от 1 до 100. При этом вместо чисел, кратных трем,
    программа должна выводить слово Fizz, а вместо чисел, кратных пяти — слово Buzz.
    Если число кратно пятнадцати, то программа должна выводить слово FizzBuzz.
    Задача может показаться очевидной, но нужно получить наиболее простое и красивое решение.*/

    private static void Task0() {
        for (int i = 0; i < 100; i += 1) {
            String printString = "";
            if (i % 3 == 0)
                printString += "Fizz ";
            if (i % 5 == 0)
                printString += "Buzz ";
            if (printString.equals(""))
                printString += " " + i + " ";
            else if(printString.equals("Fizz Buzz "))
                printString = "FizzBuzz";
            System.out.print(printString);
        }
    }

    /*#1
    Посчитать факториал вводимого числа из консоли, при этом код должен быть максимально оптимизированным.*/

    /*Воспользуемся алгоритмом вычисления деревом. Будем рекурсиво делить интервал данных нам чисел от L до R пополам,
     где R и L - крайние числа. Число получившееся в результате деления обозначим M.
     Признаком конца рекурсии будет то, что в интервале останется только 2 числа. После этого мы перемножим эти числа*/

    //Вспомогательная функция
    private static int ProdTree(int l, int r) {
        if (l > r)
            return 1;
        if (l == r)
            return l;
        if (r - l == 1)
            return l * r;
        int m = (l + r) / 2;
        return ProdTree(l, m) * ProdTree(m + 1, r);
    }

    // Основная функция
    private static void Task1() {
        int number;
        boolean flag = true;
        while (flag) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите число:");

            if (sc.hasNextInt()) {
                number = sc.nextInt();

                if (number < 0)
                    System.out.println("0");
                if (number == 0)
                    System.out.println("1");
                if (number == 1 || number == 2)
                    System.out.println(number);
                System.out.println(ProdTree(2, number));
                flag = false;
            } else
                System.out.println("Вы не ввели целое натуральное число. Введите целое натуральное число.");
        }
    }

    /*#2
    Классическая задача word count, т.е. посчитать количество каждого слова в файле и вывести в порядке убывания.*/
    private static void Task2() throws FileNotFoundException {
        File f = new File("./Task2File.txt");
        Scanner s = new Scanner(f);
        TreeMap<String, Integer> counts = new TreeMap<>();
        // Посчет количества каждого слова:
        while (s.hasNext()) {
            String word = s.next();
            if (!counts.containsKey(word))
                counts.put(word, 1);
            else
                counts.put(word, counts.get(word) + 1);
        }
        // Сортировка слов в порядке убывания
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        counts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        System.out.println("Количество слов: " + reverseSortedMap);
    }

    /*#5
    Реализуйте алгоритм для вывода всех корректных  (правильно открытых и закрытых) комбинаций из n пар круглых скобок.
    Пример ( ( () ) ), ( ()() ), ( () )(), ()( () ), ()()()*/
    private static void addBracket(ArrayList list, int l, int r, char[] str, int index) {
        if (l < 0 || r < l) return; // Некорректное состояние

        if (l == 0 && r == 0)  // условие выхода из рекурсии
            list.add(String.copyValueOf(str));
        else {
            str[index] = '(';
            addBracket(list, l - 1, r, str, index + 1);

            str[index] = ')';
            addBracket(list, l, r - 1, str, index + 1);
        }
    }

    private static void task5() {
        int count = 3;
        char[] str = new char[count * 2];
        ArrayList list = new ArrayList();
        addBracket(list, count, count, str, 0);
        for (Object s : list) {
            System.out.println(s);
        }
        System.out.println(list.size());
    }


    /*#7
    Сделать реверс целого числа (десятичного)*/
    private static void reverseMethod(int number) {
        if (number < 0) {
            System.out.print("-");
            number = Math.abs(number);
        }
        if (number > 0) {
            System.out.print(number % 10);
            reverseMethod(number / 10);
        }
    }

    private static void task7() {
        int num = -684356849;
        System.out.println("Число для реверса: \n" + num);
        System.out.println("Реверсированное число: ");
        reverseMethod(num);
    }

    /*#8
    Убрать все дубликаты слов в предложении*/

    private static void task8() {
        String str = "мама мыла раму, мыла ли; ;раму мама .";
        String[] array = new String[0];

        int start = 0;
        int end = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == ' ' || ch == ',' || ch == '.' || ch == ';' || ch == ':' || ch == '-') {
                end = i;
                String s = str.substring(start, end);
                //System.out.print(start + " " + end + " ");
                if (i + 1 != str.length() && str.charAt(i + 1) == ' ') {
                    start = end + 2;
                    i++;
                } else
                    start = end + 1;
                if (!isPresent(array, s)) {
                    array = newStringArray(array, s);
                }
                //System.out.println(s);
            }
        }
        String rezult = "";
        for (String s : array) {
            rezult += s + " ";
        }

        System.out.println(rezult.trim());
    }

    public static String[] newStringArray(String[] array, String str) {
        String[] newArray = new String[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[newArray.length - 1] = str;
        return newArray;
    }

    public static boolean isPresent(String[] array, String str) {
        for (String s : array) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }


    /*#9
    Дана строка вида:
    AAAABBBCCXYZDDDDEEEFFFAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB
    Необходимо получить строку вида:
    A4B3C2XYZD4E3F3A6B28
    которая строится путем подсчета количества последовательных символов.
    На печать выводится буква + его количество, кроме единицы*/

    private static void task9() {
        String s = "AAAABBBCCXYZDDDDEEEFFFAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB";
        int counter = 1;
        if (s.length() > 1) {
            int i = 1;
            while (i <= s.length()) {
                if(i == s.length())
                    s+="*";
                if (s.charAt(i) == s.charAt(i - 1)) {
                    counter++;
                }
                else {
                    if (counter != 1) {
                        System.out.print(s.charAt(i - 1) + String.valueOf(counter));
                    }
                    else {
                        System.out.print(s.charAt(i - 1));
                    }
                    counter = 1;
                }
                i++;
            }
        } else {
            System.out.println(s);
        }

    }



    /*#10
    Необходимо получить последовательность отрезков непрерывных чисел из массива.
    Пример [1, 5, 6, 8, 4, 9] -> [1-1, 4-6, 8-9]*/

    public static void quickSort(int[] array, int low, int high) {
        if (array.length == 0)
            return;//завершить выполнение если длина массива равна 0

        if (low >= high)
            return;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        int opora = array[middle];

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low, j = high;
        while (i <= j) {
            while (array[i] < opora) {
                i++;
            }

            while (array[j] > opora) {
                j--;
            }

            if (i <= j) {//меняем местами
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        // вызов рекурсии для сортировки левой и правой части
        if (low < j)
            quickSort(array, low, j);

        if (high > i)
            quickSort(array, i, high);
    }

    private static void task10(){
        int[] input = {1, 5, 6, 8, 4, 9};
        quickSort(input, 1, 5);
        ArrayList<Integer> output = new ArrayList<>();
        int i = 0;
        while(i < input.length - 1){
            if(input[i] == input[i + 1] - 1) {
                if(i + 1 == input.length - 1) {
                    output.add(input[i]);
                    output.add(input[i + 1]);
                }
                else
                    output.add(input[i]);
            }
            else {
                output.add(input[i]);
                if (output.size() == 1) {
                    System.out.println(output.get(0) + " - " + output.get(0));
                } else {
                    System.out.println(output.get(0) + " - " + output.get(output.size() - 1));
                }
                for (int j = 0; j < output.size(); j++)
                    output.remove(j);
            }
            i++;
        }
    }
}
