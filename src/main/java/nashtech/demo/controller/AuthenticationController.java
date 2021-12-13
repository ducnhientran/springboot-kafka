package nashtech.demo.controller;

import lombok.RequiredArgsConstructor;
import nashtech.demo.model.request.authentication.SignUp;
import nashtech.demo.model.request.authentication.UserLogin;
import nashtech.demo.model.response.authentication.LoginDto;
import nashtech.demo.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserLogin login) {
        return ResponseEntity.ok(authenticationService.login(login));
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUp signup) {
        return ResponseEntity.ok(authenticationService.signUp(signup));
    }
}
