package org.example.treciparcijalnitest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.dto.UpisDTO;
import org.example.treciparcijalnitest.service.UpisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/upis")
@AllArgsConstructor
public class UpisController {
    private UpisService service;

    @GetMapping
    public ResponseEntity<List<UpisDTO>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpisDTO> getById(@PathVariable Integer id) {
        Optional<UpisDTO> returnedObject = service.getById(id);
        return returnedObject
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<?> save(@Valid @RequestBody UpisDTO objectDTO) {
        UpisDTO savedDTO = service.save(objectDTO);
        return ResponseEntity.ok(savedDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpisDTO> update(@Valid @RequestBody UpisDTO objectDTO, @PathVariable Integer id) {
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
