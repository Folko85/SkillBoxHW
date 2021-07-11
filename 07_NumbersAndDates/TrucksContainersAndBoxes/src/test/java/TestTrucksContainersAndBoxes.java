import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test for module 4 homework 3 task 2 trucks, containers and boxes.
 * Тест рассчитан для конфигурации, когда в 1 контейнер помещается
 * 27 ящиков и в 1 грузовик помещается 12 контейнеров
 */

@DisplayName("Вывод количества грузовиков и контейнеров для перевозки указанного в консоли количества ящиков")
public class TestTrucksContainersAndBoxes {

    /**
     * in/out streams to swap System in out
     */
    private ByteArrayOutputStream outContent;
    private ByteArrayInputStream inContent;
    /**
     * original in/out streams links
     */
    private static final PrintStream originalOut = System.out;
    private static final InputStream originalIn = System.in;
    /**
     * string at which the testing program will terminate
     */
    private static final String EXIT_CODE = "exit";
    /**
     * os dependent line separator
     */
    private static final String LS = System.lineSeparator();
    /**
     * max time to execute each test, on occasion infinity loop
     */
    private static final int TEST_TIMEOUT_SECONDS = 10;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("Количество ящиков равно 1")
    void testOneBox() {
        collectTestOutput();
        launchApplication(
                collectTestInput("1"),
                collectTestOutput("Грузовик: 1",
                                "\tКонтейнер: 1",
                                "\t\tЯщик: 1",
                                "Необходимо:",
                                "грузовиков - 1 шт.",
                                "контейнеров - 1 шт."));
    }

    @Test
    @DisplayName("Количество ящиков равно 0")
    void testZeroBox() {
        collectTestOutput();
        launchApplication(
                collectTestInput("0"),
                collectTestOutput("Необходимо:",
                        "грузовиков - 0 шт.",
                        "контейнеров - 0 шт."));
    }

    @Test
    @DisplayName("Количество ящиков равно 2")
    void testTwoBoxes() {
        collectTestOutput();
        launchApplication(
                collectTestInput("2"),
                collectTestOutput("Грузовик: 1",
                        "\tКонтейнер: 1",
                        "\t\tЯщик: 1",
                        "\t\tЯщик: 2",
                        "Необходимо:",
                        "грузовиков - 1 шт.",
                        "контейнеров - 1 шт."));
    }

    @Test
    @DisplayName("Количество ящиков равно 27")
    void test27Boxes() {
        launchApplication(
                collectTestInput("27"),
                collectTestOutput("Грузовик: 1",
                        "\tКонтейнер: 1",
                        "\t\tЯщик: 1",
                        "\t\tЯщик: 2",
                        "\t\tЯщик: 3",
                        "\t\tЯщик: 4",
                        "\t\tЯщик: 5",
                        "\t\tЯщик: 6",
                        "\t\tЯщик: 7",
                        "\t\tЯщик: 8",
                        "\t\tЯщик: 9",
                        "\t\tЯщик: 10",
                        "\t\tЯщик: 11",
                        "\t\tЯщик: 12",
                        "\t\tЯщик: 13",
                        "\t\tЯщик: 14",
                        "\t\tЯщик: 15",
                        "\t\tЯщик: 16",
                        "\t\tЯщик: 17",
                        "\t\tЯщик: 18",
                        "\t\tЯщик: 19",
                        "\t\tЯщик: 20",
                        "\t\tЯщик: 21",
                        "\t\tЯщик: 22",
                        "\t\tЯщик: 23",
                        "\t\tЯщик: 24",
                        "\t\tЯщик: 25",
                        "\t\tЯщик: 26",
                        "\t\tЯщик: 27",
                        "Необходимо:",
                        "грузовиков - 1 шт.",
                        "контейнеров - 1 шт."));
    }

    @Test
    @DisplayName("Количество ящиков равно 28")
    void test28Boxes() {
        launchApplication(
                collectTestInput("28"),
                collectTestOutput("Грузовик: 1",
                        "\tКонтейнер: 1",
                        "\t\tЯщик: 1",
                        "\t\tЯщик: 2",
                        "\t\tЯщик: 3",
                        "\t\tЯщик: 4",
                        "\t\tЯщик: 5",
                        "\t\tЯщик: 6",
                        "\t\tЯщик: 7",
                        "\t\tЯщик: 8",
                        "\t\tЯщик: 9",
                        "\t\tЯщик: 10",
                        "\t\tЯщик: 11",
                        "\t\tЯщик: 12",
                        "\t\tЯщик: 13",
                        "\t\tЯщик: 14",
                        "\t\tЯщик: 15",
                        "\t\tЯщик: 16",
                        "\t\tЯщик: 17",
                        "\t\tЯщик: 18",
                        "\t\tЯщик: 19",
                        "\t\tЯщик: 20",
                        "\t\tЯщик: 21",
                        "\t\tЯщик: 22",
                        "\t\tЯщик: 23",
                        "\t\tЯщик: 24",
                        "\t\tЯщик: 25",
                        "\t\tЯщик: 26",
                        "\t\tЯщик: 27",
                        "\tКонтейнер: 2",
                        "\t\tЯщик: 28",
                        "Необходимо:",
                        "грузовиков - 1 шт.",
                        "контейнеров - 2 шт."));
    }

    @Test
    @DisplayName("Количество ящиков равно 325")
    void test325Boxes() {
        launchApplication(
                collectTestInput("325"),
                collectTestOutput("Грузовик: 1",
                        "\tКонтейнер: 1",
                        "\t\tЯщик: 1",
                        "\t\tЯщик: 2",
                        "\t\tЯщик: 3",
                        "\t\tЯщик: 4",
                        "\t\tЯщик: 5",
                        "\t\tЯщик: 6",
                        "\t\tЯщик: 7",
                        "\t\tЯщик: 8",
                        "\t\tЯщик: 9",
                        "\t\tЯщик: 10",
                        "\t\tЯщик: 11",
                        "\t\tЯщик: 12",
                        "\t\tЯщик: 13",
                        "\t\tЯщик: 14",
                        "\t\tЯщик: 15",
                        "\t\tЯщик: 16",
                        "\t\tЯщик: 17",
                        "\t\tЯщик: 18",
                        "\t\tЯщик: 19",
                        "\t\tЯщик: 20",
                        "\t\tЯщик: 21",
                        "\t\tЯщик: 22",
                        "\t\tЯщик: 23",
                        "\t\tЯщик: 24",
                        "\t\tЯщик: 25",
                        "\t\tЯщик: 26",
                        "\t\tЯщик: 27",
                        "\tКонтейнер: 2",
                        "\t\tЯщик: 28",
                        "\t\tЯщик: 29",
                        "\t\tЯщик: 30",
                        "\t\tЯщик: 31",
                        "\t\tЯщик: 32",
                        "\t\tЯщик: 33",
                        "\t\tЯщик: 34",
                        "\t\tЯщик: 35",
                        "\t\tЯщик: 36",
                        "\t\tЯщик: 37",
                        "\t\tЯщик: 38",
                        "\t\tЯщик: 39",
                        "\t\tЯщик: 40",
                        "\t\tЯщик: 41",
                        "\t\tЯщик: 42",
                        "\t\tЯщик: 43",
                        "\t\tЯщик: 44",
                        "\t\tЯщик: 45",
                        "\t\tЯщик: 46",
                        "\t\tЯщик: 47",
                        "\t\tЯщик: 48",
                        "\t\tЯщик: 49",
                        "\t\tЯщик: 50",
                        "\t\tЯщик: 51",
                        "\t\tЯщик: 52",
                        "\t\tЯщик: 53",
                        "\t\tЯщик: 54",
                        "\tКонтейнер: 3",
                        "\t\tЯщик: 55",
                        "\t\tЯщик: 56",
                        "\t\tЯщик: 57",
                        "\t\tЯщик: 58",
                        "\t\tЯщик: 59",
                        "\t\tЯщик: 60",
                        "\t\tЯщик: 61",
                        "\t\tЯщик: 62",
                        "\t\tЯщик: 63",
                        "\t\tЯщик: 64",
                        "\t\tЯщик: 65",
                        "\t\tЯщик: 66",
                        "\t\tЯщик: 67",
                        "\t\tЯщик: 68",
                        "\t\tЯщик: 69",
                        "\t\tЯщик: 70",
                        "\t\tЯщик: 71",
                        "\t\tЯщик: 72",
                        "\t\tЯщик: 73",
                        "\t\tЯщик: 74",
                        "\t\tЯщик: 75",
                        "\t\tЯщик: 76",
                        "\t\tЯщик: 77",
                        "\t\tЯщик: 78",
                        "\t\tЯщик: 79",
                        "\t\tЯщик: 80",
                        "\t\tЯщик: 81",
                        "\tКонтейнер: 4",
                        "\t\tЯщик: 82",
                        "\t\tЯщик: 83",
                        "\t\tЯщик: 84",
                        "\t\tЯщик: 85",
                        "\t\tЯщик: 86",
                        "\t\tЯщик: 87",
                        "\t\tЯщик: 88",
                        "\t\tЯщик: 89",
                        "\t\tЯщик: 90",
                        "\t\tЯщик: 91",
                        "\t\tЯщик: 92",
                        "\t\tЯщик: 93",
                        "\t\tЯщик: 94",
                        "\t\tЯщик: 95",
                        "\t\tЯщик: 96",
                        "\t\tЯщик: 97",
                        "\t\tЯщик: 98",
                        "\t\tЯщик: 99",
                        "\t\tЯщик: 100",
                        "\t\tЯщик: 101",
                        "\t\tЯщик: 102",
                        "\t\tЯщик: 103",
                        "\t\tЯщик: 104",
                        "\t\tЯщик: 105",
                        "\t\tЯщик: 106",
                        "\t\tЯщик: 107",
                        "\t\tЯщик: 108",
                        "\tКонтейнер: 5",
                        "\t\tЯщик: 109",
                        "\t\tЯщик: 110",
                        "\t\tЯщик: 111",
                        "\t\tЯщик: 112",
                        "\t\tЯщик: 113",
                        "\t\tЯщик: 114",
                        "\t\tЯщик: 115",
                        "\t\tЯщик: 116",
                        "\t\tЯщик: 117",
                        "\t\tЯщик: 118",
                        "\t\tЯщик: 119",
                        "\t\tЯщик: 120",
                        "\t\tЯщик: 121",
                        "\t\tЯщик: 122",
                        "\t\tЯщик: 123",
                        "\t\tЯщик: 124",
                        "\t\tЯщик: 125",
                        "\t\tЯщик: 126",
                        "\t\tЯщик: 127",
                        "\t\tЯщик: 128",
                        "\t\tЯщик: 129",
                        "\t\tЯщик: 130",
                        "\t\tЯщик: 131",
                        "\t\tЯщик: 132",
                        "\t\tЯщик: 133",
                        "\t\tЯщик: 134",
                        "\t\tЯщик: 135",
                        "\tКонтейнер: 6",
                        "\t\tЯщик: 136",
                        "\t\tЯщик: 137",
                        "\t\tЯщик: 138",
                        "\t\tЯщик: 139",
                        "\t\tЯщик: 140",
                        "\t\tЯщик: 141",
                        "\t\tЯщик: 142",
                        "\t\tЯщик: 143",
                        "\t\tЯщик: 144",
                        "\t\tЯщик: 145",
                        "\t\tЯщик: 146",
                        "\t\tЯщик: 147",
                        "\t\tЯщик: 148",
                        "\t\tЯщик: 149",
                        "\t\tЯщик: 150",
                        "\t\tЯщик: 151",
                        "\t\tЯщик: 152",
                        "\t\tЯщик: 153",
                        "\t\tЯщик: 154",
                        "\t\tЯщик: 155",
                        "\t\tЯщик: 156",
                        "\t\tЯщик: 157",
                        "\t\tЯщик: 158",
                        "\t\tЯщик: 159",
                        "\t\tЯщик: 160",
                        "\t\tЯщик: 161",
                        "\t\tЯщик: 162",
                        "\tКонтейнер: 7",
                        "\t\tЯщик: 163",
                        "\t\tЯщик: 164",
                        "\t\tЯщик: 165",
                        "\t\tЯщик: 166",
                        "\t\tЯщик: 167",
                        "\t\tЯщик: 168",
                        "\t\tЯщик: 169",
                        "\t\tЯщик: 170",
                        "\t\tЯщик: 171",
                        "\t\tЯщик: 172",
                        "\t\tЯщик: 173",
                        "\t\tЯщик: 174",
                        "\t\tЯщик: 175",
                        "\t\tЯщик: 176",
                        "\t\tЯщик: 177",
                        "\t\tЯщик: 178",
                        "\t\tЯщик: 179",
                        "\t\tЯщик: 180",
                        "\t\tЯщик: 181",
                        "\t\tЯщик: 182",
                        "\t\tЯщик: 183",
                        "\t\tЯщик: 184",
                        "\t\tЯщик: 185",
                        "\t\tЯщик: 186",
                        "\t\tЯщик: 187",
                        "\t\tЯщик: 188",
                        "\t\tЯщик: 189",
                        "\tКонтейнер: 8",
                        "\t\tЯщик: 190",
                        "\t\tЯщик: 191",
                        "\t\tЯщик: 192",
                        "\t\tЯщик: 193",
                        "\t\tЯщик: 194",
                        "\t\tЯщик: 195",
                        "\t\tЯщик: 196",
                        "\t\tЯщик: 197",
                        "\t\tЯщик: 198",
                        "\t\tЯщик: 199",
                        "\t\tЯщик: 200",
                        "\t\tЯщик: 201",
                        "\t\tЯщик: 202",
                        "\t\tЯщик: 203",
                        "\t\tЯщик: 204",
                        "\t\tЯщик: 205",
                        "\t\tЯщик: 206",
                        "\t\tЯщик: 207",
                        "\t\tЯщик: 208",
                        "\t\tЯщик: 209",
                        "\t\tЯщик: 210",
                        "\t\tЯщик: 211",
                        "\t\tЯщик: 212",
                        "\t\tЯщик: 213",
                        "\t\tЯщик: 214",
                        "\t\tЯщик: 215",
                        "\t\tЯщик: 216",
                        "\tКонтейнер: 9",
                        "\t\tЯщик: 217",
                        "\t\tЯщик: 218",
                        "\t\tЯщик: 219",
                        "\t\tЯщик: 220",
                        "\t\tЯщик: 221",
                        "\t\tЯщик: 222",
                        "\t\tЯщик: 223",
                        "\t\tЯщик: 224",
                        "\t\tЯщик: 225",
                        "\t\tЯщик: 226",
                        "\t\tЯщик: 227",
                        "\t\tЯщик: 228",
                        "\t\tЯщик: 229",
                        "\t\tЯщик: 230",
                        "\t\tЯщик: 231",
                        "\t\tЯщик: 232",
                        "\t\tЯщик: 233",
                        "\t\tЯщик: 234",
                        "\t\tЯщик: 235",
                        "\t\tЯщик: 236",
                        "\t\tЯщик: 237",
                        "\t\tЯщик: 238",
                        "\t\tЯщик: 239",
                        "\t\tЯщик: 240",
                        "\t\tЯщик: 241",
                        "\t\tЯщик: 242",
                        "\t\tЯщик: 243",
                        "\tКонтейнер: 10",
                        "\t\tЯщик: 244",
                        "\t\tЯщик: 245",
                        "\t\tЯщик: 246",
                        "\t\tЯщик: 247",
                        "\t\tЯщик: 248",
                        "\t\tЯщик: 249",
                        "\t\tЯщик: 250",
                        "\t\tЯщик: 251",
                        "\t\tЯщик: 252",
                        "\t\tЯщик: 253",
                        "\t\tЯщик: 254",
                        "\t\tЯщик: 255",
                        "\t\tЯщик: 256",
                        "\t\tЯщик: 257",
                        "\t\tЯщик: 258",
                        "\t\tЯщик: 259",
                        "\t\tЯщик: 260",
                        "\t\tЯщик: 261",
                        "\t\tЯщик: 262",
                        "\t\tЯщик: 263",
                        "\t\tЯщик: 264",
                        "\t\tЯщик: 265",
                        "\t\tЯщик: 266",
                        "\t\tЯщик: 267",
                        "\t\tЯщик: 268",
                        "\t\tЯщик: 269",
                        "\t\tЯщик: 270",
                        "\tКонтейнер: 11",
                        "\t\tЯщик: 271",
                        "\t\tЯщик: 272",
                        "\t\tЯщик: 273",
                        "\t\tЯщик: 274",
                        "\t\tЯщик: 275",
                        "\t\tЯщик: 276",
                        "\t\tЯщик: 277",
                        "\t\tЯщик: 278",
                        "\t\tЯщик: 279",
                        "\t\tЯщик: 280",
                        "\t\tЯщик: 281",
                        "\t\tЯщик: 282",
                        "\t\tЯщик: 283",
                        "\t\tЯщик: 284",
                        "\t\tЯщик: 285",
                        "\t\tЯщик: 286",
                        "\t\tЯщик: 287",
                        "\t\tЯщик: 288",
                        "\t\tЯщик: 289",
                        "\t\tЯщик: 290",
                        "\t\tЯщик: 291",
                        "\t\tЯщик: 292",
                        "\t\tЯщик: 293",
                        "\t\tЯщик: 294",
                        "\t\tЯщик: 295",
                        "\t\tЯщик: 296",
                        "\t\tЯщик: 297",
                        "\tКонтейнер: 12",
                        "\t\tЯщик: 298",
                        "\t\tЯщик: 299",
                        "\t\tЯщик: 300",
                        "\t\tЯщик: 301",
                        "\t\tЯщик: 302",
                        "\t\tЯщик: 303",
                        "\t\tЯщик: 304",
                        "\t\tЯщик: 305",
                        "\t\tЯщик: 306",
                        "\t\tЯщик: 307",
                        "\t\tЯщик: 308",
                        "\t\tЯщик: 309",
                        "\t\tЯщик: 310",
                        "\t\tЯщик: 311",
                        "\t\tЯщик: 312",
                        "\t\tЯщик: 313",
                        "\t\tЯщик: 314",
                        "\t\tЯщик: 315",
                        "\t\tЯщик: 316",
                        "\t\tЯщик: 317",
                        "\t\tЯщик: 318",
                        "\t\tЯщик: 319",
                        "\t\tЯщик: 320",
                        "\t\tЯщик: 321",
                        "\t\tЯщик: 322",
                        "\t\tЯщик: 323",
                        "\t\tЯщик: 324",
                        "Грузовик: 2",
                        "\tКонтейнер: 13",
                        "\t\tЯщик: 325",
                        "Необходимо:",
                        "грузовиков - 2 шт.",
                        "контейнеров - 13 шт."));
    }

    /**
     * call user method
     *
     * @param userInputMock - string lines imitate real user input
     * @param expected      - expected console output
     * @param message       - message if test has failed
     */
    private void launchApplication(String userInputMock, String expected, String message) {
        provideInput(userInputMock);
        assertTimeoutPreemptively(
                Duration.ofSeconds(TEST_TIMEOUT_SECONDS),
                () -> Main.main(new String[0]));
        assertEquals(
                expected.strip(),
                outContent
                        .toString()
                        .strip()
                        .replaceAll("\r\n", "\n")
                        .replaceAll("\n", System.lineSeparator()),
                message);
    }

    /* overload    */
    private void launchApplication(String userInputMock, String expected) {
        launchApplication(userInputMock, expected, null);
    }

    /*
    Change default System.in and fill with strings to imitate user input
    @param data - string for application input
     */
    private void provideInput(String data) {
        inContent = new ByteArrayInputStream(data.getBytes());
        System.setIn(inContent);
    }

    /*
    Construct string with lines separators and EXIT_CODE which signal that application must stop
    @param cases - strings, emulated user inputs for application
     */
    private String collectTestInput(String... cases) {
        return collectTestOutput(cases)
                .concat(EXIT_CODE);
    }

    /*
  Construct string with lines separators, use for base string actual, expected
  @param cases - strings, input/output for each line
   */
    private String collectTestOutput(String... cases) {
        return String.join(LS, cases)
                .concat(LS);
    }

}
