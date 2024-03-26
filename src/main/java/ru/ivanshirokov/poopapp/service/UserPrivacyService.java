package ru.ivanshirokov.poopapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ivanshirokov.poopapp.exception.InvalidDataInDataBaseProducedByCode;
import ru.ivanshirokov.poopapp.model.UserPrivacy;
import ru.ivanshirokov.poopapp.repository.UserPrivacyRepository;
import ru.ivanshirokov.poopapp.util.cache.ModelCache;

import java.util.List;

@Service
public class UserPrivacyService {

    private final Logger logger = LoggerFactory.getLogger(UserPrivacyService.class);

    private final UserPrivacyRepository userPrivacyRepository;
    private final ModelCache<UserPrivacy> userPrivacyCache;

    public UserPrivacyService(UserPrivacyRepository userPrivacyRepository,
                              ModelCache<UserPrivacy> userPrivacyCache) {
        this.userPrivacyRepository = userPrivacyRepository;
        this.userPrivacyCache = userPrivacyCache;
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
        throw new InvalidDataInDataBaseProducedByCode();
    }

    public void save(UserPrivacy userPrivacy) {
        userPrivacyRepository.save(userPrivacy);
        userPrivacyCache.addToCache(userPrivacy);
    }

    public void readLanguageGmtFromDB(UserPrivacy userPrivacy){
        UserPrivacy userPrivacyFromDB;
        if (!userPrivacyCache.isCached(userPrivacy.getId())) {
            userPrivacyFromDB = userPrivacyRepository.findAllById(userPrivacy.getId()).getFirst();
            userPrivacyCache.addToCache(userPrivacy);
        } else {
            userPrivacyFromDB = userPrivacyCache.getFromCache(userPrivacy.getId());
        }
        userPrivacy.setGmt(userPrivacyFromDB.getGmt());
        userPrivacy.setLanguageCode(userPrivacyFromDB.getLanguageCode());
    }

    public void update(UserPrivacy userPrivacy) {
        save(userPrivacy);
    }

}
