package ru.gogagum.microservices.task2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gogagum.microservices.task2.Dao.MapEntry;

@Repository
public interface MapRepository extends JpaRepository<MapEntry, Integer> {
}
