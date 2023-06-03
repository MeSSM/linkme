package me.linkme.repositories;


import me.linkme.models.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<Link, Integer> {

}
