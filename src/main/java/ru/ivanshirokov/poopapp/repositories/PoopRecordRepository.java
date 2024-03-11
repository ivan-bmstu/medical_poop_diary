package ru.ivanshirokov.poopapp.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.ivanshirokov.poopapp.models.PoopRecord;

import java.util.List;

@Repository
public interface PoopRecordRepository extends ListPagingAndSortingRepository<PoopRecord, Integer>, CrudRepository<PoopRecord, Integer> {

    long countByUserPrivacyId(long userPrivacyId);

    List<PoopRecord> findAllByUserPrivacyId(long userPrivacyId, Pageable pageable);
}
