package ru.otus.homework.service;

import lombok.Getter;
import ru.otus.homework.element.AbstractField;
import ru.otus.homework.element.Service;
import ru.otus.homework.types.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JsonAccumulator implements Service {
    @Getter
    protected StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void onVisit(VisitArray value) {
        stringBuilder.append("[");
    }

    @Override
    public void onVisit(FinVisitArray value) {
        stringBuilder.append("]");
    }

    @Override
    public void onVisit(VisitObject value) {
        try{
            if (value.getField() == null) {
                stringBuilder.append("{");
            } else {
                stringBuilder.append("\""+value.getField().getName()+"\":{" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onVisit(FinVisitObject value) {
        stringBuilder.append("}");
    }

    @Override
    public void onVisit(VisitPrimitive value) {
        try{
            if(value.getField() == null && isPrimitiveVisit(value.getObj())){
                if(value.getObj().getClass().equals(Character.class)){
                    stringBuilder.append("\""+value.getObj()+"\"");
                } else {
                    stringBuilder.append(value.getObj());
                }
            } else if (checkFieldPrimitive(value)) {
                stringBuilder.append(value.getField().get(value.getObj()));
            } else {
                stringBuilder.append("\""+value.getField().getName()+"\":" + value.getField().get(value.getObj()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVisit(VisitString value) {
        try {
            if(value.getField() == null){
                Method[] methods = value.getObj().getClass().getDeclaredMethods();
                for (Method method:methods){
                    if(method.getName().equals("getBytes") && method.getParameterCount() == 0){
                        stringBuilder.append("\"");
                        byte[] bytes =(byte[]) method.invoke(value.getObj());
                        stringBuilder.append(new String(bytes));
                        stringBuilder.append("\"");
                    }
                }
            }else {
                stringBuilder.append("\"" + value.getField().getName() + "\":\"" + value.getField().get(value.getObj()) + "\"");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVisit(VisitNext value) {
        stringBuilder.append(",");
    }

    @Override
    public void onVisit(VisitNull value) {
        stringBuilder.append("null");
    }

    private boolean checkFieldPrimitive(AbstractField value){
        return value.getField().getDeclaringClass().equals(Integer.class) || value.getField().getDeclaringClass().equals(Long.class)
                || value.getField().getDeclaringClass().equals(Short.class) || value.getField().getDeclaringClass().equals(Byte.class) ||
                value.getField().getDeclaringClass().equals(Boolean.class) || value.getField().getDeclaringClass().equals(Character.class)
                || value.getField().getDeclaringClass().equals(Float.class) || value.getField().getDeclaringClass().equals(Double.class) ;
    }

    private boolean isPrimitiveVisit(Object object){
        return object.getClass().equals(Integer.class) || object.getClass().equals(Byte.class) ||
                object.getClass().equals(Short.class) || object.getClass().equals(Long.class) ||
                object.getClass().equals(Character.class) || object.getClass().equals(Boolean.class)
                || object.getClass().equals(Float.class) || object.getClass().equals(Double.class);
    }

}
