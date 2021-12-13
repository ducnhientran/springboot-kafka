package nashtech.demo.configuration.sercurity.userservice;

import nashtech.demo.entity.Role;
import nashtech.demo.entity.User;
import nashtech.demo.entity.UserRole;
import nashtech.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsernameAndDeletedIsFalse(userName)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(userName, user.getPassword(), getGrantedAuthorityByRoles(user.getUserRoles()));
    }

    private Set<GrantedAuthority> getGrantedAuthorityByRoles(Set<UserRole> userRoles) {
        if(CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptySet();
        }
        Set<GrantedAuthority> grantedAuthoritiesSet = new HashSet<>();
        userRoles.parallelStream().forEach(userRole -> {
            Role role = userRole.getRole();
            if(!ObjectUtils.isEmpty(role)){
                grantedAuthoritiesSet.add(new SimpleGrantedAuthority(role.getName()));
            }
        });
        return grantedAuthoritiesSet;
    }

}
