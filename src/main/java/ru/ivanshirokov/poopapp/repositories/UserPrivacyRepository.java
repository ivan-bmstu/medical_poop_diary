package ru.ivanshirokov.poopapp.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.ivanshirokov.poopapp.models.UserPrivacy;

import java.util.List;

public interface UserPrivacyRepository extends ListCrudRepository<UserPrivacy, Integer> {
    List<UserPrivacy> findAllById(long id);
}
