/*
В духе парадигмы ООП сделаем ящики, контейнеры и грузовики объектами
 */

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //Можно было организовать ввод вместимости, но ограничимся тем, что можно задать их в коде
        Container.setMaxTonnageContainer(27);
        Cargo.setMaxTonnageCargo(12);
        System.out.println("Введите количество ящиков:");
        Scanner cratesCount = new Scanner(System.in);
        int cratesInput;
        if (cratesCount.hasNextInt()) {
            cratesInput = cratesCount.nextInt();
            if(cratesInput <= 0)
            {
                System.out.println("Количество ящиков не может быть нулевым или отрицательным.");
            }
            else {
                System.out.println("Нужно перевезти ящиков: " + cratesInput);
                Crate[] arrayCrates = new Crate[cratesInput];
                for (int i = 0; i < cratesInput; i++)         //создаём ящики, согласно введённому
                {
                    arrayCrates[i] = new Crate(i);
                }
                int containerFull = cratesInput / Container.getMaxTonnageContainer();        // вычисляем количество полных контейнеров
                int containerSemiFull = 0;                                  // если все контейнеры полны
                int containerInput = containerFull;                         // то это количество контейнеров
                if ((cratesInput % Container.getMaxTonnageContainer()) != 0) {
                    containerSemiFull = cratesInput % Container.getMaxTonnageContainer();   // один котейнер возможно будет неполным
                    containerInput++;                                                 //прибавляем его к остальным
                }
                Container[] arrayContainers = new Container[containerInput];
                System.out.println("Для этого понадобится контейнеров: " + containerInput);
                for (int i = 0; i < containerFull; i++) {
                    arrayContainers[i] = new Container(i);
                }
                if (containerSemiFull != 0) {
                    arrayContainers[containerFull] = new Container(containerFull, containerSemiFull);
                }

                int cargoFull = containerInput / Cargo.getMaxTonnageCargo();
                int cargoSemiFull = 0;
                int cargoInput = cargoFull;
                if ((containerInput % Cargo.getMaxTonnageCargo()) != 0) {
                    cargoSemiFull = containerInput % Cargo.getMaxTonnageCargo();   // один грузовик возможно будет неполным
                    cargoInput++;                                                 //прибавляем его к остальным
                }
                Cargo[] arrayCargo = new Cargo[cargoInput];           //создаём требуемое количество машин
                System.out.println("Для этого понадобится грузовиков: " + cargoInput);
                for (int i = 0; i < cargoFull; i++) {
                    arrayCargo[i] = new Cargo(i);
                }
                if (cargoSemiFull != 0) {
                    arrayCargo[cargoFull] = new Cargo(cargoFull, cargoSemiFull);
                }

                for (int i = 0; i < (cargoInput); i++) {
                    System.out.println(arrayCargo[i].getCargoName());
                    arrayCargo[i].addContainerToCargo(arrayContainers, arrayCrates);
                }
            }
        } else {
            System.out.println("Извините, но это явно не число. Перезапустите программу и попробуйте снова!");
        }
    }
}
