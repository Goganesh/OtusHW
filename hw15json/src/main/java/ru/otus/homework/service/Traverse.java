package ru.otus.homework.service;

import ru.otus.homework.element.JsonAccumulator;
import ru.otus.homework.types.*;

import java.lang.reflect.*;
import java.util.*;

public class Traverse {
    public void traverse(Field mainField, Object object, JsonAccumulator jsonAccumulator) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if(object == null) {
            new VisitNull(null).accept(jsonAccumulator);
            return;
        } else if (mainField == null && isPrimitiveVisit(object)) {
            new VisitPrimitive(null, object).accept(jsonAccumulator);
            return;
        } else if (mainField == null && object.getClass().equals(String.class)) {
            new VisitString(null, object).accept(jsonAccumulator);
            return;
        }

        if (Collection.class.isAssignableFrom(object.getClass()) || AbstractList.class.isAssignableFrom(object.getClass()) ){
            Method toArray = object.getClass().getMethod("toArray");
            toArray.setAccessible(true);
            Object obj = toArray.invoke(object);
            if(obj != null){
                traverse(null, obj, jsonAccumulator);
            }
            return;
        } else if(object.getClass().isArray()){
            new VisitArray(mainField, object).accept(jsonAccumulator);
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                if(Array.get(object, i) != null) {
                    traverse(null, Array.get(object, i), jsonAccumulator);
                    if (i != length - 1) {
                        new VisitNext(null, object).accept(jsonAccumulator);
                    }
                }
            }
        } else if (!(isPrimitiveVisit(object))){
            new VisitObject(mainField, object).accept(jsonAccumulator);
        }
        Field[] fields = object.getClass().getDeclaredFields();
        int length = fields.length;
        for(Field field: fields){
            field.setAccessible(true);
            if(Modifier.isStatic(field.getModifiers())){
                length--;
            }
        }
        int i = 0;
        for(Field field: fields){
            if (field == null) {
                System.out.println();
            }
            i++;
            field.setAccessible(true);
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            if (field.getType().isPrimitive()){
                if(length <= i){
                    new VisitPrimitive(field, object).accept(jsonAccumulator);
                } else {
                    new VisitPrimitive(field, object).accept(jsonAccumulator);
                    new VisitNext(null, object).accept(jsonAccumulator);
                }
            } else if (field.getType().equals(String.class)){
                if(length == i) {
                    new VisitString(field, object).accept(jsonAccumulator);
                } else {
                    new VisitString(field, object).accept(jsonAccumulator);
                    new VisitNext(null, object).accept(jsonAccumulator);
                }
            } else {
                traverse(field, field.get(object), jsonAccumulator);
            }
        }
        if(object.getClass().isArray()){
            new FinVisitArray(null, object).accept(jsonAccumulator);
        } else if(!(isPrimitiveVisit(object))){
            new FinVisitObject(null, object).accept(jsonAccumulator);
        }
    }

    private boolean isPrimitiveVisit(Object object){
        return object.getClass().equals(Integer.class) || object.getClass().equals(Byte.class) ||
                object.getClass().equals(Short.class) || object.getClass().equals(Long.class) ||
                object.getClass().equals(Character.class) || object.getClass().equals(Boolean.class)
                || object.getClass().equals(Float.class) || object.getClass().equals(Double.class);
    }
}
