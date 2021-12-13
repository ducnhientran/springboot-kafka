package nashtech.demo.controller;

import nashtech.demo.constant.Messages;
import nashtech.demo.model.request.authentication.SignUp;
import nashtech.demo.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AuthenticationService authenticationService;

    @Test
    void testLoginPassed() throws Exception {
        String username = "user";
        String password = "user";
        String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}" ;
        mvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(body) )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testLoginFailed() throws Exception {
        mvc.perform(
                post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSignUpPassed() throws Exception {
        String username = "testSignup";
        String password = "testSignup";
        String email  = "testSignup@gmail.com";
        String body = "{\"username\":\"" + username + "\", \"email\" : \" "+ email +"\" ,  \"password\":\"" + password + "\"}" ;
        SignUp signUp = SignUp.builder().email(email).password(password).username(username).build();
        Mockito.when(authenticationService.signUp(signUp)).thenReturn(Messages.SUCCESSFUL.getMessage());
        mvc.perform(
                post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body) )
                .andExpect(status().isOk())
                .andReturn();
    }

}
