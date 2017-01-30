package com.company;

import java.io.*;
import java.util.Scanner;

/**
 * Controls the lists. Can be made with or without commandline args.
 *
 * This class operates everything on the CLI.
 */
public class ListWorker {
    boolean on = true;
    String output;
    String name;
    String outputtext;
    String userinput;
    Scanner input = new Scanner(System.in);
    MyLinkedList<MyLinkedList<Node>> listContainer;

    /**
     * Makes listWorker with .txt file.
     *
     * @param in  name of the input file
     * @param out name of the output file
     */
    public ListWorker(String in, String out) {

        System.out.println("SHOPPING LIST");
        System.out.println("Tampere University of Applied Sciences");
        outputtext = "SHOPPING LIST" + "\r\n";
        outputtext=outputtext+"Tampere University of Applied Sciences" + "\r\n";
        output = out;
        listContainer = new MyLinkedList<>("listcontainer");
        listContainer.add(new MyLinkedList("Shopping List"));
        name = "Shopping List";
        reader(in);
    }

    /**
     * Makes listWorker without any parameters.
     */
    public ListWorker() {

        System.out.println("SHOPPING LIST");
        System.out.println("Tampere University of Applied Sciences");
        outputtext = "SHOPPING LIST" + "\r\n";
        outputtext = outputtext+"Tampere University of Applied Sciences" 
        + "\r\n";
        listContainer = new MyLinkedList<>("listcontainer");
        
        if (name == null) {
            System.out.println("Hi, make a new list by typing new");
        }
    }

    /**
     * Allows user to write.
     */
    public void askUser() {
        if (name != null) {
            thisShoppingList();
        }
        
        userinput = input.nextLine().trim();
        checkInput();
        System.out.println(printItems());
        outputtext=outputtext+"\r\n"+userinput;
        outputtext=outputtext+"\r\n"+printItems()+"\r\n";
    }

    /**
     * Makes a new list and adds it to listcontainer.
     */
    public void makeList() {
        System.out.print("List name: ");
        name = input.nextLine();
        listContainer.add(new MyLinkedList(name));
    }

    /**
     * Prints shopping list example.
     */
    public void thisShoppingList() {
        System.out.println(
                "Give shopping list (example: 1 milk;2 tomato; 3 carrot;)");
        outputtext=outputtext
                +"Give shopping list (example: 1 milk;2 tomato; 3 carrot;)";
    }

    /**
     * Checks user input.
     */
    public void checkInput() {

        if (userinput.equals("new")) {
            makeList();
        } else if (userinput.equals("lists")) {
            printNames();
        } else if (userinput.equals("exit")) {
            on = false;
            writer();
        } else if (userinput.equals("items")) {
            System.out.println(printItems());
        } else if (userinput.equals("switch")) {
            switchlist();
        } else if (userinput.equals("?")) {
            helper();
        } else if (userinput.equals("merge")) {
            merge();
        } else {
            userinput=userinput+";";
            split(userinput);
        }
    }

    /**
     * Checks if item exist.
     *
     * @param item item that is going to be checked.
     * @return true if item exists in the list.
     */
    public boolean itemExist(String item) {
        boolean exist = false;
        
        for (int i = 0; i < listContainer.size(); i++) {
            
            if (listContainer.get(i).getName().equals(name) 
                && listContainer.get(i).get(i) != null) {

                for (int k = 0; k < listContainer.get(i).size(); k++) {
                    if (listContainer.get(i).get(k).getItem().equals(item)) {
                        exist = true;
                        break;
                    } else {

                        exist = false;
                    }
                }
                
                break;
            }
        }
        
        return exist;
    }

    /**
     * Prints the names of the lists in listcontainer.
     */
    public void printNames() {
        System.out.println("Current:"+name);
        
        for (int i = 0; i < listContainer.size(); i++) {
            System.out.println(listContainer.get(i).getName());
        }
    }

    /**
     * Switchs to already existing list where you can add items.
     */
    public void switchlist() {
        boolean namefound = false;

        printNames();
        System.out.print("Switch to list: ");
        userinput = input.nextLine();
        
        for (int i = 0; i < listContainer.size(); i++) {
            if (listContainer.get(i).getName().equals(userinput)) {
                namefound = true;
                name = userinput;

                break;
            } else {
                namefound = false;
            }
        }
        
        if (!namefound) {
            System.out.println("There is no such list as: " + userinput);
        }
    }

    /**
     * Splits string to a node. If there is multiple items given splits them.
     *
     * @param split String that is going to be split.
     */
    public void split(String split) {

        MyLinkedList<String> container = new MyLinkedList<>("container");
        String s = split.replace(" ", "");
        char splitchar;
        String splitstring;
        String item = null;
        String amount = "";
        int counter = 0;
        
        for (int i = s.length() - 1; i >= 0; i--) {
            splitchar = s.charAt(i);
            splitstring = String.valueOf(splitchar);
            container.add(splitstring);
        }

        for (int i = 0; i < container.size(); i++) {

            if (container.get(i).equals(";") && counter != 0) {

                for (int k = 0; k < listContainer.size(); k++) {
                    if (listContainer.get(k).getName().equals(name)) {
                        item = item.substring(counter);
                        
                        if (item.isEmpty()) {
                            item = null;
                        }

                        if (itemExist(item)) {
                            int l=listContainer.get(k).size()-1;
                            
                            for (int p = 0; p < listContainer.get(k)
                                .size(); p++) {

                                if (listContainer.get(k).get(p).getItem()
                                    .equals(item)) {
                                    listContainer.get(k).get(p)
                                    .setAmount(listContainer.get(k).get(p)
                                    .getAmount()+ Integer.parseInt(amount));
                                }
                            }
                        } else if (item != null) {

                            listContainer.get(k).add(new Node
                            (Integer.parseInt(amount), item));
                        }
                    }
                }

                item = null;
                amount = "";
                counter = 0;
            } else {
                for (int k = 0; k < 9; k++) {

                    if (container.get(i).equals(Integer.toString(k))) {
                        amount = amount + container.get(i);

                        counter++;
                    }
                }
                
                if (item == null) {
                    item = "" + container.get(i);
                } else {
                    item = item + container.get(i);
                }
            }
        }
    }

    /**
     * Writes the list to output.txt file.
     */
    public void writer() {
        
        try {
            
            if (output == null) {
                output="output.txt";
            }
            
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter
            (new FileOutputStream(output),"utf-8"));
            out.write(outputtext);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the file which is given in the Program arguments.
     *
     * @param s name of the file.
     */
    public void reader(String s) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(s));

            String line;

            while ((line = in.readLine()) != null) {

                userinput = line;
                outputtext = outputtext
                 +"Give shopping list (example: 1 milk;2 tomato;3 carrot;)";
                System.out.println(
                 "Give shopping list (example: 1 milk;2 tomato;3 carrot;)");
                checkInput();
                System.out.println(printItems());
                outputtext=outputtext+"\r\n"+printItems()+"\r\n";
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Prints commands to command line that you can use.
     */
    public void helper() {
        System.out.println("Commands:");
        System.out.println("new: makes a new list");
        System.out.println("lists: shows existing lists");
        System.out.println("switch: switch to different list");
        System.out.println("items: shows items on the current list");
        System.out.println("exit: exits the program");
        System.out.println("merge: merge two lists together");
    }

    /**
     * Merges two lists together.
     */ 
    public void merge() {
        String mergelist;
        String item;
        printNames();
        System.out.print("Merge "+name+" with: ");
        mergelist=input.nextLine();
        
        for (int i = 0; i < listContainer.size(); i++) {
            
            if (listContainer.get(i).getName().equals(mergelist) ) {

                for (int k = 0; k < listContainer.get(i).size(); k++) {
                    item = listContainer.get(i).get(k).getAmount()+listContainer
                    .get(i).get(k).getItem()+";";
                    split(item);
                }
                
                listContainer.remove(listContainer.get(i));
            }
        }
    }

    /**
     * Returns the items.
     *
     * @return string which has the items of the list.
     */
    public String printItems() {
        String print = "Your Shopping List now:\r\n";

        for (int i = 0; i < listContainer.size(); i++) {
            if (listContainer.get(i).getName().equals(name) 
                && listContainer.get(i).get(i) != null) {

                for (int k = listContainer.get(i).size()-1; k >= 0; k--) {
                    if (k-1 < listContainer.get(i).size()) {
                        print = print +"  "+ listContainer.get(i).get(k)
                        .toString() + "\r\n";
                    } else {
                        print = print + "  "+listContainer.get(i).get(k)
                        .toString();
                    }
                }
                
                break;
            }
        }
        
        return print;
    }
}
