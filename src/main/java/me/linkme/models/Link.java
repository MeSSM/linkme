package me.linkme.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("Link")
@Getter @Setter
public class Link {

    @Id
    public int shortener;
    public String url;
    public LocalDateTime generation_date;
    public User created_by;

    public Link() {
        this.generation_date = LocalDateTime.now();
        this.created_by = null;
    }
}
