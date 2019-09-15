package ru.otus.homework.service;

import lombok.Getter;
import ru.otus.homework.element.Service;
import ru.otus.homework.types.*;

public class JsonService implements Service {
    @Getter
    StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void visit(VisitArray value) {
        stringBuilder.append("[");
    }

    @Override
    public void visit(FinVisitArray value) {
        stringBuilder.append("]");
    }

    @Override
    public void visit(VisitObject value) {
        try{
            if (value.getField() == null) {
                stringBuilder.append("{");
            } else {
                stringBuilder.append("\""+value.getField().getName()+"\":{" );
            }
        } catch (Exception e) {}

    }

    @Override
    public void visit(FinVisitObject value) {
        stringBuilder.append("}");
    }

    @Override
    public void visit(VisitPrimitive value) {
        try{
            if (value.getField().getDeclaringClass().equals(Integer.class) || value.getField().getDeclaringClass().equals(Long.class)
                    || value.getField().getDeclaringClass().equals(Short.class) || value.getField().getDeclaringClass().equals(Byte.class) ||
                    value.getField().getDeclaringClass().equals(Boolean.class) || value.getField().getDeclaringClass().equals(Character.class)) {
                stringBuilder.append(value.getField().get(value.getObj()));
            } else {
                stringBuilder.append("\""+value.getField().getName()+"\":" + value.getField().get(value.getObj()));
            }
        } catch (Exception e) {}
    }

    @Override
    public void visit(VisitString value) {
        try {
            stringBuilder.append("\""+value.getField().getName()+"\":\""+ value.getField().get(value.getObj())+"\"");
        } catch (Exception e) {}
    }

    @Override
    public void visit(VisitNext value) {
        stringBuilder.append(",");
    }

}
