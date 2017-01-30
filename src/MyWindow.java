package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * This class makes the GUI and the class listworker.
 *
 * @author Eerik Timonen
 * @version 3.0
 * @since 2016-12-20
 */
class MyWindow extends JFrame implements ActionListener {
    ListWorker worker;
    JButton add;
    JButton removebutton;
    JMenuItem menuItem;
    JMenuItem menuItem2;
    JMenuItem menuItem3;
    JMenuItem merge;
    JTable table;
    JMenuBar menuBar;
    JMenu file;
    JMenu Options;
    JSpinner spinner;
    JFileChooser chooser;
    SpinnerModel smodel;
    JPanel top;
    FileNameExtensionFilter filter;
    JPanel bot;
    JPanel inputpanel;
    JSplitPane split;
    JScrollPane pane;
    int newlists;
    JTextField textField;
    DefaultTableModel model;
    String[] columnNames = {"amount", "item"};
    Object[][] data;
    String in;
    String out;
       
    /**
     * Creates a window with a list given via command args.
     *
     * @param in name of the input file from commandline args
     * @param out name of the output file from commandline args
     */
    public MyWindow(String in, String out) {
        this.in = in;
        this.out = out;
        worker = new ListWorker(in, out);
        window();
        setSize(500, 500);
    }
    
    /**
     * Creates a window without any content.
     */
    public MyWindow() {

        worker = new ListWorker();
        window();
        setSize(500, 500);
    }
    
    /**
     * Updates the table content.
     */
    public void tablecontent() {
        setTitle(worker.name);
        
        for (int i = 0; i < worker.listContainer.size(); i++) {
            
            if (worker.listContainer.get(i).getName().equals(worker.name) &&
                    worker.listContainer.get(i).get(i) != null) {

                for (int k = worker.listContainer.get(i).size() - 1;
                k >= 0; k--) {
                    model.addRow(new Object[]{worker.listContainer.get(i).get(k)
                    .getAmount(),
                            worker.listContainer.get(i).get(k).getItem()});
                }
                
                break;
            }
        }
    }

    /*
     * Adds any changes made in the table editor to the list.
     */
    public void add() {
        int amount = (Integer) spinner.getValue();
        String amountstr = Integer.toString(amount);
        String item = textField.getText();
        item = item+";";
        String content = amountstr + item;
        worker.split(content);
        model.setRowCount(0);
        tablecontent();
        textField.setText("");
        spinner.setValue(0);
    }

    /**
     * Checks if a button is pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            System.out.println("add");
            add();
        } else if (e.getSource() == menuItem) {
            int rVal = chooser.showOpenDialog(MyWindow.this);
            
            if (rVal == chooser.APPROVE_OPTION) {
                openList(chooser.getName(chooser.getSelectedFile())
                ,chooser.getCurrentDirectory());
            }
        } else if (e.getSource() == menuItem2) {
            int rVal = chooser.showSaveDialog(MyWindow.this);
            
            if (rVal == chooser.APPROVE_OPTION) {
                saveList(chooser.getName(chooser.getSelectedFile())
                ,chooser.getCurrentDirectory());
            }
        } else if (e.getSource() == removebutton) {
            System.out.println(table.getSelectedRow());
            removeRow();
            model.removeRow(table.getSelectedRow());
            removebutton.setEnabled(false);
        } else if (e.getSource() == merge) {
            int rVal = chooser.showOpenDialog(MyWindow.this);
            
            if (rVal == chooser.APPROVE_OPTION) {
                mergefile(chooser.getName(chooser.getSelectedFile())
                ,chooser.getCurrentDirectory());
            }
        } else if (e.getSource() == menuItem3){
            worker.listContainer.add(new MyLinkedList
            (new String("List"+newlists)));
            worker.name=new String("List"+newlists);
            newlists++;
            model.setRowCount(0);
            tablecontent();
        }
    }
    
    /**
     * Saves the current list to a .txt file on location chosen by the user.
     *
     * @param name of the saved file
     * @param directory of the saved file
     */
    public void saveList(String name, File directory) {

        if (name.length() >= 4 && name.substring(name.length()-4
        ,name.length()).equals(".txt")) {
            name=name.substring(0,name.length()-4);
        }
        
        name=name+".txt";
        System.out.println(name);
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter
            (new FileOutputStream(directory+"/"+name)
                    , "utf-8"));

            out.write(worker.printItems());

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens the .txt file that the user chooses and 
     *
     * puts the content into list. 
     *
     * @param name of the opened file
     * @param directory of the opened file
     */
    public void openList(String name, File directory) {
        worker.listContainer.add(new MyLinkedList<>(name));
        worker.name=name;
        try {
            BufferedReader in = new BufferedReader
            (new FileReader(directory+"/"+name));

            String line;

            while ((line = in.readLine()) != null) {
                
                if (line.equals("Your Shopping list now:")) {

                } else {
                    worker.userinput = line;
                    System.out.println(
                    "Give shopping list (example: 1 milk;2 tomato;3 carrot;)");
                    worker.checkInput();
                    System.out.println(worker.printItems());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        model.setRowCount(0);
        tablecontent();
    }
    
    /**
     * Merges the current list and the .txt file that the user wants.
     *
     * @param name of the file that is merged with current list
     * @param directory of the file that is merged
     */
    public void mergefile(String name, File directory) {

        try {
            BufferedReader in = new BufferedReader(new FileReader(directory
            +"/"+name));

            String line;

            while ((line = in.readLine()) != null) {
                
                if (line.equals("Your Shopping list now:")) {

                } else {
                    worker.userinput = line;
                    worker.checkInput();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        model.setRowCount(0);
        tablecontent();
    }
    
    /**
     * Creates the menu bar and its content to the window.
     */
    public void createMenu() {
        chooser = new JFileChooser();
        menuBar = new JMenuBar();
        filter=new FileNameExtensionFilter("TEXT FILES", "txt","text");
        chooser.setFileFilter(filter);
        file = new JMenu("File");
        Options=new JMenu("Options");
        Options.setMnemonic(KeyEvent.VK_F);
        file.setMnemonic(KeyEvent.VK_F);
        menuItem = new JMenuItem("Open");
        menuItem2=new JMenuItem("Save");
        menuItem3=new JMenuItem("New");
        merge=new JMenuItem("Merge");
        merge.addActionListener(this);
        menuItem.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        merge.setMnemonic(KeyEvent.VK_E);
        menuItem.setMnemonic(KeyEvent.VK_E);
        menuItem2.setMnemonic(KeyEvent.VK_E);
        menuItem3.setMnemonic(KeyEvent.VK_E);
        file.add(menuItem3);
        file.add(menuItem2);
        file.add(menuItem);
        Options.add(merge);
        menuBar.add(file);
        menuBar.add(Options);
        setJMenuBar(menuBar);
    }
    
    /**
     * Creates the inputholder and the input mechanism to the window.
     */
    public void createInput() {
        bot = new JPanel();
        inputpanel = new JPanel();
        textField = new JTextField();
        bot.setLayout(new BoxLayout(bot, BoxLayout.Y_AXIS));
        bot.add(inputpanel);
        inputpanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        inputpanel.setLayout(new BoxLayout(inputpanel, BoxLayout.X_AXIS));
        smodel = new SpinnerNumberModel(0, 0, 9999, 1);
        spinner = new JSpinner(smodel);
        add = new JButton("Add");
        add.addActionListener(this);
        removebutton=new JButton("Remove Row");
        removebutton.addActionListener(this);
        removebutton.setEnabled(false);
        inputpanel.add(removebutton);
        inputpanel.add(spinner);
        inputpanel.add(textField);
        inputpanel.add(add);
    }
    
    /**
     * Creates the inputholder and the input mechanism to the window.
     *
     * @param e an object which is being listened
     */
    public void window() {
        createMenu();
        createInput();
        split = new JSplitPane();
        top = new JPanel();
        getContentPane().setLayout(new GridLayout());
        getContentPane().add(split);
        split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        split.setDividerLocation(400);
        split.setTopComponent(top);
        split.setBottomComponent(bot);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table = new JTable(new DefaultTableModel(data, columnNames));
        pane = new JScrollPane(table);
        top.add(pane);
        model = (DefaultTableModel) table.getModel();
        table.getSelectionModel()
        .addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removebutton.setEnabled(true);
            }
        });
    }

    /**
     * Removes the selected row on the QUI and from the list.
     */
    public void removeRow() {
        for (int i = 0; i < worker.listContainer.size(); i++) {
            if (worker.listContainer.get(i).getName().equals(worker.name) 
                && worker.listContainer.get(i).get(i)
                    != null) {

                for (int k = 0; k < worker.listContainer.get(i).size(); k++) {
                    
                    if (worker.listContainer.get(i).get(k).getItem()
                        .equals(table.getValueAt(table.getSelectedRow(),1))){
                     worker.listContainer.get(i).remove(k);
                    }
                }
                
                System.out.println(worker.printItems());
                break;
            }
        }
    }
}
