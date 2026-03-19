package org.example.treciparcijalnitest.service;

import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.domain.Upis;
import org.example.treciparcijalnitest.dto.UpisDTO;
import org.example.treciparcijalnitest.repository.UpisRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpisServiceImpl implements UpisService{
    private UpisRepository repository;

    @Override
    public List<UpisDTO> getAll() {
        return repository.getAll().stream().map(this::convertDomainToDTO).toList();
    }

    @Override
    public Optional<UpisDTO> getById(Integer id) {
        return repository.getById(id).map(this::convertDomainToDTO);
    }

    @Override
    public UpisDTO save(UpisDTO objectDTO) {
        return convertDomainToDTO(repository.save(convertDtoToDomain(objectDTO)));
    }

    @Override
    public Optional<UpisDTO> update(UpisDTO objectDTO, Integer id) {
        Optional<Upis> updatedOptional = repository.update(convertDtoToDomain(objectDTO), id);

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

    private UpisDTO convertDomainToDTO(Upis object) {
        return new UpisDTO(object.getProgramId(), object.getStudentId());
    }

    private Upis convertDtoToDomain(UpisDTO objectDTO) {
        Integer latestId = repository.getAll()
                .stream()
                .map(Upis::getId)
                .max(Integer::compare)
                .orElse(null);

        return new Upis(latestId + 1, objectDTO.getProgramId(), objectDTO.getStudentId());
    }
}
