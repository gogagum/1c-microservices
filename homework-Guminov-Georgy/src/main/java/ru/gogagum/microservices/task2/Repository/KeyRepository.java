package ru.gogagum.microservices.task2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository<Key> extends JpaRepository<Key, Key> {
}
