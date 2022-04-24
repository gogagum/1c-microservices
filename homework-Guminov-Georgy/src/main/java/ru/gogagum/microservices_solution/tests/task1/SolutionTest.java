package ru.gogagum.microservices_solution.tests.task1;

import org.junit.Test;
import ru.gogagum.microservices_solution.task1.GKeyValueStorage;
import ru.mipt1c.homework.task1.KeyValueStorage;
import ru.mipt1c.homework.task1.MalformedDataException;
import ru.mipt1c.homework.tests.task1.AbstractSingleFileStorageTest;
import ru.mipt1c.homework.tests.task1.StorageTestUtils;
import ru.mipt1c.homework.tests.task1.Student;
import ru.mipt1c.homework.tests.task1.StudentKey;

import java.util.Iterator;

public class SolutionTest extends AbstractSingleFileStorageTest {
    @Override
    protected KeyValueStorage<String, String> buildStringsStorage(String path) throws MalformedDataException {
        return new GKeyValueStorage<>(path);
    }

    @Override
    protected KeyValueStorage<Integer, Double> buildNumbersStorage(String path) throws MalformedDataException {
        return new GKeyValueStorage<>(path);
    }

    @Override
    protected KeyValueStorage<StudentKey, Student> buildPojoStorage(String path) throws MalformedDataException {
        return new GKeyValueStorage<>(path);
    }

    @Test(expected = Exception.class)
    public void iteratorsInvalidationNext() {
        StorageTestUtils.doInTempDirectory(path -> doWithPojo(path, storage -> {
            storage.write(KEY_1, VALUE_1);
            storage.write(KEY_2, VALUE_2);
            storage.write(KEY_3, VALUE_3);
            Iterator<StudentKey> iterator = storage.readKeys();
            iterator.next();
            storage.close();
            iterator.next();

            throw new AssertionError("Storage iterator should not allow iterate when storage is closed.");
        }));
    }

    @Test(expected = Exception.class)
    public void iteratorsInvalidationHasNext() {
        StorageTestUtils.doInTempDirectory(path -> doWithPojo(path, storage -> {
            storage.write(KEY_1, VALUE_1);
            storage.write(KEY_2, VALUE_2);
            storage.write(KEY_3, VALUE_3);
            Iterator<StudentKey> iterator = storage.readKeys();
            iterator.next();
            storage.close();
            iterator.hasNext();

            throw new AssertionError("Storage iterator should not allow iterate when storage is closed.");
        }));
    }
}
