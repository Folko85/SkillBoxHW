package core;

import java.util.*;
import java.util.stream.Collectors;

public class StationIndex {
    TreeMap<String, Set<String>> stations = new TreeMap();
    List<TreeSet<Station>> connections = new ArrayList<>();
    TreeSet<Line> lines = new TreeSet<>();

    public StationIndex() {
    }

    public void addAllLineStations(Line line, List<Station> stations) {
        Set<String> stationsSet = stations.stream().map(Station::getName).collect(Collectors.toSet());
        this.stations.put(line.getID(), stationsSet);
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public void addConnection(TreeSet<Station> stations) {
        if (!connections.contains(stations)) {
            connections.add(stations);
        }
    }

    public List<TreeSet<Station>> getConnections() {
        return connections;
    }
}
