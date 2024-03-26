package ru.ivanshirokov.poopapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ivanshirokov.poopapp.model.UserPrivacy;
import ru.ivanshirokov.poopapp.repository.AdminUserRepository;

import java.util.List;

@Service
public class AdminUserService {

    private final Logger logger = LoggerFactory.getLogger(AdminUserService.class);

    private final AdminUserRepository adminUserRepository;

    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public List<UserPrivacy> getAdmins() {
        return adminUserRepository.findAllAdmins();
    }
}
