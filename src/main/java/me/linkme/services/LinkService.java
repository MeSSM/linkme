package me.linkme.services;

import lombok.RequiredArgsConstructor;
import me.linkme.models.Link;
import me.linkme.models.Sequence;
import me.linkme.repositories.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final SequenceService sequenceService;
    private final UserService userService;


    public Iterable<Link> findAll(){
        return linkRepository.findAll();
    }

    public Link generateLink(String url){
        Sequence seq = sequenceService.yield();
        Link link = new Link();
        link.setUrl(url);
        link.setShortener(seq.getSequence());
        link.setCreated_by(userService.getLoggedUser().orElse(null));
        return linkRepository.save(link);
    }


    public Optional<Link> findByShortener(int shortener){
        return linkRepository.findById(shortener);
    }

    public Optional<Link> deleteById(int id){
        Optional<Link> link = linkRepository.findById(id);
        linkRepository.deleteById(id);
        return link;
    }

}
