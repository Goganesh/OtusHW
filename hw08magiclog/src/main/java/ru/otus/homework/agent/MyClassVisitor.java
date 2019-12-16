package ru.otus.homework.agent;

import org.objectweb.asm.*;

public class MyClassVisitor extends ClassVisitor {

    MyClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor myMethodVisitor = new MyMethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, descriptor, signature, exceptions), name);
        return myMethodVisitor;
    }
}
