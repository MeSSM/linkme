package me.linkme.controllers.MVC;

import lombok.RequiredArgsConstructor;
import me.linkme.models.DTO.UserDTO;
import me.linkme.models.Link;
import me.linkme.models.User;
import me.linkme.services.LinkService;
import me.linkme.services.SequenceService;
import me.linkme.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Pattern;


@Controller
@RequiredArgsConstructor
@RequestMapping(value="/")
public class HomeController {
    private final static String REGEX = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$";
    final SequenceService sequenceService;
    final UserService userService;
    final LinkService linkService;


    @GetMapping("/")
    public String home(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("register_form", userDTO);
        model.addAttribute("user", userService.getLoggedUser().orElse(null));
        return "index";
    }

    @GetMapping("/{id}")
    public String redirectToShortenedUrl(@PathVariable int id, HttpServletResponse response){
        Optional<Link> link = linkService.findByShortener(id);
        if (link.isPresent()){
            response.setHeader("Location", link.get().url);
            response.setStatus(302);
        }else{
            response.setStatus(404);
        }
        return "error";
    }


    @GetMapping("/register")
    public String register(){
        return "redirect:/";
    }

    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("register_form")UserDTO userDto, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "index";
        }
        if (userService.loadUserByUsername(userDto.getEmail()) != null){
            model.addAttribute("user_exists", userDto.getEmail());
            return "index";
        }

        User user = userService.registerNewUser(userDto);
        model.addAttribute("success", user.getEmail());
        return "index";
    }

    @PostMapping("/shorten-link")
    public RedirectView addLink(@RequestParam String url){
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        if (!url.startsWith("http")){
            url = "http://" + url;
        }
        if (!Pattern.compile(REGEX).matcher(url).matches()){
            rv.setUrl("/?wrong_link=" + url);
            return rv;
        }
        Link link = this.linkService.generateLink(url);
        rv.setUrl("/generated-link/" + link.shortener);
        return rv;
    }


    @GetMapping("/generated-link/{id}")
    public String generated_link(@PathVariable int id, Model model, HttpServletRequest request){
        model.addAttribute("user", userService.getLoggedUser().orElse(null));
        Optional<Link> link = this.linkService.findByShortener(id);
        if (!link.isPresent()){
            return "error";
        }
        String url = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443){
            url += ":" + request.getServerPort();
        }
        url +=  "/" + id;
        model.addAttribute("link", link.get());
        model.addAttribute("url", url);
        return "generatedLink";
    }

}

