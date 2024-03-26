package ru.ivanshirokov.poopapp.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ivanshirokov.poopapp.exception.UserPrivacyNotFoundException;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.model.UserPrivacy;
import ru.ivanshirokov.poopapp.repository.PoopRecordRepository;
import ru.ivanshirokov.poopapp.repository.UserPrivacyRepository;
import ru.ivanshirokov.poopapp.util.cache.ModelCache;

import java.util.List;

@Service
public class PoopRecordService {

    private final PoopRecordRepository poopRecordRepository;
    private final UserPrivacyRepository userPrivacyRepository;
    private final ModelCache<UserPrivacy> userPrivacyCache;

    public PoopRecordService(PoopRecordRepository poopRecordRepository,
                             UserPrivacyRepository userPrivacyRepository,
                             ModelCache<UserPrivacy> userPrivacyCache) {
        this.poopRecordRepository = poopRecordRepository;
        this.userPrivacyRepository = userPrivacyRepository;
        this.userPrivacyCache = userPrivacyCache;
    }

    public void save(PoopRecord poopRecord){
        poopRecordRepository.save(poopRecord);
    }

    public long getCountByUser(UserPrivacy userPrivacy){
        return poopRecordRepository.countByUserPrivacyId(userPrivacy.getId());
    }

    public List<PoopRecord> getPoopRecords(long userPrivacyId, int page){
        Pageable sortPageByDate = PageRequest.of(page, 5, Sort.by("dateTime").reverse());

        UserPrivacy user;
        if (userPrivacyCache.isCached(userPrivacyId)) {
            user = userPrivacyCache.getFromCache(userPrivacyId);
        }
        else {
            user = userPrivacyRepository.findById((int) userPrivacyId) // TODO мб стоит переделать id юзера в модели на int
                    .orElseThrow(UserPrivacyNotFoundException::new);
            userPrivacyCache.addToCache(user);
        }

        List<PoopRecord> records = poopRecordRepository.findAllByUserPrivacyId(userPrivacyId, sortPageByDate);
        records.forEach(rec -> {
            rec.setUserPrivacy(user);
        });
        return records;
    }
}
