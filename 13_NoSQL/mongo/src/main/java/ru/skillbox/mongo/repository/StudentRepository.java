package ru.skillbox.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.skillbox.mongo.model.Student;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    @Query ("{'age': {$gt: ?0}}")
    List<Student> findByAgeOlder(int ageGT);

    Student findTopByOrderByAgeAsc();

    Student findTopByOrderByAgeDesc();
}
