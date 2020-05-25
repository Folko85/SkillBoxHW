import core.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RouteCalculator {
    private StationIndex stationIndex;      // вычислитель маршрута чётко привязан к карте метро

    private static double interStationDuration = 2.5;        // время перехода между станциями
    private static double interConnectionDuration = 3.5;     // время перехода между линиями

    public RouteCalculator(StationIndex stationIndex) {
        this.stationIndex = stationIndex;
    }

    public List<Station> getShortestRoute(Station from, Station to)      // определение кратчайшего маршрута
    {
        List<Station> route = getRouteOnTheLine(from, to);          // если маршрут проходит по одной линии
        if (route != null) {
            return route;
        }

        route = getRouteWithOneConnection(from, to);           // если маршрут с одном переходом
        if (route != null) {
            return route;
        }

        route = getRouteWithTwoConnections(from, to);         // если маршрут с двумя переходами
        return route;
    }

    public static double calculateDuration(List<Station> route)     // вычисление длительности
    {
        double duration = 0;
        Station previousStation = null;
        for (int i = 0; i < route.size(); i++) {
            Station station = route.get(i);
            if (i > 0)             //заглушка, чтобы подсчёт вёлся, начиная со второй станции, ведь на первом проходе, предыдущей станции не существует
            {
                duration += previousStation.getLine().equals(station.getLine()) ?  // тернарный оператор, ну надо же
                        interStationDuration : interConnectionDuration;
            }
            previousStation = station;
        }
        return duration;
    }

    //=========================================================================

    private List<Station> getRouteOnTheLine(Station from, Station to)     // получение маршрута станций с одной линии
    {
        if (!from.getLine().equals(to.getLine())) {
            return null;               // если линии не совпадают, то возвращаемся
        }
        ArrayList<Station> route = new ArrayList<>();
        List<Station> stations = from.getLine().getStations();   // получаем все станции, лежащие на одной линии с исходной
        int direction = 0;                             //      определяем направление движения
        for (Station station : stations)          // проходимся проследовательно по всем станциям
        {
            if (direction == 0) {
                if (station.equals(from)) {
                    direction = 1;
                } else if (station.equals(to)) {
                    direction = -1;
                }
            }

            if (direction != 0) {        // как только фиксируем одну из станций, начинаем записывать маршрут
                route.add(station);
            }

            if ((direction == 1 && station.equals(to)) ||     // как только дошли до второй - выходим из цикла
                    (direction == -1 && station.equals(from))) {
                break;
            }
        }
        if (direction == -1) {     // если первой попалась станция назначения, то переворачиваем список
            Collections.reverse(route);
        }
        return route;
    }

    private List<Station> getRouteWithOneConnection(Station from, Station to) {
        // if(from.getLine().equals(to.getLine())) {              // найденная ошибка, мы должны вернуть null не когда станции на одной линии
        //     return null;                                   // а когда у линий, на которых лежат станции нет пересечения
        // }
        ArrayList<Station> route = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for (Station srcStation : fromLineStations)         // для всех станций с линии отправления
        {
            for (Station dstStation : toLineStations)       // и для всех станций с линии назначения
            {
                if (isConnected(srcStation, dstStation))       // если между станциями есть переход
                {
                    ArrayList<Station> way = new ArrayList<>();
                    way.addAll(getRouteOnTheLine(from, srcStation));  // то считаем маршрут от исходной линиии до перехода
                    way.addAll(getRouteOnTheLine(dstStation, to));    // и от перехода до конечной линии
                    if (route.isEmpty() || route.size() > way.size())       // на случай, если таких маршрутов несколько
                    {
                        route.clear();                   // выбираем кратчайший
                        route.addAll(way);
                    }
                }
            }
        }
        if (route.isEmpty()) {
            return null;       // если маршрут не найден, то возвращаем null и переходим к следующему методу
        }
        return route;
    }

    private boolean isConnected(Station station1, Station station2) {
        Set<Station> connected = stationIndex.getConnectedStations(station1);   // метод проверяем, есть ли переход между станциями
        return connected.contains(station2);
    }

    private List<Station> getRouteViaConnectedLine(Station from, Station to)  // есть ли переход на одну и ту же линию
    {
        Set<Station> fromConnected = stationIndex.getConnectedStations(from);// порлучаем станции на которые есть переход с исходной      //
        Set<Station> toConnected = stationIndex.getConnectedStations(to);    // станции, на которые есть переход сч конечной
        for (Station srcStation : fromConnected)   // для всех станций из первого множества
        {
            for (Station dstStation : toConnected)   // и всех станций из второго множества
            {
                if (srcStation.getLine().equals(dstStation.getLine())) {       // ищем общую линию
                    return getRouteOnTheLine(srcStation, dstStation);   // и возвращаем станции, по которым можно на неё перейти
                }
            }
        }
        return null;
    }

    private List<Station> getRouteWithTwoConnections(Station from, Station to)  // для случая с двумя переходами
    {
        if (from.getLine().equals(to.getLine())) {
            return null;                           // код опять же никогда не выполнится, но если уж мы возвращаем null, то нужно вернуть его для случая более двух переходов
        }                                           // который для петербургского метро не существует, хз, ошибка ли это

        ArrayList<Station> route = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for (Station srcStation : fromLineStations)      // для всех станций с исходной линии
        {
            for (Station dstStation : toLineStations)       // и для всех станций с конечной линии
            {
                List<Station> connectedLineRoute =
                        getRouteViaConnectedLine(srcStation, dstStation); // проверяем, есть ли переход на одну и ту же линию
                if (connectedLineRoute == null) {
                    continue;        // если общей линии нет, то следует вернуть null
                }
                ArrayList<Station> way = new ArrayList<>();
                way.addAll(getRouteOnTheLine(from, srcStation));
                way.addAll(connectedLineRoute);
                way.addAll(getRouteOnTheLine(dstStation, to));
                if (route.isEmpty() || route.size() > way.size())     // на случай нескольких общих линий
                {
                    route.clear();
                    route.addAll(way);
                }
            }
        }

        return route;
    }
}