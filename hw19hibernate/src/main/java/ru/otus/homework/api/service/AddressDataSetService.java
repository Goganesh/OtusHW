package ru.otus.homework.api.service;


import ru.otus.homework.model.AddressDataSet;

public interface AddressDataSetService {
    long saveAddressDataSet(AddressDataSet addressDataSet);
    AddressDataSet getAddressDataSet(long id);
    void updateAddressDataSet(AddressDataSet addressDataSet);
    void deleteAddressDataSet(AddressDataSet addressDataSet);
    AddressDataSet getAddressDataSetWithAllInfo(long id);
}
