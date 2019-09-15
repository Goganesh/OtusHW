package ru.otus.homework.element;

import ru.otus.homework.types.*;

public interface Service {
    void visit(VisitArray value);
    void visit(FinVisitArray value);
    void visit(VisitObject value);
    void visit(FinVisitObject value);
    void visit(VisitPrimitive value);
    void visit(VisitString value);
    void visit(VisitNext value);
}
