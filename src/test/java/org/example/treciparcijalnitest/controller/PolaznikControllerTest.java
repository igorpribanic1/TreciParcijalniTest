package org.example.treciparcijalnitest.controller;

import org.example.treciparcijalnitest.dto.PolaznikDTO;
import org.example.treciparcijalnitest.service.PolaznikService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PolaznikControllerTest {

    @Mock
    private PolaznikService service;

    @InjectMocks
    private PolaznikController controller;

    private MockMvc mockMvc;

    private List<PolaznikDTO> objectList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        PolaznikDTO object1 = new PolaznikDTO("Marko", "Marić");
        PolaznikDTO object2 = new PolaznikDTO("Pero", "Perić");
        objectList.add(object1);
        objectList.add(object2);
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(objectList);

        mockMvc.perform(get("/polaznik")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Marko")))
                .andExpect(jsonPath("$[1].firstName", is("Pero")));

        verify(service, times(1)).getAll();
    }

    @Test
    void testGetById_Found() throws Exception {
        when(service.getById(1)).thenReturn(Optional.of(objectList.get(0)));

        mockMvc.perform(get("/polaznik/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Marko"));

        verify(service, times(1)).getById(1);
    }



    @Test
    void testGetById_NotFound() throws Exception {
        when(service.getById(12345)).thenReturn(Optional.empty());

        mockMvc.perform(get("/polaznik/{id}", 12345)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getById(12345);
    }



    @Test
    void testSave() throws Exception {
        PolaznikDTO object3 = new PolaznikDTO("Ivan", "Ivić");
        objectList.add(object3);

        when(service.save(any(PolaznikDTO.class))).thenReturn(object3);

        mockMvc.perform(post("/polaznik/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "firstName": "Ivan",
                    "lastName": "Ivić"
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(service, times(1)).save(any(PolaznikDTO.class));
    }


    @Test
    void testUpdate_Success() throws Exception {
        PolaznikDTO objectToUpdate = new PolaznikDTO("Ivan Update", "Ivić");

        when(service.exists(anyInt())).thenReturn(true);
        when(service.update(any(PolaznikDTO.class), anyInt())).thenReturn(Optional.of(objectToUpdate));

        mockMvc.perform(put("/polaznik/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "firstName": "Ivan Update",
                    "lastName": "Ivić"
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivan Update"));


        verify(service, times(1)).exists(anyInt());
        verify(service, times(1)).update(any(PolaznikDTO.class), anyInt());
    }



    @Test
    void testUpdate_NotFound() throws Exception {
        PolaznikDTO objectToUpdate = new PolaznikDTO("Ivan Update", "Ivić");

        when(service.exists(anyInt())).thenReturn(false);
        when(service.update(any(PolaznikDTO.class), anyInt())).thenReturn(Optional.of(objectToUpdate));

        mockMvc.perform(put("/polaznik/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "firstName": "Ivan Update",
                    "lastName": "Ivić"
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        verify(service, times(1)).exists(anyInt());
        verify(service, never()).update(any(PolaznikDTO.class), anyInt());
    }



    @Test
    void testDelete_Success() throws Exception {
        when(service.exists(anyInt())).thenReturn(true);
        when(service.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/polaznik/{id}", 3))
                .andExpect(status().isOk());


        verify(service, times(1)).exists(anyInt());
        verify(service, times(1)).delete(anyInt());
    }



    @Test
    void testDelete_NotFound() throws Exception {
        when(service.exists(anyInt())).thenReturn(false);
        when(service.delete(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/polaznik/{id}", 3))
                .andExpect(status().isNotFound());


        verify(service, times(1)).exists(anyInt());
        verify(service, never()).delete(anyInt());
    }


}