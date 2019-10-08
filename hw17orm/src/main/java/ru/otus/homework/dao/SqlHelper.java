package ru.otus.homework.dao;

import ru.otus.homework.annotation.Id;
import ru.otus.homework.annotation.NoAnnotationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SqlHelper<T> {

    private SqlHelper(){
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

    public Method findSetterMethodForField(Field field, Class<T> entity){
        Method[] methods = entity.getDeclaredMethods();
        String methodNameInLowerCase = ("set" + field.getName()).toLowerCase();
        for(Method method : methods){
            if(method.getName().toLowerCase().equals(methodNameInLowerCase)){
                return method;
            }
        }
        return null;
    }

    public String getSelectStatement(Class<T> clazz) throws NoAnnotationException {
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("select * from ");
        insertStatement.append(clazz.getSimpleName());
        Field annotatedField = getAnnotatedField(clazz);
        insertStatement.append(" where ").append(annotatedField.getName()).append("  = ?");
        return insertStatement.toString();
    }

    public String getUpdateStatement(Class<T> clazz) throws NoAnnotationException {
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

    public String getInsertStatement(Class<T> clazz) throws NoAnnotationException {
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

    public List<String> getParamsForUpdate(T clazz) throws NoAnnotationException {
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

    public List<String> getParamsForInsert(T clazz) throws NoAnnotationException {
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

}
