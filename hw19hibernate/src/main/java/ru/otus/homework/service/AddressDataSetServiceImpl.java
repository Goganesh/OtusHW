package ru.otus.homework.service;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.api.dao.AddressDataSetDao;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.service.AddressDataSetService;
import ru.otus.homework.api.service.DbServiceException;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.AddressDataSet;

public class AddressDataSetServiceImpl implements AddressDataSetService {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final AddressDataSetDao dao;

    public AddressDataSetServiceImpl(AddressDataSetDao dao) {
        this.dao = dao;
    }

    @Override
    public long saveAddressDataSet(AddressDataSet addressDataSet) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long addressDataSetId = dao.saveAddressDataSet(addressDataSet);
                sessionManager.commitSession();

                logger.info("created adressDataSet: {}", addressDataSetId);
                return addressDataSetId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }

        }
    }

    @Override
    public void updateAddressDataSet(AddressDataSet addressDataSet) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.updateAddressDataSet(addressDataSet);
                sessionManager.commitSession();

                logger.info("created addressDataSet: {}", addressDataSet.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void deleteAddressDataSet(AddressDataSet addressDataSet) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.deleteAddressDataSet(addressDataSet);
                sessionManager.commitSession();

                logger.info("deleted addressDataSet: {}", addressDataSet.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public AddressDataSet getAddressDataSet(long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                AddressDataSet addressDataSet = dao.findById(id);

                logger.info("loaded addressDataSet: {}", addressDataSet.getId());
                return addressDataSet;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }

    @Override
    public AddressDataSet getAddressDataSetWithAllInfo(long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                AddressDataSet addressDataSet = dao.findById(id);
                Hibernate.initialize(addressDataSet);

                logger.info("loaded addressDataSet: {}", addressDataSet.getId());
                return addressDataSet;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }
}
