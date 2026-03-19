package org.example.treciparcijalnitest.repository;

import org.example.treciparcijalnitest.domain.ProgramObrazovanja;

import java.util.List;
import java.util.Optional;

public interface ProgramObrazovanjaRepository {
    List<ProgramObrazovanja> getAll();
    Optional<ProgramObrazovanja> getById(Integer id);
    ProgramObrazovanja save(ProgramObrazovanja programObrazovanja);
    Optional<ProgramObrazovanja> update(ProgramObrazovanja programObrazovanja, Integer id);
    boolean exists(Integer id);
    boolean delete(Integer id);
}
