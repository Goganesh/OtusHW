package ru.otus.homework.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.NoAnnotationException;
import ru.otus.homework.api.dao.Dao;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DaoImpl<T> implements Dao<T> {
    private static Logger logger = LoggerFactory.getLogger(DaoImpl.class);
    private final Connection connection;
    private final SqlHelper<T> sqlHelper;


    public DaoImpl(Connection connection) {
        this.connection = connection;
        this.sqlHelper = new SqlHelper<>();
    }

    @Override
    public Optional<T> findById(long id, Class<T> clazz) throws NoAnnotationException, SQLException, IllegalAccessException {
        String sql =  sqlHelper.getSelectStatement(clazz);
        T obj = null;
        try {
             obj = clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        }
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                loadDataToObject(obj, rs, clazz);
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        }
        return Optional.ofNullable(obj);
    }

    private void loadDataToObject(T obj, ResultSet rs, Class clazz) throws SQLException, InvocationTargetException, IllegalAccessException {
        while(rs.next()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                if(field.getType().equals(int.class)) {
                    int value = rs.getInt(field.getName());
                    Method method = sqlHelper.findSetterMethodForField(field, clazz);
                    method.invoke(obj, value);
                } else if (field.getType().equals(String.class)){
                    String value = rs.getString(field.getName());
                    Method method = sqlHelper.findSetterMethodForField(field, clazz);
                    method.invoke(obj, value);
                }
            }
        }
    }

    @Override
    public void updateEntity(T entity) throws NoAnnotationException, SQLException {
        String sql = sqlHelper.getUpdateStatement((Class)entity.getClass());
        List<String> params = sqlHelper.getParamsForUpdate(entity);
        Savepoint savePoint = connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            for (int idx = 0; idx < params.size(); idx++) {
                Field[] fields = entity.getClass().getDeclaredFields();
                for(Field field : fields){
                    if(field.getName().equals(params.get(idx))){
                        if(field.getType().equals(String.class)){
                            field.setAccessible(true);
                            try {
                                pst.setString(idx + 1,(String) field.get(entity));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else if (field.getType().equals(int.class)){
                            field.setAccessible(true);
                            try {
                                pst.setInt(idx + 1,(Integer) field.get(entity));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            pst.executeUpdate();
            connection.commit();
            logger.info("updated rowCount: {}");
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage());
            throw ex;
        }
    }


    @Override
    public long saveEntity(T entity) throws SQLException, NoAnnotationException {
        String sql = sqlHelper.getInsertStatement((Class)entity.getClass());
        List<String> params = sqlHelper.getParamsForInsert(entity);
        Savepoint savePoint = connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                Field[] fields = entity.getClass().getDeclaredFields();
                for(Field field : fields){
                    if(field.getName().equals(params.get(idx))){
                        if(field.getType().equals(String.class)){
                            field.setAccessible(true);
                            try {
                                pst.setString(idx + 1,(String) field.get(entity));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else if (field.getType().equals(int.class)){
                            field.setAccessible(true);
                            try {
                                pst.setInt(idx + 1,(Integer) field.get(entity));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            int rowCount = pst.executeUpdate();
            connection.commit();
            logger.info("inserted rowCount: {}", rowCount);
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);

            }
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage());
            throw ex;
        }
    }
}
