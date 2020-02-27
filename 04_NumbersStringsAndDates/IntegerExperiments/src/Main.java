public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;
        System.out.println("Возьмём число: " + container.count);
        System.out.println("Сумма его цифр: " + sumDigits(container.count));
        System.out.println("Посчитаем вторым методом: " + otherSumDigits(container.count));
        // Домашнее задание 4.2
        System.out.println("Минимальное значение byte " + Byte.MIN_VALUE);
        System.out.println("Максимальное значение byte " + Byte.MAX_VALUE);
        System.out.println("Минимальное значение short " + Short.MIN_VALUE);
        System.out.println("Максимальное значение short " + Short.MAX_VALUE);
        System.out.println("Минимальное значение int " + Integer.MIN_VALUE);
        System.out.println("Максимальное значение int " + Integer.MAX_VALUE);
        System.out.println("Минимальное значение long " + Long.MIN_VALUE);
        System.out.println("Максимальное значение long " + Long.MAX_VALUE);
        System.out.println("Минимальное значение float -" + Float.MAX_VALUE);  // в float и double - минус хранится отдельно
        System.out.println("Максимальное значение float " + Float.MAX_VALUE);
        System.out.println("Минимальное значение double -" + Double.MAX_VALUE);  //MIN_VALUE там - минимальное положительное значение
        System.out.println("Максимальное значение double " + Double.MAX_VALUE);
    }

    public static Integer sumDigits(Integer number)  // сделал метод статическим, чтобы проверить
    {
        Integer sumOfDigits = 0;
        String digitSum = number.toString();
        for (int i = 0; i < digitSum.length(); i++)
        {
            Integer numberI = Character.digit(digitSum.charAt(i), 10);  // вычленяем цифры из нашего числа
            sumOfDigits = sumOfDigits + numberI;                          //складываем их
        }                              
        return sumOfDigits;
    }

    public static Integer otherSumDigits(Integer number)  // второй вариант реализации
    {
        Integer sumOfDigits = 0;
        String digitSum = number.toString();
        for (int i = 0; i < digitSum.length(); i++)
        {
            String numberO = String.valueOf(digitSum.charAt(i));  // вычленяем цифры из нашего числа
            Integer numberI = Integer.parseInt(numberO);          // переводим цифры из строкового типа в int
            sumOfDigits = sumOfDigits + numberI;                          //складываем их
        }
        return sumOfDigits;
    }
}
