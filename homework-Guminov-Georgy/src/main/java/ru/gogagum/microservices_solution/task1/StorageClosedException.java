package ru.gogagum.microservices_solution.task1;

public class StorageClosedException extends RuntimeException{
    StorageClosedException(String additionalMessage) {
        super("Storage is closed. " + additionalMessage);
    }
}
