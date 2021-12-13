package nashtech.demo.service;


import nashtech.demo.configuration.sercurity.jwt.JwtUtils;
import nashtech.demo.constant.Messages;
import nashtech.demo.entity.Role;
import nashtech.demo.entity.User;
import nashtech.demo.entity.UserRole;
import nashtech.demo.exception.BadRequestException;
import nashtech.demo.model.request.authentication.SignUp;
import nashtech.demo.model.request.authentication.UserLogin;
import nashtech.demo.model.response.authentication.LoginDto;
import nashtech.demo.repository.RoleRepository;
import nashtech.demo.repository.UserRepository;
import nashtech.demo.repository.UserRoleRepository;
import nashtech.demo.service.impl.AuthenticationServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRoleRepository userRoleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Test
    void testSignUpPassed(){
        String username = "testSignup";
        String password = "testSignup";
        String email  = "testSignup@gmail.com";
        SignUp signUp = SignUp.builder().email(email).password(password).username(username).build();

        Mockito.when(userRepository.existsByUsername(username)).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(false);
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        User user = User.builder().build();

        user = userRepository.save(user);
        Role role = Role.builder().build();
        Mockito.when(roleRepository.findFirstByNameAndDeletedIsFalse("USER")).thenReturn(role);
        UserRole userRole = UserRole.builder()
                .role(role)
                .user(user)
                .build();
        userRoleRepository.save(userRole);

        String result = authenticationService.signUp(signUp);
        Assert.assertEquals(Messages.SUCCESSFUL.getMessage(), result);
    }

    @Test
    void testSignUpUsernameFailed(){
        String username = "testSignup";
        String password = "testSignup";
        String email  = "testSignup@gmail.com";
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(true);
        SignUp signUp = SignUp.builder().email(email).password(password).username(username).build();
        Assertions.assertThrows(BadRequestException.class, () -> {
            authenticationService.signUp(signUp);
        }, "Username is already taken!");
    }

    @Test
    void testSignUpEmailFailed(){
        String username = "testSignup";
        String password = "testSignup";
        String email  = "testSignup@gmail.com";
        SignUp signUp = SignUp.builder().email(email).password(password).username(username).build();
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(true);
        Assertions.assertThrows(BadRequestException.class, () -> {
            authenticationService.signUp(signUp);
        }, "Email is already taken!");
    }


    @Test
    void testLoginFailed(){
        String username = "testSignup";
        String password = "testSignup";
        UserLogin login = UserLogin.builder().password(password).username(username).build();
        Mockito.when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))).thenThrow(new BadRequestException(Messages.UNAUTHORIZED.getMessage()));
        Assertions.assertThrows(BadRequestException.class, () -> {
            authenticationService.login(login);
        }, "UNAUTHORIZED");
    }

    @Test
    void testLoginPassed(){
        String username = "user";
        String password = "user";
        UserLogin login = UserLogin.builder().username(username).password(password).build();
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))).thenReturn(authentication);
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(username, password, Collections.EMPTY_SET);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        LoginDto loginDto = LoginDto.builder()
                .token(jwtUtils.generateToken(userDetails))
                .build();

        Assert.assertEquals(loginDto, authenticationService.login(login));
    }
}
