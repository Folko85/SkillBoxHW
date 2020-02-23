
public class Cat
{
    public static final int EYES_COUNT = 2;          //создаём константы по заданию
    public static final double MIN_WEIGHT =1000.0;
    public static final double MAX_WEIGHT =9000.0;
    private double originWeight;
    private double weight;
    private double feedWeight;    //чтобы отслеживать изменения веса кота
    public static int count;       // количество котов
    private CatColor catColor;     // здесь мы будем хранить окрас котов
    private boolean alive;                //жить или не жить


    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        feedWeight = 0.0;      //новосозданный кот пока не успел покушать
        count++;
        catColor = CatColor.getRandomColor();  // получаем кота случайного цвета
        alive = true;
    }

    public Cat(double weight)                   //второй конструктор
    {
        this();
        this.weight = weight;
        originWeight = weight;
    }

    // 7й урок. Создание копии объекта
    public Cat (Cat original)                //конструктор создания копии
    {
        this();                                  // на будущее
        this.weight = original.weight;
        this.originWeight = original.weight;     //предположи, что кот успел поесть перед копированием
        this.catColor = original.catColor;
        this.alive = original.isAlive();          // если кот мёртв, то и его копия будет мертва
    }

    public void meow()
    {
        if (! this.isAlive())
            {
                System.out.println("Кот мёртв, поэтому не может мяукать");
            }
        else
            {
                weight = weight - 1;
                if (weight <= MIN_WEIGHT)
                {
                    count--;
                    alive = false;    // избыточно конечно, но пусть будет
                }
                System.out.println("Meow");
            }
    }

    public void feed(Double amount)
    {
        if (! this.isAlive())
            {
                System.out.println("Кот мёртв, поэтому вы не можете его покормить");
            }
        else
            {
                weight = weight + amount;
                if (weight >= MAX_WEIGHT)
                {
                    count--;
                    alive = false;
                }
                feedWeight = feedWeight + amount;        // фиксируем каждое кормление
            }
    }

    public void drink(Double amount)
    {
        if (! this.isAlive())
            {
                System.out.println("Кот мёртв, поэтому вы не можете его напоить");
            }
        else
            {
                weight = weight + amount;
                if (weight >= MAX_WEIGHT)
                {
                    count--;
                    alive = false;
                }
            }
    }

    public Double getWeight()
    {
        return weight;
    }

    public String getStatus()
    {
        if(weight < MIN_WEIGHT) {
            return "Dead";
        }
        else if(weight > MAX_WEIGHT) {
            return "Exploded";
        }
        else if(weight > originWeight) {
            return "Sleeping";
        }
        else {
            return "Playing";
        }
    }

    public void pee()               // метод сходить в туалет
    {
        if (! this.isAlive())
            {
                System.out.println("Кот мёртв, поэтому не может сходить в туалет");
            }
        else
            {
                weight = weight - 2;
                if (weight <= MIN_WEIGHT)
                {
                count--;
                alive = false;
                }
                System.out.println("Oops!!!");
            }
    }

    public Double getEaten()   //метод съедено
    {
        return feedWeight;
    }

    public static int getCount()   // возвращаем количество котов
    {
        return count;
    }

    public CatColor getCatColor()                //геттер окраса кота
    {
        return catColor;
    }

    public void setCatColor(CatColor color)    //сеттер окраса кота
    {
        this.catColor = color;
    }

    public boolean isAlive()                       // метод проверяет, жив ли кот
    {
        if (weight <= MIN_WEIGHT || weight >= MAX_WEIGHT)
        {
            return false;
        }
        else return true;
    }
}
