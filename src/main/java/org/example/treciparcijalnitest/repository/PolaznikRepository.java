package org.example.treciparcijalnitest.repository;

import org.example.treciparcijalnitest.domain.Polaznik;

import java.util.List;
import java.util.Optional;

public interface PolaznikRepository {
    List<Polaznik> getAll();
    Optional<Polaznik> getById(Integer id);
    Polaznik save(Polaznik polaznik);
    Optional<Polaznik> update(Polaznik polaznik, Integer id);
    boolean exists(Integer id);
    boolean delete(Integer id);
}
