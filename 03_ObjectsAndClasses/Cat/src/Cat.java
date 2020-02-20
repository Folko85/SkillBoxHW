
public class Cat
{
    public static final int EYES_COUNT = 2;          //создаём константы по заданию
    public static final double MIN_WEIGHT =1000.0;
    public static final double MAX_WEIGHT =9000.0;
    private double originWeight;
    private double weight;

    private double minWeight;
    private double maxWeight;
    private double feedWeight;    //чтобы отслеживать изменения веса кота
    public static int count;       // количество котов

    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        minWeight = MIN_WEIGHT;        // здесь им самое место.
        maxWeight = MAX_WEIGHT;
        feedWeight = 0.0; //новосозданный кот пока не успел покушать
        count++;
    }

    public void meow()
    {
        if (weight <= minWeight || weight >= maxWeight)
            {
                System.out.println("Кот мёртв, поэтому не может мяукать");
            }
        else
            {
                weight = weight - 1;
                if (weight <= minWeight) {
                count--;}
                System.out.println("Meow");
            }
    }

    public void feed(Double amount)
    {
        if (weight <= minWeight || weight >= maxWeight)
            {
                System.out.println("Кот мёртв, поэтому вы не можете его покормить");
            }
        else
            {
                weight = weight + amount;
                if (weight >= maxWeight) {
                count--;}
                feedWeight = feedWeight + amount;        // фиксируем каждое кормление
            }
    }

    public void drink(Double amount)
    {
        if (weight <= minWeight || weight >= maxWeight)
            {
                System.out.println("Кот мёртв, поэтому вы не можете его напоить");
            }
        else
            {
                weight = weight + amount;
                if (weight >= maxWeight) {
                count--;}
            }
    }

    public Double getWeight()
    {
        return weight;
    }

    public String getStatus()
    {
        if(weight < minWeight) {
            return "Dead";
        }
        else if(weight > maxWeight) {
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
        if (weight <= minWeight || weight >= maxWeight)
            {
                System.out.println("Кот мёртв, поэтому не может сходить в туалет");
            }
        else
            {
                weight = weight - 2;
                if (weight <= minWeight) {
                count--;}
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
}
