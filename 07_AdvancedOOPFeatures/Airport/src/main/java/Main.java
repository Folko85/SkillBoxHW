import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        List<Terminal> terminals = airport.getTerminals();
        ArrayList<Flight> flights = new ArrayList<>();
        terminals.forEach(terminal -> flights.addAll(terminal.getFlights())); // долой циклы, минимализм наше всё

// время вылета генерируется с небольшим разбросом как в прошлое, так и в будущее, чтоб не путаться, просто фильтруем дважды
        // в такие моменты жалеешь, что в Java нет макросов.
        flights.stream().filter(flight -> flight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().isBefore(LocalTime.now().plusHours(2)))
                .filter(flight -> flight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().isAfter(LocalTime.now()))
                .filter(flight -> flight.getType() == Flight.Type.DEPARTURE) // нам нужны только вылеты
                .forEach(System.out::println);

    }
}
