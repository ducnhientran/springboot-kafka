package nashtech.demo.service;

import nashtech.demo.model.request.authentication.SignUp;
import nashtech.demo.model.request.authentication.UserLogin;
import nashtech.demo.model.response.authentication.LoginDto;
import nashtech.demo.model.response.user.ProfileDto;

public interface AuthenticationService {

    LoginDto login(UserLogin login);

    ProfileDto getProfileUser();

    String signUp(SignUp signup);
}
