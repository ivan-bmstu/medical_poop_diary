package ru.ivanshirokov.poopapp.util.cache;

import org.springframework.stereotype.Component;
import ru.ivanshirokov.poopapp.model.UserPrivacy;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class UserPrivacyCache implements ModelCache<UserPrivacy>{

    private final ReadWriteLock readWriteLock;
    private final int SECONDS_TO_CACHED = 30 * 60;
    private final Map<Long, UserPrivacyTimed> cachedUsersPrivacy;

    public UserPrivacyCache() {
        readWriteLock = new ReentrantReadWriteLock();
        cachedUsersPrivacy = new HashMap<>(); // TODO возможно тут лучше использовать TreeHashMap и при очистке если не удаляется, то последющие нет смысла удалять
        ScheduledExecutorService es1 = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
        es1.scheduleWithFixedDelay(this::clearCache, SECONDS_TO_CACHED, SECONDS_TO_CACHED, TimeUnit.SECONDS);
    }

    @Override
    public boolean isCached(long userPrivacyId) {
        readWriteLock.readLock().lock();
        boolean result = cachedUsersPrivacy.containsKey(userPrivacyId);
        readWriteLock.readLock().unlock();
        return result;
    }

    @Override
    public void addToCache(UserPrivacy userPrivacy) {
        readWriteLock.readLock().lock();
        UserPrivacyTimed userPrivacyTimed = new UserPrivacyTimed(userPrivacy);
        cachedUsersPrivacy.put(userPrivacy.getId(), userPrivacyTimed);
        readWriteLock.readLock().unlock();
    }

    @Override
    public void clearCache() {
        readWriteLock.writeLock().lock();
        cachedUsersPrivacy.entrySet()
                .removeIf(e ->
                (Instant.now().getEpochSecond() - e.getValue().atTimeAddedToCache) >= SECONDS_TO_CACHED);
        readWriteLock.writeLock().unlock();
    }

    @Override
    public UserPrivacy getFromCache(long id) {
        readWriteLock.readLock().lock();
        UserPrivacy userPrivacy = cachedUsersPrivacy.get(id).userPrivacy;
        readWriteLock.readLock().unlock();
        return userPrivacy;
    }

    private class UserPrivacyTimed {
        UserPrivacy userPrivacy;
        long atTimeAddedToCache;


        public UserPrivacyTimed(UserPrivacy userPrivacy) {
            this.userPrivacy = userPrivacy;
            this.atTimeAddedToCache = Instant.now().getEpochSecond();
        }
    }
}
