package com.company;

    /**
     * Makes a helper class for MyLinkedlist. Enables two objects for elements. 
     * 
     */
public class Node {
    
    /**
     * Holds the amount of the item.
     *
     * @param amount of the item
     */
    private int amount;
    
    /**
     * Holds the name of the item.
     *
     * @param item name of the item
     */
    private String item;

    /**
     * Makes the node with int amount and string item.
     *
     * @param amount amount of the item
     * @param item   name of the item
     */
    public Node(Integer amount, String item) {
        setAmount(amount);
        setItem(item);
    }

    /**
     * Adds amount and item to a string and returns it.
     *
     * @return String of amount and item.
     */
    public String toString() {
        String listitem = new String(getAmount() + " " + getItem());

        return listitem;
    }

    /**
     * Sets the amount of the item.
     *
     * @param amount number that is give to the method
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Gets the amount of the item which is saved to this node.
     *
     * @return amount of the item.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the item.
     *
     * @param item name of the item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Gets the item which is saved to this node.
     *
     * @return name of the item.
     */
    public String getItem() {
        return item;
    }
}
