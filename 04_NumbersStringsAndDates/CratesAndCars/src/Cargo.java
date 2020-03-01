public class Cargo
{
    private int cargoNumber;
    private String cargoName;
    private static int maxTonnageCargo = 12; //вместимость статична. Чтобы не создавать объект для её изменения
    private int tonnageCargo;
    private Container[] containerCount ;  // массив контейнеров в грузовике

    public Cargo(int number)
    {
        this.cargoNumber = number;
        this.cargoName = "Грузовик " + (number + 1);
        this.tonnageCargo = maxTonnageCargo;
        this.containerCount = new Container[tonnageCargo];  // каждому контейнеру соответствует массив ящиков
    }

    public Cargo(int number, int tonnage)         // метод для неполных грузовиков
    {
        this(number);
        this.tonnageCargo = tonnage;
    }

    public String getCargoName()
    {
        return this.cargoName;
    }

    public static void setMaxTonnageCargo(int maxTonnage)
    {
        maxTonnageCargo = maxTonnage;
    }

    public static int getMaxTonnageCargo()
    {
        return maxTonnageCargo;
    }

    public void addContainerToCargo(Container[] allContainers, Crate[] allCrates)
    {
        for (int i = 0; i < tonnageCargo; i++)
        {
            this.containerCount[i] = allContainers[(this.cargoNumber * maxTonnageCargo) + i];
            System.out.println("\t" + this.containerCount[i].getContainerName());
            this.containerCount[i].addCratesToContainer(allCrates);         // для каждого контейнера выводим ящики
        }
    }
}
