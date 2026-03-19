package org.example.treciparcijalnitest.service;

import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.domain.ProgramObrazovanja;
import org.example.treciparcijalnitest.dto.ProgramObrazovanjaDTO;
import org.example.treciparcijalnitest.repository.ProgramObrazovanjaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProgramObrazovanjaServiceImpl implements ProgramObrazovanjaService{
    private ProgramObrazovanjaRepository repository;

    @Override
    public List<ProgramObrazovanjaDTO> getAll() {
        return repository.getAll().stream().map(this::convertDomainToDTO).toList();
    }

    @Override
    public Optional<ProgramObrazovanjaDTO> getById(Integer id) {
        return repository.getById(id).map(this::convertDomainToDTO);
    }

    @Override
    public ProgramObrazovanjaDTO save(ProgramObrazovanjaDTO objectDTO) {
        return convertDomainToDTO(repository.save(convertDtoToDomain(objectDTO)));
    }

    @Override
    public Optional<ProgramObrazovanjaDTO> update(ProgramObrazovanjaDTO objectDTO, Integer id) {
        Optional<ProgramObrazovanja> updatedOptional = repository.update(convertDtoToDomain(objectDTO), id);

        if(updatedOptional.isPresent()) {
            return Optional.of(convertDomainToDTO(updatedOptional.get()));
        }

        return Optional.empty();
    }

    @Override
    public boolean exists(Integer id) {
        return repository.exists(id);
    }

    @Override
    public boolean delete(Integer id) {
        return repository.delete(id);
    }

    private ProgramObrazovanjaDTO convertDomainToDTO(ProgramObrazovanja object) {
        return new ProgramObrazovanjaDTO(object.getName(), object.getCSVET());
    }

    private ProgramObrazovanja convertDtoToDomain(ProgramObrazovanjaDTO objectDTO) {
        Integer latestId = repository.getAll()
                .stream()
                .map(ProgramObrazovanja::getId)
                .max(Integer::compare)
                .orElse(null);

        return new ProgramObrazovanja(latestId + 1, objectDTO.getName(), objectDTO.getCSVET());
    }
}
