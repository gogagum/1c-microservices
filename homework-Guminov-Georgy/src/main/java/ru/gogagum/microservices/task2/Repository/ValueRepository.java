package ru.gogagum.microservices.task2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueRepository<Val> extends JpaRepository<Val, Long> {
}
