import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Airport airport = Airport.getInstance();              // создаём случайный аэропорт
        List<Aircraft> listOfAircraft = airport.getAllAircrafts(); // получаем список созданных при инициализации самолётов
        int aircraftCount = listOfAircraft.size();                  //получаем количество самолётов
        for(int i = 0; i < listOfAircraft.size(); i++)            //выведем модели самолётов
        {
            System.out.println("Модель самолёта " + (i + 1) + ": " + listOfAircraft.get(i).getModel());
        }
        System.out.println("Количество самолётов в аэропорту равно " + aircraftCount);    //этим пока и ограничимся
    }
}
