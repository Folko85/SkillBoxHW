
public class Cat
{
    private double originWeight;
    private double weight;

    private double minWeight;
    private double maxWeight;
    private double feedWeight;    //чтобы отслеживать изменения веса кота

    public Cat()
    {
        weight = 1500.0 + 3000.0 * Math.random();
        originWeight = weight;
        minWeight = 1000.0;
        maxWeight = 9000.0;
        feedWeight = 0.0; //новосозданный кот пока не успел покушать

    }

    public void meow()
    {
        weight = weight - 1;
        System.out.println("Meow");
    }

    public void feed(Double amount)
    {
        weight = weight + amount;
        feedWeight = feedWeight + amount;        // фиксируем каждое кормление
    }

    public void drink(Double amount)
    {
        weight = weight + amount;
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
        weight = weight - 2;
        System.out.println("Oops!!!");
    }

    public Double getEaten()   //метод съедено
    {
        return feedWeight;
    }


}
