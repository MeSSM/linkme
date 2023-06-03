package me.linkme.services;



import lombok.RequiredArgsConstructor;
import me.linkme.models.Sequence;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SequenceService {
    private final static String LINK_SEQUENCE = "LINK_SEQUENCE";
    private final RedisKeyValueTemplate template;


    public Sequence init(){
        Sequence sequence = new Sequence();
        sequence.setType(LINK_SEQUENCE);
        return template.insert(sequence);
    }

    public Sequence peek(){
        Optional<Sequence> sequence = template.findById(LINK_SEQUENCE, Sequence.class);
        return sequence.orElseGet(this::init);
    }

    public Sequence yield(){
        Sequence current = peek();
        current.setSequence(current.getSequence()+1);
        template.update(current);
        return current;
    }




}
