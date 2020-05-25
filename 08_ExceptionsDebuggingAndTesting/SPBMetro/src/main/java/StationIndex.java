import core.Line;
import core.Station;

import java.util.*;
import java.util.stream.Collectors;

public class StationIndex
{
    HashMap<Integer, Line> number2line;
    TreeSet<Station> stations;
    TreeMap<Station, TreeSet<Station>> connections;

    public StationIndex()
    {
        number2line = new HashMap<>();  // у каждой карты станций есть карта соответствий номеров линиям
        stations = new TreeSet<>();           // множество станций
        connections = new TreeMap<>();       // и карта переходов между линиями
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public void addLine(Line line)
    {
        number2line.put(line.getNumber(), line);
    }

    public void addConnection(List<Station> stations)   // порядок добавления перехода
    {
        for(Station station : stations)          // для каждой станции
        {
            if(!connections.containsKey(station)) {         // создаём карту, где она является ключом
                connections.put(station, new TreeSet<>());   // и ей соответствует пустое значение множества
            }
            TreeSet<Station> connectedStations = connections.get(station);// получаем это пустое множество из карты
            connectedStations.addAll(stations.stream()                          // и добавляем туда все переданные значения
                .filter(s -> !s.equals(station)).collect(Collectors.toList()));   // кроме ключа.
        }
    }

    public Line getLine(int number)
    {
        return number2line.get(number);
    }

    public Station getStation(String name)
    {
        for(Station station : stations)
        {
            if(station.getName().equalsIgnoreCase(name)) {
                return station;
            }
        }
        return null;
    }

    public Station getStation(String name, int lineNumber)
    {
        Station query = new Station(name, getLine(lineNumber));
        Station station = stations.ceiling(query);
        return station.equals(query) ? station : null;
    }

    public Set<Station> getConnectedStations(Station station)
    {
        if(connections.containsKey(station)) {
            return connections.get(station);
        }
        return new TreeSet<>();
    }
}
