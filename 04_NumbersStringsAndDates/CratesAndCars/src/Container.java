//Контейнеры отличаются от ящиков тем, что могут содержать в себе массивы с ящиками
//А ещё у них есть свойство (переменная) вместимость, которую тоже можно менять

public class Container
{
    private int containerNumber;
    private String containerName;
    private static int maxTonnageContainer = 27; //максимальная вместимость контейнера, общий параметр для класса
    private int tonnageContainer;
    private Crate[] cratesCount ;          // массив ящиков, находящихся в контейнере


    public Container(int number)
    {
        this.containerNumber = number;
        this.containerName = "Контейнер " + (number + 1);
        this.tonnageContainer = maxTonnageContainer;
        this.cratesCount = new Crate[tonnageContainer];  // каждому контейнеру соответствует массив ящиков
    }

    public Container(int number, int tonnage)           //конструктор для неполных контейнеров
    {
        this(number);
        this.tonnageContainer = tonnage;
    }

    public String getContainerName()
    {
        return this.containerName;
    }

    public static void setMaxTonnageContainer(int maxTonnage)    // метод, позволяющий менять вместимость контейнеров
    {
        maxTonnageContainer = maxTonnage;
    }

    public static int getMaxTonnageContainer()              //узнаём вместимость
    {
        return  maxTonnageContainer;
    }

    public void addCratesToContainer(Crate[] allCrates)       //добавляем ящики в определённый контейнер
    {
        for (int i = 0; i < tonnageContainer; i++)
        {
            this.cratesCount[i] = allCrates[(this.containerNumber * maxTonnageContainer) + i];
            System.out.println("\t\t" + this.cratesCount[i].getCrateName());
        }    //выделяем для контейнера ящики согласно вместимости и номера
    }
}
