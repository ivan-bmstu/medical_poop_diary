package ru.ivanshirokov.poopapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class AdminUser {

    @Id
    private int id;

    @OneToOne
    private UserPrivacy userPrivacy;
}
