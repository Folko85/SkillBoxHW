import core.Line;
import core.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RouteCalculatorTest {

    /*          Следуя заветам чатика рисуем свою карту метро, которую и будем тестить, добавлять будем вручную.
     *                      ибо быстрее выйдет, чем переписывать из мейна весь парсинг.
     *
     *                                                Line Three(Green of course:)
     *
     *                                                             Venus
     *                                                               |
     *   Line One(Red)   -- Blood -- Ruby -- Tomato -- Square -- Alert/Earth -- Banner
     *                                                               |
     *                                                            Jupiter
     *                                                               |
     *                                                            Uranus
     *                                                               |
     * Line Two (Blue)       -- Sky -- Puppy -- Eyes -- Wagon  -- Sea/ Neptune -- Topaz
     *                                                               |
     *                                                             Pluto
     */

    StationIndex testStationIndex = new StationIndex();
    RouteCalculator testCalculator;

    @Before
    public void setUp() throws Exception {
        // создаём пустую карту
        testStationIndex.addLine(new Line(1, "Red"));   // добавляем туда вновь созданные линии
        testStationIndex.addLine(new Line(2, "Blue"));
        testStationIndex.addLine(new Line(3, "Green"));

        String[][] stations = new String[][]{{"Blood", "Ruby", "Tomato", "Square", "Alert", "Banner"},
                {"Sky", "Puppy", "Eyes", "Wagon", "Sea", "Topaz"},
                {"Venus", "Earth", "Jupiter", "Uranus", "Neptune", "Pluto"}};// зададим названия станций двумерным массивом
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 6; column++) {        // добавляем на карту вновь созданные станции
                testStationIndex.addStation(new Station(stations[row][column], testStationIndex.getLine(row + 1)));
                testStationIndex.getLine(row + 1).addStation(testStationIndex.getStation(stations[row][column]));
            }                                                   // те же станции добавляем линиям
        }
        List<Station> connectionOne = new ArrayList<>();        // первый переход
        connectionOne.add(testStationIndex.getStation("Alert"));
        connectionOne.add(testStationIndex.getStation("Earth"));
        List<Station> connectionTwo = new ArrayList<>();          // второй переход
        connectionTwo.add(testStationIndex.getStation("Sea"));
        connectionTwo.add(testStationIndex.getStation("Neptune"));

        testStationIndex.addConnection(connectionOne);      // добавляем переходы
        testStationIndex.addConnection(connectionTwo);

        testCalculator = new RouteCalculator(testStationIndex);

    }  // получилось конечно длинновато, но можно было сделать и длиннее

    @Test
    public void testGetRouteOnTheLine() {
        Station from = testStationIndex.getStation("Square");  // проверим маршрут, который возвращает метод getShortestRoute
        Station to = testStationIndex.getStation("Banner");
        List<Station> mustBe = buildRoute("Square->Alert->Banner");
        List<Station> really = testCalculator.getShortestRoute(from, to);
        assertEquals(mustBe, really);       // метод прекрасно сравнивает даже списки объектов. Like))
    }

    @Test
    public void testGetRouteWithOneConnection() {                  // сравниваем маршруты с одной пересадкой
        Station from = testStationIndex.getStation("Venus");  // проверим маршрут, который возвращает метод getShortestRoute
        Station to = testStationIndex.getStation("Banner");
        List<Station> mustBe = buildRoute("Venus->Earth->Alert->Banner");
        List<Station> really = testCalculator.getShortestRoute(from, to);
        assertEquals(mustBe, really);       // метод прекрасно сравнивает даже списки объектов. Like))
    }

    @Test
    public void testGetRouteWithTwoConnections() {                    // метод с двумя пересадками помогает нам выявить ошибку в коде
        Station from = testStationIndex.getStation("Square");  // а именно, в методе getRouteWithOneConnection
        Station to = testStationIndex.getStation("Wagon");
        List<Station> mustBe = buildRoute("Square->Alert->Earth->Jupiter->Uranus->Neptune->Sea->Wagon");
        List<Station> really = testCalculator.getShortestRoute(from, to);
        assertEquals(mustBe, really);       // метод прекрасно сравнивает даже списки объектов. Like))
    }

    @Test
    public void testCalculateDurationOnLine() { // будем вычислять длительность для трёх вариантов построения маршрута
        Station from = testStationIndex.getStation("Square");  // сперва маршрут на линии
        Station to = testStationIndex.getStation("Banner");
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 5.0;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    @Test
    public void testCalculateDurationWithOneConnection() { // будем вычислять длительность для трёх вариантов построения маршрута
        Station from = testStationIndex.getStation("Venus");  // маршрут с пересадкой
        Station to = testStationIndex.getStation("Banner");
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 8.5;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    @Test
    public void testCalculateDurationWithTwoConnection() { // будем вычислять длительность для трёх вариантов построения маршрута
        Station from = testStationIndex.getStation("Square");  // маршрут с двумя пересадками
        Station to = testStationIndex.getStation("Wagon");
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 19.5;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    @Test
    public void testCalculateDurationWithoutStation() {            // ну и обязательно стоит проверить случай совпадения исходной и конечной станций
        Station from = testStationIndex.getStation("Tomato");
        Station to = testStationIndex.getStation("Tomato");
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 0.0;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    private List<Station> buildRoute(String route) {
        String[] stations = route.split("->");
        List<Station> routeStation = new ArrayList();
        for (String station : stations) {
            routeStation.add(testStationIndex.getStation(station));
        }
        return routeStation;
    }

    @After
    public void tearDown() throws Exception {
        testStationIndex = null;
        testCalculator = null;
        System.gc();               //хз, насколько это правильно и нужно, но не оставлять же метод пустым
    }
}
