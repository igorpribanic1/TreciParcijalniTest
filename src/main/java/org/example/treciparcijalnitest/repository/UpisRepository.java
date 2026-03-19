package org.example.treciparcijalnitest.repository;

import org.example.treciparcijalnitest.domain.Upis;

import java.util.List;
import java.util.Optional;

public interface UpisRepository {
    List<Upis> getAll();
    Optional<Upis> getById(Integer id);
    Upis save(Upis upis);
    Optional<Upis> update(Upis upis, Integer id);
    boolean exists(Integer id);
    boolean delete(Integer id);
}
