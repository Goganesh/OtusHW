package ru.otus.homework.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.Id;
import ru.otus.homework.annotation.NoAnnotationException;
import ru.otus.homework.api.dao.Dao;
import ru.otus.homework.api.sessionmanager.SessionManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoRepository<T> implements Dao<T> {
    private static Logger logger = LoggerFactory.getLogger(DaoRepository.class);
    private final SessionManager sessionManager;

    public DaoRepository(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<T> findById(long id, Class<T> clazz) throws NoAnnotationException, SQLException, IllegalAccessException {
        sessionManager.beginSession();
        Connection connection = sessionManager.getCurrentSession().getConnection();
        String sql =  getSelectStatement(clazz);
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
        sessionManager.close();
        return Optional.ofNullable(obj);
    }

    private void loadDataToObject(T obj, ResultSet rs, Class clazz) throws SQLException, InvocationTargetException, IllegalAccessException {
        while(rs.next()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                if(field.getType().equals(int.class)) {
                    int value = rs.getInt(field.getName());
                    Method method = findSetterMethodForField(field, clazz);
                    method.invoke(obj, value);
                } else if (field.getType().equals(String.class)){
                    String value = rs.getString(field.getName());
                    Method method = findSetterMethodForField(field, clazz);
                    method.invoke(obj, value);
                }
            }
        }
    }

    @Override
    public void updateEntity(T entity) throws NoAnnotationException, SQLException {
        String sql = getUpdateStatement((Class)entity.getClass());
        List<String> params = getParamsForUpdate(entity); // уточнить
        sessionManager.beginSession();
        Connection connection = getSessionManager().getCurrentSession().getConnection();
        Savepoint savePoint = connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            for (int idx = 0; idx < params.size(); idx++) {
                Field[] fields = entity.getClass().getDeclaredFields();
                for(Field field : fields){
                    if(field.getName().equals(params.get(idx))){
                        if(field.getType().getSimpleName().equals("String")){
                            field.setAccessible(true);
                            try {
                                pst.setString(idx + 1,(String) field.get(entity));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else if (field.getType().getSimpleName().equals("int")){
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
        String sql = getInsertStatement((Class)entity.getClass());
        List<String> params = getParamsForInsert(entity);
        sessionManager.beginSession();
        Connection connection = getSessionManager().getCurrentSession().getConnection();
        Savepoint savePoint = connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                Field[] fields = entity.getClass().getDeclaredFields();
                for(Field field : fields){
                    if(field.getName().equals(params.get(idx))){
                        if(field.getType().getSimpleName().equals("String")){
                            field.setAccessible(true);
                            try {
                                pst.setString(idx + 1,(String) field.get(entity));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else if (field.getType().getSimpleName().equals("int")){
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

    private Method findSetterMethodForField(Field field, Class<T> entity){
        Method[] methods = entity.getDeclaredMethods();
        String methodNameInLowerCase = ("set" + field.getName()).toLowerCase();
        for(Method method : methods){
            if(method.getName().toLowerCase().equals(methodNameInLowerCase)){
                return method;
            }
        }
        return null;
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private String getSelectStatement(Class<T> clazz) throws NoAnnotationException {
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("select * from ");
        insertStatement.append(clazz.getSimpleName());
        Field annotatedField = getAnnotatedField(clazz);
        insertStatement.append(" where ").append(annotatedField.getName()).append("  = ?");
        return insertStatement.toString();
    }

    private String getUpdateStatement(Class<T> clazz) throws NoAnnotationException {
        StringBuilder updateStatement = new StringBuilder();
        updateStatement.append("update ").append(clazz.getSimpleName()).append(" set ");
        Field[] fields = clazz.getDeclaredFields();
        Field annotatedField = getAnnotatedField(clazz);
        int size = fields.length;
        for(int i = 0; i < size; i++){
            if(annotatedField.equals(fields[i]))
                continue;
            updateStatement.append(fields[i].getName()).append(" = ? ");
            if(i + 1 < size)
                updateStatement.append(", ");
        }
        updateStatement.append("where ").append(annotatedField.getName()).append(" = ? ");
        return updateStatement.toString();
    }

    private String getInsertStatement(Class<T> clazz) throws NoAnnotationException {
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("insert into ").append(clazz.getSimpleName()).append("(");
        Field[] fields = clazz.getDeclaredFields();
        Field annotatedField = getAnnotatedField(clazz);
        int size = fields.length;
        for(int i = 0; i < size; i++){
            if(annotatedField.equals(fields[i]))
                continue;
            insertStatement.append(fields[i].getName());
            if(i + 1 < size)
                insertStatement.append(", ");
        }
        insertStatement.append(")").append(" values (");
        for(int i = 1; i < size; i++){
            insertStatement.append("?");
            if(i + 1 < size)
                insertStatement.append(",");
        }
        insertStatement.append(")");
        return insertStatement.toString();
    }

    private List<String> getParamsForUpdate(T clazz) throws NoAnnotationException {
        List<String> params = new ArrayList<>();
        Field[] fields = clazz.getClass().getDeclaredFields();
        Class tClass = clazz.getClass();
        Field annotatedField = getAnnotatedField(tClass);
        int size = fields.length;
        for(int i = 0; i < size; i++){
            if(annotatedField.equals(fields[i]))
                continue;
            params.add(fields[i].getName());
        }
        params.add(annotatedField.getName());
        return params;
    }

    private List<String> getParamsForInsert(T clazz) throws NoAnnotationException {
        List<String> params = new ArrayList<>();
        Field[] fields = clazz.getClass().getDeclaredFields();
        Class tClass = clazz.getClass();
        Field annotatedField = getAnnotatedField(tClass);
        int size = fields.length;
        for(int i = 0; i < size; i++){
            if(annotatedField.equals(fields[i]))
                continue;
            params.add(fields[i].getName());
        }
        return params;
    }

    private Field getAnnotatedField(Class<T> clazz) throws NoAnnotationException {
        for (Field field : clazz.getDeclaredFields()){
            for(Annotation annotation : field.getDeclaredAnnotations()){
                if(annotation.annotationType().equals(Id.class)){
                    return field;
                }
            }
        }
        throw new NoAnnotationException();
    }
}
