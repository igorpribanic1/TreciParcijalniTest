package org.example.treciparcijalnitest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.treciparcijalnitest.dto.AuthRequestDTO;
import org.example.treciparcijalnitest.dto.JwtResponseDTO;
import org.example.treciparcijalnitest.dto.PolaznikDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class PolaznikControllerIntegrationTest {

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
        mockMvc.perform(get("/polaznik")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(5)))
                .andExpect(jsonPath("$[0].firstName", is("Pero")));
    }


    @Test
    void testGetById_Found() throws Exception {
       mockMvc.perform(get("/polaznik/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Pero"));
    }


    @Test
    void testGetById_NotFound() throws Exception {
        mockMvc.perform(get("/polaznik/{id}", 12345)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void testSave() throws Exception {
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
    }


    @Test
    void testUpdate_Success() throws Exception {
        mockMvc.perform(put("/polaznik/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "firstName": "Update",
                    "lastName": "Korisnik"
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Update"));
    }


    @Test
    void testUpdate_NotFound() throws Exception {
        mockMvc.perform(put("/polaznik/{id}", 12345)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "firstName": "Update",
                    "lastName": "Korisnik"
                }
            """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void testDelete_Success() throws Exception {
        mockMvc.perform(delete("/polaznik/{id}", 3))
                .andExpect(status().isOk());
    }



    @Test
    void testDelete_NotFound() throws Exception {
       mockMvc.perform(delete("/polaznik/{id}", 12345))
                .andExpect(status().isNotFound());
    }


}
