package core;

public class Line implements Comparable<Line> {
    private String id;
    private String name;
    private String color;

    public Line(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.id + ". " + this.name;
    }

    public void addColor(String color) {
        this.color = color;
    }

    @Override
    public int compareTo(Line line) {
        return this.getID().compareTo(line.getID());
    }
}

