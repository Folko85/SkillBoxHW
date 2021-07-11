package ru.skillbox.mongo.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.mongo.model.Student;
import ru.skillbox.mongo.repository.StudentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String writeFileToDataBase(MultipartFile file) throws IOException, CsvException {
        studentRepository.deleteAll();
        InputStream inputStream = file.getInputStream();
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
        List<String[]> allStrings = reader.readAll();
        List<Student> allStudents = allStrings.stream().map(Student::new).collect(Collectors.toList());
        studentRepository.saveAll(allStudents);
        return "Файл успешно загружен. Всего записей в базе: " + studentRepository.count() +
                "<br>" + "Количество студентов старше 40 лет: " + studentRepository.findByAgeOlder(40).size() +
                "<br>" + "Имя самого молодого сдудента: " + studentRepository.findTopByOrderByAgeAsc().getFullName() +
                "<br>" + "Курсы самого старого студента: " + studentRepository.findTopByOrderByAgeDesc().getCourses();
    }

}
