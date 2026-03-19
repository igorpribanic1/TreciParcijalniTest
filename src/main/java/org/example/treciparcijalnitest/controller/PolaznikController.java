package org.example.treciparcijalnitest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.dto.PolaznikDTO;
import org.example.treciparcijalnitest.service.PolaznikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/polaznik")
@AllArgsConstructor
public class PolaznikController {
    private PolaznikService service;

    @GetMapping
    public ResponseEntity<List<PolaznikDTO>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PolaznikDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<?> save(@Valid @RequestBody PolaznikDTO objectDTO) {
        PolaznikDTO savedDTO = service.save(objectDTO);
        return ResponseEntity.ok(savedDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolaznikDTO> update(@Valid @RequestBody PolaznikDTO objectDTO, @PathVariable Integer id) {
        if(service.exists(id)) {
            service.update(objectDTO, id);
            return ResponseEntity.ok(objectDTO);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if(service.exists(id)) {
            boolean result = service.delete(id);
            if(result) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
