import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;

import java.time.LocalTime;
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        airport.getTerminals().stream()
                .flatMap(terminal -> terminal.getFlights().stream())
                .filter(flight -> flight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().isBefore(LocalTime.now().plusHours(2)))
                .filter(flight -> flight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime().isAfter(LocalTime.now()))
                .filter(flight -> flight.getType() == Flight.Type.DEPARTURE) // нам нужны только вылеты
                .forEach(System.out::println);

    }
}
