package ru.gogagum.microservices.task2;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import ru.gogagum.microservices.task2.Dao.HasId;
import ru.gogagum.microservices.task2.Dao.MapEntry;
import ru.gogagum.microservices.task2.Exception.StorageClosedException;
import ru.gogagum.microservices.task2.Iteraror.StorageIterator;
import ru.gogagum.microservices.task2.Repository.KeyRepository;
import ru.gogagum.microservices.task2.Repository.MapRepository;
import ru.gogagum.microservices.task2.Repository.ValueRepository;
import ru.mipt1c.homework.task1.KeyValueStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class DBKeyValueStorage<K, V extends HasId> implements KeyValueStorage<K, V> {

    private boolean isClosed;

    private final MapRepository mapRepository;
    private final ValueRepository<V> valueRepository;
    private final KeyRepository<K> keyRepository;

    private final List<StorageIterator<K>> givenIterators;

    public DBKeyValueStorage(KeyRepository<K> keyRepository,
                             ValueRepository<V> valueRepository,
                             MapRepository mapRepository) {
        this.keyRepository = keyRepository;
        this.valueRepository = valueRepository;
        this.mapRepository = mapRepository;

        givenIterators = new ArrayList<>();
        isClosed = true;
    }

    @Override
    public V read(K key) {
        if (isClosed) {
            throw new StorageClosedException("Can not read.");
        }

        Optional<MapEntry> me = mapRepository.findById(key.hashCode());

        if (me.isPresent()) {
            Long valueId = me.get().getValId();
            Optional<V> val = valueRepository.findById(valueId);
            if (val.isPresent()) {
                return val.get();
            }
        }

        return null;
    }

    @Override
    public boolean exists(K key) {
        if (isClosed) {
            throw new StorageClosedException("Can not check existence.");
        }

        return mapRepository.existsById(key.hashCode());
    }

    @Override
    public void write(K key, V value) {
        if (isClosed) {
            throw new StorageClosedException("Can not write.");
        }

        Optional<MapEntry> me = mapRepository.findById(key.hashCode());

        keyRepository.save(key);

        if (me.isPresent()) {
            V newValue = valueRepository.save(value);
            mapRepository.save(new MapEntry(key.toString(), newValue.getId()));
        }
    }

    @Override
    public void delete(K key) {
        if (isClosed) {
            throw new StorageClosedException("Can not delete.");
        }

        Optional<MapEntry> me = mapRepository.findById(key.hashCode());
        me.ifPresent(mapEntry -> valueRepository.deleteById(mapEntry.getValId()));
        keyRepository.delete(key);
    }

    @Override
    public Iterator<K> readKeys() {
        return keyRepository.findAll().stream().iterator();
    }

    @Override
    public int size() {
        return keyRepository.findAll().toArray().length;
    }

    @Override
    public void flush() {
        keyRepository.flush();
        valueRepository.flush();
        mapRepository.flush();
    }

    @Override
    public void close() throws IOException {
        isClosed = true;
        flush();

        for (StorageIterator<K> givenIterator : givenIterators) {
            givenIterator.invalidate();
        }
    }
}
