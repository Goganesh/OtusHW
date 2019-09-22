package ru.otus.homework.element;

import ru.otus.homework.types.*;

public interface JsonAccumulator {
    void onVisit(VisitArray value);
    void onVisit(FinVisitArray value);
    void onVisit(VisitObject value);
    void onVisit(FinVisitObject value);
    void onVisit(VisitPrimitive value);
    void onVisit(VisitString value);
    void onVisit(VisitNext value);
    void onVisit(VisitNull value);
}
