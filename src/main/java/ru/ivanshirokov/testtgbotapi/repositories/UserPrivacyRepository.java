package ru.ivanshirokov.testtgbotapi.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.ivanshirokov.testtgbotapi.models.UserPrivacy;

import java.util.List;

public interface UserPrivacyRepository extends ListCrudRepository<UserPrivacy, Integer> {
    public List<UserPrivacy> findAllById(long id);
}
