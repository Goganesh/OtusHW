package ru.otus.homework.api.dao;

import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.AddressDataSet;

public interface AddressDataSetDao {

    AddressDataSet findById(long id);

    long saveAddressDataSet(AddressDataSet addressDataSet);

    void updateAddressDataSet(AddressDataSet addressDataSet);

    void deleteAddressDataSet(AddressDataSet addressDataSet);

    SessionManager getSessionManager();
}
