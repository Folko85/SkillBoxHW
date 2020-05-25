import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

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

    @Override
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

    }  // получилось конечно длинновато, но можно было сделать и длиннее

    //всего 7 методов в классе  RouteCalculator

    //  public void testGetShortestRoute()               // этот метод последовательно вызывает три других метода,
    //{                                                    // и больше ничего не делает, значит нечего и проверят
    public void testGetRouteOnTheLine() {
        Station from = testStationIndex.getStation("Square");  // проверим маршрут, который возвращает метод getShortestRoute
        Station to = testStationIndex.getStation("Banner");
        List<Station> mustBe = new ArrayList<>();
        mustBe.add(testStationIndex.getStation("Square"));
        mustBe.add(testStationIndex.getStation("Alert"));
        mustBe.add(testStationIndex.getStation("Banner"));
        RouteCalculator testCalculator = new RouteCalculator(testStationIndex);
        List<Station> really = testCalculator.getShortestRoute(from, to);
        assertEquals(mustBe, really);       // метод прекрасно сравнивает даже списки объектов. Like))
    }

    public void testGetRouteWithOneConnection() {                  // сравниваем маршруты с одной пересадкой
        Station from = testStationIndex.getStation("Venus");  // проверим маршрут, который возвращает метод getShortestRoute
        Station to = testStationIndex.getStation("Banner");
        List<Station> mustBe = new ArrayList<>();
        mustBe.add(testStationIndex.getStation("Venus"));
        mustBe.add(testStationIndex.getStation("Earth"));
        mustBe.add(testStationIndex.getStation("Alert"));
        mustBe.add(testStationIndex.getStation("Banner"));
        RouteCalculator testCalculator = new RouteCalculator(testStationIndex);
        List<Station> really = testCalculator.getShortestRoute(from, to);
        assertEquals(mustBe, really);       // метод прекрасно сравнивает даже списки объектов. Like))
    }

 //   public void testIsConnected() {  этот метод косвенно вызывается при проверке маршрута с одной пересадкой
    //}

 //   public void testGetRouteViaConnectedLine() {  этот метод косвенно вызывается при проверке маршрута с двумя пересадками
   // }

    public void testGetRouteWithTwoConnections() {                    // метод с двумя пересадками помогает нам выявить ошибку в коде
        Station from = testStationIndex.getStation("Square");  // а именно, в методе getRouteWithOneConnection
        Station to = testStationIndex.getStation("Wagon");
        List<Station> mustBe = new ArrayList<>();
        mustBe.add(testStationIndex.getStation("Square"));
        mustBe.add(testStationIndex.getStation("Alert"));
        mustBe.add(testStationIndex.getStation("Earth"));
        mustBe.add(testStationIndex.getStation("Jupiter"));
        mustBe.add(testStationIndex.getStation("Uranus"));
        mustBe.add(testStationIndex.getStation("Neptune"));
        mustBe.add(testStationIndex.getStation("Sea"));
        mustBe.add(testStationIndex.getStation("Wagon"));
        RouteCalculator testCalculator = new RouteCalculator(testStationIndex);
        List<Station> really = testCalculator.getShortestRoute(from, to);
        assertEquals(mustBe, really);       // метод прекрасно сравнивает даже списки объектов. Like))
    }

    public void testCalculateDurationOnLine() { // будем вычислять длительность для трёх вариантов построения маршрута
        Station from = testStationIndex.getStation("Square");  // сперва маршрут на линии
        Station to = testStationIndex.getStation("Banner");
        RouteCalculator testCalculator = new RouteCalculator(testStationIndex);
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 5.0;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    public void testCalculateDurationWithOneConnection() { // будем вычислять длительность для трёх вариантов построения маршрута
        Station from = testStationIndex.getStation("Venus");  // маршрут с пересадкой
        Station to = testStationIndex.getStation("Banner");
        RouteCalculator testCalculator = new RouteCalculator(testStationIndex);
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 8.5;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    public void testCalculateDurationWithTwoConnection() { // будем вычислять длительность для трёх вариантов построения маршрута
        Station from = testStationIndex.getStation("Square");  // маршрут с двумя пересадками
        Station to = testStationIndex.getStation("Wagon");
        RouteCalculator testCalculator = new RouteCalculator(testStationIndex);
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 19.5;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    public void testCalculateDurationWithoutStation() {            // ну и обязательно стоит проверить случай совпадения исходной и конечной станций
        Station from = testStationIndex.getStation("Tomato");
        Station to = testStationIndex.getStation("Tomato");
        RouteCalculator testCalculator = new RouteCalculator(testStationIndex);
        List<Station> route = testCalculator.getShortestRoute(from, to);  // мы уже убедились, чтиог методы работают правильно, так что долой ручное заполнение
        double mustBe = 0.0;
        double really = RouteCalculator.calculateDuration(route);
        assertEquals(mustBe, really);
    }

    @Override
    protected void tearDown() throws Exception {

    }
}
