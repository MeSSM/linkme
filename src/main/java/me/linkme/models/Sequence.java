package me.linkme.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Sequence")
@Getter @Setter
public class Sequence {
    @Id
    private String type;
    private int sequence;

    public Sequence() {
        this.sequence = 0;
    }
}
