package com.company;

/**
 * Makes a generic linked list.
 *
 * @param <T> enables the list to be made for any object
 */
public class MyLinkedList<T> {
    
    /**
     * This is the name of the list.
     */
    private String name;

    /**
     * Makes the element class.
     *
     * @param <T> element can be any object
     */
    private class Element<T> {
        Element<T> next;
        T content;
    }
    
    /**
     * This is the first element of the list.
     */
    private Element<T> first;
    
    /**
     * Holds the size of the list.
     */
    private int size;

    /**
     * Gives a name to the list when it is made.
     *
     * @param name the string that is going to be the name
     */
    public MyLinkedList(String name) {
        this.name = name;
    }
    
    /**
     * Adds a new element to the list. 
     *
     * @param e object that is added to the list.
     */
    public void add(T e) {
        if (first == null) {
            first = new Element<T>();
            first.next = null;
            first.content = e;
        } else {
            Element<T> newElement = new Element<T>();
            newElement.content = e;
            newElement.next = first;
            first = newElement;
        }
        
        size++;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * Returns the content of the element.
     *
     * @param index number of the element on the list
     * @return elements content
     */
    public T get(int index) {
        Element a = first;
        
        if (index < 0 || index >= size) {
            return null;
        } else {
            for (int i = 0; i < index; i++) {
                a = a.next;
            }
        }
        
        return (T) a.content;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if it is empty
     */
    public boolean isEmpty() {
        if (size != 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Removes the element on the index which.
     *
     * @param index the index of the element which is removed
     * @return the object which is removed
     */
    public T remove(int index) {
        Element<T> a = first;
        Element<T> b = first;

        if (index < 0 || index >= size) {
            return null;
        } else {

            for (int i = 0; i < index; i++) {
                b = a;
                a = a.next;
            }
            
            if (a == first) {
                first = first.next;
            } else {
                b.next = a.next;
            }
        }
        
        size--;
        return a.content;
    }

    /**
     * Removes the element which equals to the given object.
     *
     * @param o the object that is going to be removed
     * @return return true if object is removed
     */
    public boolean remove(Object o) {
        Element<T> a = first;
        Element<T> b = first;

        for (int i = 0; i < size - 1; i++) {
            b = a;
            a = a.next;

            if (a.content.equals(o)) {
                b.next = a.next;
                size--;

                return true;
            }
        }
        
        return false;
    }

    /**
     * Returns the name of the list.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the size of the list.
     *
     * @return size
     */
    public int size() {
        return size;
    }
}
