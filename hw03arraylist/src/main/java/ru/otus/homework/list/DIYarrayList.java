package ru.otus.homework.list;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class DIYarrayList<T> implements List<T> {
    private int size = 0;
    private Object[] objects = {};
    private int modCount = 0;

    public boolean add(T t) {
        if (size + 1 > this.objects.length) {
            Object[] newObjects = Arrays.copyOf(this.objects, this.objects.length + 10);
            newObjects[size] = t;
            this.objects = newObjects;
            size++;
        } else {
            this.objects[size] = t;
            size++;
        }
        return true;
    }

    public DIYarrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.objects = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    public DIYarrayList(){
        this.objects = new Object[10];
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        return  (T)this.objects[index];
    }

    public T set(int index, T element) {
        this.objects[index] = element;
        return get(index);
    }

    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) objects, 0, size, c);
    }

    public ListIterator<T> listIterator() {
        return new Itr(0);
    }

    private class Itr implements Iterator<T>, ListIterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        Itr() {}

        Itr(int index) {
            cursor = index;
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.objects;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                DIYarrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            final int size = DIYarrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = DIYarrayList.this.objects;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((T) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = DIYarrayList.this.objects;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (T) elementData[lastRet = i];
        }

        public void set(T e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                DIYarrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(T e) {
            checkForComodification();

            try {
                int i = cursor;
                DIYarrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }


    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
}

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void add(int index, T element) {

        Object[] newList = new Object[objects.length+1];
        int count = 0;
        for (int i = 0; i < size+1; i++) {
            if (index == count) {
                newList[count] = element;
                count++;
                i--;
                continue;
            } newList[count] = objects[i];
            count++;
        }
        objects = newList;
        size++;
    }

    public T remove(int index) {
        T t = (T) objects[index];

        Object[] newList = new Object[objects.length];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if(index == i) {
                continue;
            }
            newList[count] = objects[i];
            count++;
        }
        size--;
        objects = newList;
        return t;
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException();
    }
}
