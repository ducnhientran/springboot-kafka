package nashtech.demo.service.impl;

import lombok.RequiredArgsConstructor;
import nashtech.demo.configuration.sercurity.jwt.JwtUtils;
import nashtech.demo.constant.Messages;
import nashtech.demo.entity.Role;
import nashtech.demo.entity.User;
import nashtech.demo.entity.UserRole;
import nashtech.demo.exception.BadRequestException;
import nashtech.demo.model.request.authentication.SignUp;
import nashtech.demo.model.request.authentication.UserLogin;
import nashtech.demo.model.response.authentication.LoginDto;
import nashtech.demo.model.response.user.ProfileDto;
import nashtech.demo.repository.RoleRepository;
import nashtech.demo.repository.UserRepository;
import nashtech.demo.repository.UserRoleRepository;
import nashtech.demo.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Override
    public LoginDto login(UserLogin login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            return LoginDto.builder()
                    .token(jwtUtils.generateToken((org.springframework.security.core.userdetails.User) authentication.getPrincipal()))
                    .build();
        } catch (Exception e){
            throw new BadRequestException(Messages.UNAUTHORIZED.getMessage());
        }
    }

    @Override
    public ProfileDto getProfileUser() {
         org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         User user = userRepository.findFirstByUsernameAndDeletedIsFalse(userDetails.getUsername()).orElse(null);
        if (!ObjectUtils.isEmpty(user)) {
            return ProfileDto.builder()
                    .userName(user.getUsername())
                    .email(user.getEmail())
                    .status(user.getStatus())
                    .build();
        }
       throw new BadRequestException("User not found");
    }

    @Override
    public String signUp(SignUp signup) {
        boolean existsByUsername = userRepository.existsByUsername(signup.getUsername());
        if(existsByUsername){
            throw new BadRequestException("Username is already taken!");
        }
        boolean existsByEmail = userRepository.existsByEmail(signup.getEmail());
        if(existsByEmail){
            throw new BadRequestException("Email is already taken!");
        }
        User user = User.builder()
            .username(signup.getUsername())
            .email(signup.getEmail())
            .status("ACTIVE")
            .password(passwordEncoder.encode(signup.getPassword()))
            .build();
        user = userRepository.save(user);
        Role role = roleRepository.findFirstByNameAndDeletedIsFalse("USER");
        UserRole userRole = UserRole.builder()
            .role(role)
            .user(user)
            .build();
        userRoleRepository.save(userRole);
        return Messages.SUCCESSFUL.getMessage();
    }
}
