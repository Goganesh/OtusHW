package ru.otus.homework.api.service;

import ru.otus.homework.model.PhoneDataSet;

public interface PhoneDataSetService{
    long savePhoneDataSet(PhoneDataSet phoneDataSet);
    PhoneDataSet getPhoneDataSet(long id);
    void updatePhoneDataSet(PhoneDataSet phoneDataSet);
    void deletePhoneDataSet(PhoneDataSet phoneDataSet);
    PhoneDataSet getPhoneDataSetWithAllInfo(long id);
}
