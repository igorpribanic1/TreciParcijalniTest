package org.example.treciparcijalnitest.service;

import org.example.treciparcijalnitest.dto.PolaznikDTO;

import java.util.List;
import java.util.Optional;

public interface PolaznikService {
    List<PolaznikDTO> getAll();
    Optional<PolaznikDTO> getById(Integer id);
    PolaznikDTO save(PolaznikDTO polaznikDTO);
    Optional<PolaznikDTO> update(PolaznikDTO polaznikDTO, Integer id);
    boolean exists(Integer id);
    boolean delete(Integer id);
}
