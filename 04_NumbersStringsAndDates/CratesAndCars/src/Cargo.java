public class Cargo
{
    private int cargoNumber;
    private static int tonnageCargo = 12; //вместимость статична. Чтобы не создавать объект для её изменения
    private Container[] containerCount ;  // массив контейнеров в грузовике

    public Cargo(int number)
    {
        this.cargoNumber = number + 1;
        this.containerCount = new Container[tonnageCargo];  // каждому грузовику соответствует массив контейнеров
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Грузовик " + this.cargoNumber + "\n");

        for (int i = 0; i < tonnageCargo; i++) {
            if (containerCount[i] != null) {
                builder.append("\t" + this.containerCount[i]);  // здесь выходил бы двойной перевод строки, что некрасиво
            }
        }

        return builder.toString();
    }

    public static void setTonnageCargo(int maxTonnage)       //меняем вместимость грузовика
    {
        tonnageCargo = maxTonnage;
    }

    public static int getTonnageCargo()            // узнаём вместимость грузовика
    {
        return tonnageCargo;
    }

    public void addContainerToCargo(Container[] allContainers)
    {
        for (int i = 0; i < allContainers.length; i++)
        {
            if (i < (this.cargoNumber * tonnageCargo) && i >= ((this.cargoNumber - 1) * tonnageCargo) ) {
                this.containerCount[i - ((this.cargoNumber - 1) * tonnageCargo)] = allContainers[i];
            }
        }
    }
}
