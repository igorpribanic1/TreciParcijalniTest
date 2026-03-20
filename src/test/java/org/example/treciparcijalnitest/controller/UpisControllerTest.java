package org.example.treciparcijalnitest.controller;

import org.example.treciparcijalnitest.dto.UpisDTO;
import org.example.treciparcijalnitest.service.UpisService;
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

public class UpisControllerTest {
    @Mock
    private UpisService service;

    @InjectMocks
    private UpisController controller;

    private MockMvc mockMvc;

    private List<UpisDTO> objectList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        UpisDTO object1 = new UpisDTO(1, 1);
        UpisDTO object2 = new UpisDTO(2, 2);
        objectList.add(object1);
        objectList.add(object2);
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(objectList);

        mockMvc.perform(get("/upis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].programId", is(1)))
                .andExpect(jsonPath("$[1].programId", is(2)));

        verify(service, times(1)).getAll();
    }

    @Test
    void testGetById_Found() throws Exception {
        when(service.getById(1)).thenReturn(Optional.of(objectList.get(0)));

        mockMvc.perform(get("/upis/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.programId").value(1));

        verify(service, times(1)).getById(1);
    }



    @Test
    void testGetById_NotFound() throws Exception {
        when(service.getById(12345)).thenReturn(Optional.empty());

        mockMvc.perform(get("/upis/{id}", 12345)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getById(12345);
    }



    @Test
    void testSave() throws Exception {
        UpisDTO object3 = new UpisDTO(3, 3);
        objectList.add(object3);

        when(service.save(any(UpisDTO.class))).thenReturn(object3);

        mockMvc.perform(post("/upis/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "programId": 3,
                    "studentId": 3
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(service, times(1)).save(any(UpisDTO.class));
    }


    @Test
    void testUpdate_Success() throws Exception {
        UpisDTO objectToUpdate = new UpisDTO(3, 4);

        when(service.exists(anyInt())).thenReturn(true);
        when(service.update(any(UpisDTO.class), anyInt())).thenReturn(Optional.of(objectToUpdate));

        mockMvc.perform(put("/upis/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "programId": 3,
                    "studentId": 4
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.programId").value(3));


        verify(service, times(1)).exists(anyInt());
        verify(service, times(1)).update(any(UpisDTO.class), anyInt());
    }



    @Test
    void testUpdate_NotFound() throws Exception {
        UpisDTO objectToUpdate = new UpisDTO(3, 3);

        when(service.exists(anyInt())).thenReturn(false);
        when(service.update(any(UpisDTO.class), anyInt())).thenReturn(Optional.of(objectToUpdate));

        mockMvc.perform(put("/upis/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "programId": 3,
                    "studentId": 3
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        verify(service, times(1)).exists(anyInt());
        verify(service, never()).update(any(UpisDTO.class), anyInt());
    }



    @Test
    void testDelete_Success() throws Exception {
        when(service.exists(anyInt())).thenReturn(true);
        when(service.delete(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/upis/{id}", 3))
                .andExpect(status().isOk());


        verify(service, times(1)).exists(anyInt());
        verify(service, times(1)).delete(anyInt());
    }



    @Test
    void testDelete_NotFound() throws Exception {
        when(service.exists(anyInt())).thenReturn(false);
        when(service.delete(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/upis/{id}", 3))
                .andExpect(status().isNotFound());


        verify(service, times(1)).exists(anyInt());
        verify(service, never()).delete(anyInt());
    }
}
