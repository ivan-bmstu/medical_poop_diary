package ru.ivanshirokov.poopapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import ru.ivanshirokov.poopapp.model.UserPrivacy;

import java.util.List;

public interface UserPrivacyRepository extends ListCrudRepository<UserPrivacy, Integer> {

    @Query("SELECT up FROM UserPrivacy up WHERE up.id = :id")
    List<UserPrivacy> findAllById(long id);

}
