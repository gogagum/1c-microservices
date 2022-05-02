package ru.gogagum.microservices.task2.Repository;

import org.springframework.stereotype.Repository;
import ru.gogagum.microservices.task2.Dao.Student;

@Repository
public interface StudentRepository extends ValueRepository<Student> {
}
