
public class Loader
{
    public static void main(String[] args)
    {
        Cat.count = 0;         //счётчик котов. Пока котов нет
        int catQuantity = 5;     //сколько котов мы будем создавать изначально

        Cat[] catArray = new Cat[catQuantity];  //чтобы не объявлять каждого кота отдельно, поместим их в массив

        for (int i = 0; i < catQuantity; i++)
        {
            catArray[i] = new Cat();                                                           //создадим котов
            System.out.println("Вес кота " + (i+1) + " равен " + catArray[i].getWeight());   // и сразу распечатаем вес животных
        }

        catArray[0].feed(1.0);               //кормим 2х котов для проверки
        catArray[1].feed(1.5);
        System.out.println("Вес первого кота теперь равен " + catArray[0].getWeight());
        System.out.println("Вес второго кота теперь равен " + catArray[1].getWeight());

        while (! catArray[0].getStatus().equals("Exploded"))
        {
            catArray[0].feed(1.0);       //кормим кота пока он не взовётся
        }
        System.out.println("Первый кот взорвался.");

        while (! catArray[1].getStatus().equals("Dead"))
        {
            catArray[1].meow();                  // заставляем мяукать пока не умрёт
        }
        System.out.println("Второй кот умер.");

        catArray[2].drink(2.0);              // поим одного из оставшихся котов, чтобы проверить действие остальных методов

        for (int i = 0; i < catQuantity; i++)
        {
            System.out.println("Текущее состояние кота " + (i+1) + ": " + catArray[i].getStatus());   // проверяем состояние котов после экспериментов
        }

        // Проверяем задание ко второму уроку модуля
        System.out.println("Осталось живых котов: " + Cat.getCount());
        System.out.println("Создадим ещё одного кота");
        Cat tasty = new Cat();
        tasty.feed(150.00);
        tasty.pee();
        tasty.pee();
        tasty.pee();

        System.out.println("Кот съел " + tasty.getEaten());

        //Проверяем задание к третьему уроку

        System.out.println("Осталось живых котов: " + Cat.getCount());
        System.out.println("Создадим ещё одного кота");
        Cat nasty = new Cat();
        System.out.println("Осталось живых котов: " + Cat.getCount());
        catArray[0].pee();

        Cat kitty = getKitten();  // проверяем, удалось ли получить котёнка
        System.out.println("Вес нового кота равен:" + kitty.getWeight());
    }

    private static Cat getKitten()  //создаём класс получить котёнка
    {
        return new Cat(1100.00);
    }
}