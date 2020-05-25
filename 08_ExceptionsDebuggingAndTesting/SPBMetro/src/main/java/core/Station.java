package core;

public class Station implements Comparable<Station>
{
    private Line line;
    private String name;

    public Station(String name, Line line)
    {
        this.name = name;     // у каждой станции есть имя
        this.line = line;      // и она относится к какой-то линии
    }

    public Line getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int compareTo(Station station)         // сравнение станций идёт по линиям
    {
        int lineComparison = line.compareTo(station.getLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());   // если линия одна, то сравниваются имена
    }

    @Override
    public boolean equals(Object obj)
    {
        return compareTo((Station) obj) == 0;
    }

    @Override
    public String toString()
    {
        return name;
    }
}