package ru.otus.homework.dao;

import ru.otus.homework.annotation.Id;
import ru.otus.homework.annotation.NoAnnotationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassMetaData<T> {
    Field annotatedField;
    Field[] fields;
    List<String> paramsForUpdate;
    List<String> paramsForInsert;
    String selectStatement;
    String updateStatement;
    String insertStatement;
    Method[] methods;


    public ClassMetaData(){
    }

    public void fillClassData(Class<T> clazz) throws NoAnnotationException {
        if(fields == null) {
            fields = clazz.getDeclaredFields();
            annotatedField = getAnnotatedField(clazz);
            methods = clazz.getDeclaredMethods();
            paramsForUpdate = getParamsForUpdate();
            paramsForInsert = getParamsForInsert();
            selectStatement = getSelectStatement(clazz);
            updateStatement = getUpdateStatement(clazz);
            insertStatement = getInsertStatement(clazz);
        }
    }

    private Field getAnnotatedField(Class<T> clazz) throws NoAnnotationException {
        for (Field field : fields){
            for(Annotation annotation : field.getDeclaredAnnotations()){
                if(annotation.annotationType().equals(Id.class)){
                    return field;
                }
            }
        }
        throw new NoAnnotationException();
    }

    public Method findSetterMethodForField(Field field){
        String methodNameInLowerCase = ("set" + field.getName()).toLowerCase();
        for(Method method : methods){
            if(method.getName().toLowerCase().equals(methodNameInLowerCase)){
                return method;
            }
        }
        return null;
    }

    private String getSelectStatement(Class<T> clazz){
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("select * from ");
        insertStatement.append(clazz.getSimpleName());
        insertStatement.append(" where ").append(annotatedField.getName()).append("  = ?");
        return insertStatement.toString();
    }

    private String getUpdateStatement(Class<T> clazz){
        StringBuilder updateStatement = new StringBuilder();
        updateStatement.append("update ").append(clazz.getSimpleName()).append(" set ");
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

    private String getInsertStatement(Class<T> clazz) {
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("insert into ").append(clazz.getSimpleName()).append("(");
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

    private List<String> getParamsForUpdate() {
        List<String> params = new ArrayList<>();
        int size = fields.length;
        for(int i = 0; i < size; i++){
            if(annotatedField.equals(fields[i]))
                continue;
            params.add(fields[i].getName());
        }
        params.add(annotatedField.getName());
        return params;
    }

    private List<String> getParamsForInsert() {
        List<String> params = new ArrayList<>();
        int size = fields.length;
        for(int i = 0; i < size; i++){
            if(annotatedField.equals(fields[i]))
                continue;
            params.add(fields[i].getName());
        }
        return params;
    }

}
