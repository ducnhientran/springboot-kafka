//package nashtech.demo.configuration.sercurity.userservice;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import nashtech.demo.entity.Role;
//import nashtech.demo.entity.User;
//import nashtech.demo.entity.UserRole;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.ObjectUtils;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserDetailsImpl  implements UserDetails {
//
//    private Long id;
//
//    private String username;
//
//    private String email;
//
//    private String password;
//
//    private String status;
//
//    private Set<Role> roles = new HashSet<>();
//
//    private Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
//    public UserDetailsImpl(User user){
//        super();
//        this.id = user.getId();
//        this.username = user.getUsername();
//        this.password = user.getPassword();
//        this.email = user.getEmail();
//        this.status = user.getStatus();
//        this.grantedAuthorities = getGrantedAuthorityByRoles(user.getUserRoles());
//
//    }
//
//    private Set<GrantedAuthority> getGrantedAuthorityByRoles(Set<UserRole> userRoles) {
//        if(CollectionUtils.isEmpty(userRoles)) {
//            return Collections.emptySet();
//        }
//        Set<GrantedAuthority> grantedAuthoritiesSet = new HashSet<>();
//        userRoles.parallelStream().forEach(userRole -> {
//            Role role = userRole.getRole();
//            if(!ObjectUtils.isEmpty(role)){
//                grantedAuthoritiesSet.add(new SimpleGrantedAuthority(role.getName()));
//            }
//        });
//        return grantedAuthoritiesSet;
//    }
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.grantedAuthorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
