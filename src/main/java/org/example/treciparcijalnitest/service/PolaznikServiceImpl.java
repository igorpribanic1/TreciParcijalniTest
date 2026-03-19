package org.example.treciparcijalnitest.service;

import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.domain.Polaznik;
import org.example.treciparcijalnitest.dto.PolaznikDTO;
import org.example.treciparcijalnitest.repository.PolaznikRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PolaznikServiceImpl implements PolaznikService{
    private PolaznikRepository repository;

    @Override
    public List<PolaznikDTO> getAll() {
        return repository.getAll().stream().map(this::convertDomainToDTO).toList();
    }

    @Override
    public Optional<PolaznikDTO> getById(Integer id) {
        return repository.getById(id).map(this::convertDomainToDTO);
    }

    @Override
    public PolaznikDTO save(PolaznikDTO objectDTO) {
        return convertDomainToDTO(repository.save(convertDtoToDomain(objectDTO)));
    }

    @Override
    public Optional<PolaznikDTO> update(PolaznikDTO objectDTO, Integer id) {
        Optional<Polaznik> updatedOptional = repository.update(convertDtoToDomain(objectDTO), id);

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

    private PolaznikDTO convertDomainToDTO(Polaznik object) {
        return new PolaznikDTO(object.getFirstName(), object.getLastName());
    }

    private Polaznik convertDtoToDomain(PolaznikDTO objectDTO) {
        Integer latestId = repository.getAll()
                .stream()
                .map(Polaznik::getId)
                .max(Integer::compare)
                .orElse(null);

        return new Polaznik(latestId + 1, objectDTO.getFirstName(), objectDTO.getLastName());
    }
}
