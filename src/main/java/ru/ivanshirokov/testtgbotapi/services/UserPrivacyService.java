package ru.ivanshirokov.testtgbotapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ivanshirokov.testtgbotapi.models.UserPrivacy;
import ru.ivanshirokov.testtgbotapi.repositories.UserPrivacyRepository;

@Service
public class UserPrivacyService {

    private final Logger logger = LoggerFactory.getLogger(UserPrivacyService.class);

    private final UserPrivacyRepository userPrivacyRepository;

    public UserPrivacyService(UserPrivacyRepository userPrivacyRepository) {
        this.userPrivacyRepository = userPrivacyRepository;
    }

    public boolean isPresent(UserPrivacy userPrivacy){
        var user = userPrivacyRepository.findAllById(userPrivacy.getId());

        if (user.isEmpty()){
            return false;
        }
        if (user.size() == 1){
            return true;
        }
        logger.error("DB have duplicate user: {}", userPrivacy);
        throw  new RuntimeException();
    }

    public void save(UserPrivacy userPrivacy){
        userPrivacyRepository.save(userPrivacy);
    }
}
