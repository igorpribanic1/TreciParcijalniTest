package org.example.treciparcijalnitest.service;

import org.example.treciparcijalnitest.dto.UpisDTO;

import java.util.List;
import java.util.Optional;

public interface UpisService {
    List<UpisDTO> getAll();
    Optional<UpisDTO> getById(Integer id);
    UpisDTO save(UpisDTO upisDTO);
    Optional<UpisDTO> update(UpisDTO upisDTO, Integer id);
    boolean exists(Integer id);
    boolean delete(Integer id);
}
