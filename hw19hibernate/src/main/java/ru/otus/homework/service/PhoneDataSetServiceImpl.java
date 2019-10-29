package ru.otus.homework.service;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.api.dao.PhoneDataSetDao;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.service.DbServiceException;
import ru.otus.homework.api.service.PhoneDataSetService;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;

public class PhoneDataSetServiceImpl implements PhoneDataSetService {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final PhoneDataSetDao dao;

    public PhoneDataSetServiceImpl(PhoneDataSetDao dao) {
        this.dao = dao;
    }

    @Override
    public long savePhoneDataSet(PhoneDataSet phoneDataSet) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long phoneDataSetId = dao.savePhoneDataSet(phoneDataSet);
                sessionManager.commitSession();

                logger.info("created phoneDataSet: {}", phoneDataSetId);
                return phoneDataSetId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }

        }
    }

    @Override
    public void updatePhoneDataSet(PhoneDataSet phoneDataSet) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.updatePhoneDataSet(phoneDataSet);
                sessionManager.commitSession();

                logger.info("created phoneDataSet: {}", phoneDataSet.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void deletePhoneDataSet(PhoneDataSet phoneDataSet) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.deletePhoneDataSet(phoneDataSet);
                sessionManager.commitSession();

                logger.info("deleted phoneDataSet: {}", phoneDataSet.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public PhoneDataSet getPhoneDataSet(long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                PhoneDataSet phoneDataSet = dao.findById(id);

                logger.info("loaded phoneDataSet: {}", phoneDataSet.getId());
                return phoneDataSet;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }

    @Override
    public PhoneDataSet getPhoneDataSetWithAllInfo(long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                PhoneDataSet phoneDataSet = dao.findById(id);
                Hibernate.initialize(phoneDataSet);

                logger.info("loaded phoneDataSet: {}", phoneDataSet.getId());
                return phoneDataSet;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }
}
