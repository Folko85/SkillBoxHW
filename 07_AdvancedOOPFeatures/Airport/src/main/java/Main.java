import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        airport.getTerminals().stream()
                .flatMap(terminal -> terminal.getFlights().stream())
                .filter(flight -> toLocalTime(flight.getDate()).isBefore(LocalTime.now().plusHours(2)))
                .filter(flight -> toLocalTime(flight.getDate()).isAfter(LocalTime.now()))
                .filter(flight -> flight.getType() == Flight.Type.DEPARTURE) // нам нужны только вылеты
                .forEach(System.out::println);

    }

    private static LocalTime toLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }
}
