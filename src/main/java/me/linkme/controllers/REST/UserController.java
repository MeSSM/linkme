package me.linkme.controllers.REST;


import lombok.RequiredArgsConstructor;
import me.linkme.models.User;
import me.linkme.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class UserController {
    final private UserRepository userRepository;

    @GetMapping("users")
    public Iterable<User> all(){
        return this.userRepository.findAll();
    }


}