package org.example.treciparcijalnitest.controller;

import org.example.treciparcijalnitest.dto.ProgramObrazovanjaDTO;
import org.example.treciparcijalnitest.service.ProgramObrazovanjaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProgramObrazovanjaControllerTest {
    @Mock
    private ProgramObrazovanjaService service;

    @InjectMocks
    private ProgramObrazovanjaController controller;

    private MockMvc mockMvc;

    private List<ProgramObrazovanjaDTO> objectList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        ProgramObrazovanjaDTO object1 = new ProgramObrazovanjaDTO("Prvi program obrazovnja", 10);
        ProgramObrazovanjaDTO object2 = new ProgramObrazovanjaDTO("Drugi program obrazovnja", 20);
        objectList.add(object1);
        objectList.add(object2);
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(objectList);

        mockMvc.perform(get("/program-obrazovanja")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Prvi program obrazovnja")))
                .andExpect(jsonPath("$[1].name", is("Drugi program obrazovnja")));

        verify(service, times(1)).getAll();
    }

    @Test
    void testGetById_Found() throws Exception {
        when(service.getById(1)).thenReturn(Optional.of(objectList.get(0)));

        mockMvc.perform(get("/program-obrazovanja/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Prvi program obrazovnja"));

        verify(service, times(1)).getById(1);
    }



    @Test
    void testGetById_NotFound() throws Exception {
        when(service.getById(12345)).thenReturn(Optional.empty());

        mockMvc.perform(get("/program-obrazovanja/{id}", 12345)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getById(12345);
    }



    @Test
    void testSave() throws Exception {
        ProgramObrazovanjaDTO object3 = new ProgramObrazovanjaDTO("Treći program obrazovanja", 12);
        objectList.add(object3);

        when(service.save(any(ProgramObrazovanjaDTO.class))).thenReturn(object3);

        mockMvc.perform(post("/program-obrazovanja/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Treći program obrazovanja",
                    "csvet": 12
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(service, times(1)).save(any(ProgramObrazovanjaDTO.class));
    }


    @Test
    void testUpdate_Success() throws Exception {
        ProgramObrazovanjaDTO objectToUpdate = new ProgramObrazovanjaDTO("Treći program obrazovanja Update", 12);

        when(service.exists(anyInt())).thenReturn(true);
        when(service.update(any(ProgramObrazovanjaDTO.class), anyInt())).thenReturn(Optional.of(objectToUpdate));

        mockMvc.perform(put("/program-obrazovanja/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Treći program obrazovanja Update",
                    "csvet": 12
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Treći program obrazovanja Update"));


        verify(service, times(1)).exists(anyInt());
        verify(service, times(1)).update(any(ProgramObrazovanjaDTO.class), anyInt());
    }



    @Test
    void testUpdate_NotFound() throws Exception {
        ProgramObrazovanjaDTO objectToUpdate = new ProgramObrazovanjaDTO("Treći program obrazovanja", 12);

        when(service.exists(anyInt())).thenReturn(false);
        when(service.update(any(ProgramObrazovanjaDTO.class), anyInt())).thenReturn(Optional.of(objectToUpdate));

        mockMvc.perform(put("/program-obrazovanja/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Treći program obrazovanja",
                    "csvet": 12
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        verify(service, times(1)).exists(anyInt());
        verify(service, never()).update(any(ProgramObrazovanjaDTO.class), anyInt());
    }



    @Test
    void testDelete_Success() throws Exception {
        when(service.exists(anyInt())).thenReturn(true);
        when(service.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/program-obrazovanja/{id}", 3))
                .andExpect(status().isOk());


        verify(service, times(1)).exists(anyInt());
        verify(service, times(1)).delete(anyInt());
    }



    @Test
    void testDelete_NotFound() throws Exception {
        when(service.exists(anyInt())).thenReturn(false);
        when(service.delete(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/program-obrazovanja/{id}", 3))
                .andExpect(status().isNotFound());


        verify(service, times(1)).exists(anyInt());
        verify(service, never()).delete(anyInt());
    }
}
