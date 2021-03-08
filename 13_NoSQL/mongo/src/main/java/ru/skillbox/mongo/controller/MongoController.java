package ru.skillbox.mongo.controller;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.mongo.repository.StudentRepository;
import ru.skillbox.mongo.service.StudentService;

import java.io.IOException;

@RestController
public class MongoController {

    private final StudentService studentService;

    @Autowired
    public MongoController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/students")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().equals("mongo.csv")) {
            return "Вы должны загрузить файл mongo.csv";
        } else {
            try {
                return studentService.writeFileToDataBase(file);
            } catch (IOException | CsvException e) {
                e.printStackTrace();
                return "Возникла ошибка: " + e.getMessage();
            }

        }
    }

}
