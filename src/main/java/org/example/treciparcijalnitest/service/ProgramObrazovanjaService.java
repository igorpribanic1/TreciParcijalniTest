package org.example.treciparcijalnitest.service;

import org.example.treciparcijalnitest.dto.ProgramObrazovanjaDTO;

import java.util.List;
import java.util.Optional;

public interface ProgramObrazovanjaService {
    List<ProgramObrazovanjaDTO> getAll();
    Optional<ProgramObrazovanjaDTO> getById(Integer id);
    ProgramObrazovanjaDTO save(ProgramObrazovanjaDTO programObrazovanjaDTO);
    Optional<ProgramObrazovanjaDTO> update(ProgramObrazovanjaDTO programObrazovanjaDTO, Integer id);
    boolean exists(Integer id);
    boolean delete(Integer id);
}
