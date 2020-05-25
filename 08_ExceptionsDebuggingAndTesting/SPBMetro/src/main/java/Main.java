import core.Line;
import core.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static String dataFile = "src/main/resources/map.json";   //путь до файла со станциями
    private static Scanner scanner;

    private static StationIndex stationIndex;        // карта станций

    public static void main(String[] args)
    {
        RouteCalculator calculator = getRouteCalculator();        // объект, рассчитывающий маршрут

        System.out.println("Программа расчёта маршрутов метрополитена Санкт-Петербурга\n");
        scanner = new Scanner(System.in);
        for(;;)
        {
            Station from = takeStation("Введите станцию отправления:");
            Station to = takeStation("Введите станцию назначения:");

            List<Station> route = calculator.getShortestRoute(from, to);         // записываем в список кратчайший маршрут
            System.out.println("Маршрут:");
            printRoute(route);

            System.out.println("Длительность: " +
                RouteCalculator.calculateDuration(route) + " минут");           // высчитываем его длительность
        }
    }

    private static RouteCalculator getRouteCalculator()
    {
        createStationIndex();                             //рассчётчик маршрутов создаётся на основе карты маршрутов
        return new RouteCalculator(stationIndex);
    }

    private static void printRoute(List<Station> route)
    {
        Station previousStation = null;                          //1. предыдущей станции нет
        for(Station station : route)
        {
            if(previousStation != null)
            {
                Line prevLine = previousStation.getLine();
                Line nextLine = station.getLine();
                if(!prevLine.equals(nextLine))                   //4.если линия предыдущей не совпадает с линией текущей
                {
                    System.out.println("\tПереход на станцию " +            //5. то фиксируем переход на другую линию
                        station.getName() + " (" + nextLine.getName() + " линия)");
                }
            }
            System.out.println("\t" + station.getName());       //2. распечатываем текущую станцию
            previousStation = station;                         //3. присваиваем ссылку на неё предыдущей.
        }
    }

    private static Station takeStation(String message)
    {
        for(;;)
        {
            System.out.println(message);               // вводим станцию с клавиатуры и проверяем, есть ли на карте станций
            String line = scanner.nextLine().trim();
            Station station = stationIndex.getStation(line);
            if(station != null) {
                return station;
            }
            System.out.println("Станция не найдена :(");
        }
    }

    private static void createStationIndex()
    {
        stationIndex = new StationIndex();       // парсим файл и записываем станции в объект StationIndex
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLines(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStations(stationsObject);

            JSONArray connectionsArray = (JSONArray) jsonData.get("connections");
            parseConnections(connectionsArray);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parseConnections(JSONArray connectionsArray)       // парсим переходы
    {
        connectionsArray.forEach(connectionObject ->
        {
            JSONArray connection = (JSONArray) connectionObject;
            List<Station> connectionStations = new ArrayList<>();
            connection.forEach(item ->
            {
                JSONObject itemObject = (JSONObject) item;
                int lineNumber = ((Long) itemObject.get("line")).intValue();
                String stationName = (String) itemObject.get("station");

                Station station = stationIndex.getStation(stationName, lineNumber);
                if(station == null)
                {
                    throw new IllegalArgumentException("core.Station " +
                        stationName + " on line " + lineNumber + " not found");
                }
                connectionStations.add(station);
            });
            stationIndex.addConnection(connectionStations); // 4. Добавляем переходы
        });
    }

    private static void parseStations(JSONObject stationsObject)   // парсим станции
    {
        stationsObject.keySet().forEach(lineNumberObject ->
        {
            int lineNumber = Integer.parseInt((String) lineNumberObject);
            Line line = stationIndex.getLine(lineNumber);
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumberObject);
            stationsArray.forEach(stationObject ->
            {
                Station station = new Station((String) stationObject, line);
                stationIndex.addStation(station);         // 2.добавляем станции на карту
                line.addStation(station);                // 3. добавлем  станции на линию
            });
        });
    }

    private static void parseLines(JSONArray linesArray)    //парсим линии
    {
        linesArray.forEach(lineObject -> {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    ((Long) lineJsonObject.get("number")).intValue(),
                    (String) lineJsonObject.get("name")
            );
            stationIndex.addLine(line);              //1. Добавляем линии на карту
        });
    }

    private static String getJsonFile()     // читаем json и записываем его в список
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}