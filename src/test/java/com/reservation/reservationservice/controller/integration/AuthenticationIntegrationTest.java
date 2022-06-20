package com.reservation.reservationservice.controller.integration;

import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.reservationservice.config.security.JwtUtils;
import com.reservation.reservationservice.model.ConfirmationToken;
import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.model.Role;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.payload.RegisterPayload;
import com.reservation.reservationservice.repository.ConfirmationTokenRepository;
import com.reservation.reservationservice.repository.RefreshTokenRepository;
import com.reservation.reservationservice.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuthenticationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${reservation.app.jwtSecret}")
    private String secretKey;

    @Autowired
    RoleRepository mockRoleRepository;

    @Autowired
    RefreshTokenRepository mockRefreshTokenRepository;

    @Autowired
    ConfirmationTokenRepository mockConfirmationTokenRepository;

//    @Autowired
//    private AuthenticationController authenticationController;
//
//    @MockBean
//    private AuthenticationService authenticationService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private ModelMapper modelMapper;

    private String jwtToken;
    private User user;
    private Role role;
    private ERole eRole;
    private ConfirmationToken confirmationToken;
    private RegisterPayload registerPayload;

    @BeforeEach
    void setUp() {
//        user = new User();
//        user.setId(UUID.randomUUID().toString());
//        user.setEmail("test@gmail.com");
//        user.setPassword("password");
//        user.setEnabled(false);
//        mockUserRepository.save(user);
        registerPayload = new RegisterPayload();
        registerPayload.setEmail("test1@gmail.com");
        registerPayload.setPassword("password");
        registerPayload.setRole("USER");

        eRole =  ERole.ROLE_USER;
        role = new Role();
        role.setId("1L");
        role.setRole(eRole);
        mockRoleRepository.save(role);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void registerUser_should_register_user_and_return_response_message_when_valid_user_credentials() throws Exception {
        RequestBuilder builder = post("/api/v1/public/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerPayload));
        mockMvc.perform(builder).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"User registered Successfully!\"}"));
    }

//    @Test
//    public void testRegisterUser() throws Exception {
//        doNothing().when(this.authenticationService).register((RegisterPayload) any());
//
//        RegisterPayload registerPayload = new RegisterPayload();
//        registerPayload.setEmail("jane.doe@example.org");
//        registerPayload.setPassword("iloveyou");
//        registerPayload.setRole("Role");
//        String content = (new ObjectMapper()).writeValueAsString(registerPayload);
//        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/public/auth/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authenticationController)
//                .build()
//                .perform(requestBuilder);
//        actualPerformResult.andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"User registered Successfully!\"}"));
//    }
}

