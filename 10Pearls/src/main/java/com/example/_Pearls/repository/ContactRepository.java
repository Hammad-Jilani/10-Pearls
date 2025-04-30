package com.example._Pearls.repository;

import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findByNameContainingAndUserId(String partialName, Long user);
    List<Contact> findAllByUserId(Long userId);
    Page<Contact> findByUserId(Long userId, Pageable pageable);
    Contact deleteByUserId(Long userId);
    Optional<Contact> findByContactIdAndUserId(Long contactId, Long userId);
}
