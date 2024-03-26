package ru.ivanshirokov.poopapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ivanshirokov.poopapp.model.AdminUser;
import ru.ivanshirokov.poopapp.model.UserPrivacy;

import java.util.List;

public interface AdminUserRepository extends CrudRepository<AdminUser, Integer> {

    @Query("SELECT au.userPrivacy FROM AdminUser au")
    List<UserPrivacy> findAllAdmins();
}
