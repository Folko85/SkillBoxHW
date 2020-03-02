//Контейнеры отличаются от ящиков тем, что могут содержать в себе массивы с ящиками
//А ещё у них есть свойство (переменная) вместимость, которую тоже можно менять

public class Container
{
    private int containerNumber;
    private static int tonnageContainer = 27; //максимальная вместимость контейнера, общий параметр для класса
    private Crate[] cratesCount ;          // массив ящиков, находящихся в контейнере


    public Container(int number)
    {
        this.containerNumber = number + 1;
        this.cratesCount = new Crate[tonnageContainer];  // каждому контейнеру соответствует массив ящиков
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Контейнер " + this.containerNumber + "\n");

        for (int i = 0; i < tonnageContainer; i++)
        {
            if(this.cratesCount[i] != null) {
                builder.append("\t\t" + this.cratesCount[i] + "\n");
            }
        }

        return builder.toString();
    }

    public static void setTonnageContainer(int maxTonnage)    // метод, позволяющий менять вместимость контейнеров
    {
        tonnageContainer = maxTonnage;
    }

    public static int getMaxTonnageContainer()              //узнаём вместимость
    {
        return  tonnageContainer;
    }

    public void addCratesToContainer(Crate[] allCrates)       //добавляем ящики в определённый контейнер
    {
        for (int i = 0; i < allCrates.length; i++)
            {
                if (i < (this.containerNumber * tonnageContainer) && i >= ((this.containerNumber - 1) * tonnageContainer) )
                    this.cratesCount[i -((this.containerNumber - 1) * tonnageContainer) ] = allCrates[i];
            }
            //выделяем для контейнера ящики согласно вместимости и номера
    }
}
