package org.example.treciparcijalnitest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.treciparcijalnitest.dto.AuthRequestDTO;
import org.example.treciparcijalnitest.dto.JwtResponseDTO;
import org.example.treciparcijalnitest.dto.PolaznikDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class ProgramObrazovanjaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    private String accessToken;

    @Autowired
    private ObjectMapper objectMapper;



    @BeforeEach
    void setUp() {
        AuthRequestDTO authRequest = new AuthRequestDTO();
        authRequest.setUsername("admin");
        authRequest.setPassword("admin");

        if(Optional.ofNullable(accessToken).isEmpty()) {
            JwtResponseDTO jwtResponse = authController.authenticateAndGetToken(authRequest);
            accessToken = jwtResponse.getAccessToken();
        }
    }


    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/program-obrazovanja")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(5)))
                .andExpect(jsonPath("$[0].name", is("Prvi program obrazovanja")));
    }


    @Test
    void testGetById_Found() throws Exception {
        mockMvc.perform(get("/program-obrazovanja/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Prvi program obrazovanja"));
    }


    @Test
    void testGetById_NotFound() throws Exception {
        mockMvc.perform(get("/program-obrazovanja/{id}", 12345)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void testSave() throws Exception {
        mockMvc.perform(post("/program-obrazovanja/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Novi program obrazovanja",
                    "csvet": 12
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testUpdate_Success() throws Exception {
        mockMvc.perform(put("/program-obrazovanja/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Updated program obrazovanja",
                    "csvet": 12
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated program obrazovanja"));
    }


    @Test
    void testUpdate_NotFound() throws Exception {
        mockMvc.perform(put("/program-obrazovanja/{id}", 12345)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Updated program obrazovanja",
                    "csvet": 12
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void testDelete_Success() throws Exception {
        mockMvc.perform(delete("/program-obrazovanja/{id}", 3))
                .andExpect(status().isOk());
    }



    @Test
    void testDelete_NotFound() throws Exception {
        mockMvc.perform(delete("/program-obrazovanja/{id}", 12345))
                .andExpect(status().isNotFound());
    }

}
