package core;

public class Station implements Comparable<Station> {
    private String lineID;
    private String stationName;

    public Station(String name, Line line) {
        this.stationName = name;
        this.lineID = line.getID();
    }

    public String getLineID() {
        return this.lineID;
    }

    public String getName() {
        return this.stationName;
    }

    public String toString() {
        return this.stationName;
    }

    @Override
    public int compareTo(Station station) {
        if (this.getLineID().equals(station.getLineID())) {
            return this.getName().compareTo(station.getName());
        } else return this.getLineID().compareTo(station.getLineID());
    }
}
