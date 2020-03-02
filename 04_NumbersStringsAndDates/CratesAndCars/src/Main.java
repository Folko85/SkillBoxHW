/*
В духе парадигмы ООП сделаем ящики, контейнеры и грузовики объектами
 */

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //Можно было организовать ввод вместимости, но ограничимся тем, что можно задать их в коде
        Container.setTonnageContainer(27);
        Cargo.setTonnageCargo(12);
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

                int containerInput = (int) Math.ceil((double) cratesInput / Container.getMaxTonnageContainer()); // то это количество контейнеров
                Container[] arrayContainers = new Container[containerInput];
                System.out.println("Для этого понадобится контейнеров: " + containerInput);

                for (int i = 0; i < containerInput; i++) {
                    arrayContainers[i] = new Container(i);
                    arrayContainers[i].addCratesToContainer(arrayCrates);
                }

                int cargoInput = (int) Math.ceil((double)containerInput / Cargo.getTonnageCargo());
                Cargo[] arrayCargo = new Cargo[cargoInput];                                                  //создаём требуемое количество машин
                System.out.println("Для этого понадобится грузовиков: " + cargoInput);
                for (int i = 0; i < cargoInput; i++) {
                    arrayCargo[i] = new Cargo(i);
                    arrayCargo[i].addContainerToCargo(arrayContainers);
                    System.out.println(arrayCargo[i]);
                }
             }
        } else {
            System.out.println("Извините, но это явно не число. Перезапустите программу и попробуйте снова!");
        }
    }
}
