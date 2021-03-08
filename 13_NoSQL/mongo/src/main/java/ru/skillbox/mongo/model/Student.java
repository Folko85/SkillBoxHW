package ru.skillbox.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "students")
public class Student {

    public Student(){
    }

    public Student(String[] strings){
        this.fullName = strings[0];
        this.age = Integer.parseInt(strings[1]);
        this.courses = Arrays.stream(strings[2].split(","))
                .map(Course::valueOf).collect(Collectors.toList());
    }

    private String fullName;

    private int age;

    private List<Course> courses;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
