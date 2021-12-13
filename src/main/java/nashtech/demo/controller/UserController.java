package nashtech.demo.controller;

import lombok.RequiredArgsConstructor;
import nashtech.demo.model.response.user.ProfileDto;
import nashtech.demo.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AuthenticationService authenticationService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile(){
        return ResponseEntity.ok(authenticationService.getProfileUser());
    }

}
