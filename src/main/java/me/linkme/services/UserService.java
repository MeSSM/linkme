package me.linkme.services;

import lombok.RequiredArgsConstructor;
import me.linkme.models.DTO.UserDTO;
import me.linkme.models.Roles;
import me.linkme.models.User;
import me.linkme.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public User registerNewUser(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRoles(Stream.of(new Roles[]{Roles.ADMIN}).collect(Collectors.toList()));
        return this.repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.repository.findById(username);
        return user.orElse(null);
    }


    public Optional<User> getLoggedUser(){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User){
            return  Optional.of((User) user);
        }else{
            return Optional.empty();
        }
    }


}
