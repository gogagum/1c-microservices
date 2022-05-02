package ru.gogagum.microservices.task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gogagum.microservices.task2.Dao.Student;
import ru.gogagum.microservices.task2.Repository.KeyRepository;
import ru.gogagum.microservices.task2.Repository.MapRepository;
import ru.gogagum.microservices.task2.Repository.StudentKeyRepository;
import ru.gogagum.microservices.task2.Repository.StudentRepository;
import ru.mipt1c.homework.task1.KeyValueStorage;
import ru.mipt1c.homework.tests.task1.StorageTestUtils;
import ru.mipt1c.homework.tests.task1.StudentKey;

@SpringBootTest
class HomeworkGuminovGeorgyApplicationTests {

	@Autowired
	KeyRepository<Integer> integerKeyRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentKeyRepository studentKeyRepository;

	@Autowired
	MapRepository mapRepository;

	public static final StudentKey STUDENT_KEY
			= new StudentKey(813, "Georgy");

	public static final Student STUDENT
			= new Student("Zelenograd", StorageTestUtils.date(2000, 3, 27), true, 5.47);

	@Test
	void contextLoads() {
	}

	@Test
	public void oneKeyTest() {
        KeyValueStorage<Integer, Student> storage
                = new DBKeyValueStorage<>(integerKeyRepository,
                                          studentRepository,
            	                          mapRepository);

        storage.write(1, STUDENT);

		Assertions.assertEquals(STUDENT, storage.read(1));
		StorageTestUtils.assertFullyMatch(storage.readKeys(), 1);
	}

}
