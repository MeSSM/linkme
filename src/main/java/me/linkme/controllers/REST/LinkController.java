package me.linkme.controllers.REST;



import lombok.RequiredArgsConstructor;
import me.linkme.models.Link;
import me.linkme.services.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class LinkController {
    final private LinkService linkService;

    @GetMapping("links")
    public Iterable<Link> all(){
        return this.linkService.findAll();
    }

    @PostMapping("link")
    public Link add(@RequestBody String url){
        return this.linkService.generateLink(url);
    }

    @GetMapping("link/{id}")
    public Link getById(@PathVariable int id){
        return this.linkService.findByShortener(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Not Found !"));
    }

    @DeleteMapping("link/{id}")
    public Link delete(@PathVariable int id){
        return linkService.deleteById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Not Found !"));
    }


}
