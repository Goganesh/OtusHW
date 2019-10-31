package ru.otus.homework.api.dao;

import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.PhoneDataSet;

public interface PhoneDataSetDao {
    PhoneDataSet findById(long id);

    long savePhoneDataSet(PhoneDataSet phoneDataSet);

    void updatePhoneDataSet(PhoneDataSet phoneDataSet);

    void deletePhoneDataSet(PhoneDataSet phoneDataSet);

    SessionManager getSessionManager();
}
