package com.example.sweatter.repos;

import com.example.sweatter.domain.Message;
import com.example.sweatter.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {

    Page<Message> findAll(Pageable pageable);

    Page<Message> findByAuthor (User user, Pageable pageable);

    Page<Message> findByTag(String tag, Pageable pageable);
}