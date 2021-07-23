package model;

import java.time.LocalDate;
import java.util.Objects;

public class Voter {

    private String name;
    private LocalDate birthDay;

    public Voter(String name, LocalDate birthDay) {
        this.name = name;
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(name, voter.name) && Objects.equals(birthDay, voter.birthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDay);
    }

    public String toString() {
        return name + " (" + birthDay + ")";
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }
}
