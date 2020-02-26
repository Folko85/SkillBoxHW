public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;
        System.out.println("Возьмём число: " + container.count);
        System.out.println("Сумма его цифр: " + sumDigits(container.count));
        System.out.println("Посчитаем вторым методом: " + otherSumDigits(container.count));
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
