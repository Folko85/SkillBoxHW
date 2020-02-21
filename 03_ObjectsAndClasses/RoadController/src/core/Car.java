package core;

public class Car
{
    public String number;
    public int height;
    public double weight;
    public boolean hasVehicle;
    public boolean isSpecial;

    public String toString()
    {
        String special = isSpecial ? "СПЕЦТРАНСПОРТ " : "";
        return "\n=========================================\n" +
            special + "Автомобиль с номером " + number +
            ":\n\tВысота: " + height + " мм\n\tМасса: " + weight + " кг";
    }

    public String getNumber()
    {
        return number;
    }

    public int getHeight()
    {
        return height;
    }

    public double getWeight()
    {
        return weight;
    }

    public boolean getHasVehicle()
    {
        return hasVehicle;
    }

    public boolean getIsSpecial()
    {
        return isSpecial;
    }

    public void  setNumber (String carNumber)
    {
        this.number = carNumber;
    }

    public void setHeight (int carHeight)
    {
        this.height = carHeight;
    }

    public void setWeight (int carWeight)
    {
        this.weight = carWeight;
    }

    public void setHasVehicle(boolean vehicle)
    {
        this.hasVehicle = vehicle;
    }

    public void setIsSpecial (boolean special)
    {
        this.isSpecial = special;
    }
}