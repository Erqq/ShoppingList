package com.company;

import java.io.File;

/**
 * This program allows user to make shopping lists.
 *
 * @author Eerik Timonen
 * @version 3.0
 * @since 2016-12-20
 */
public class Main {
    
    /**
     * This is the main method which makes ListWorker class.
     *
     * @param args Command line parameters
     */
    public static void main(String[] args) {
        MyWindow window;
        
        if (args.length == 0) {
            window = new MyWindow();
            window.setVisible(true);
        } else {
            File in = new File(args[0]);
            String output=null;
            String input = args[0];
            
            if (args.length > 1) {
                output=args[1];
            }
            
            if (in.exists()) {
                window=new MyWindow(input, output);
                window.setVisible(true);
            } else {
                System.out.println("asd");
                window=new MyWindow();
                window.setVisible(true);
            }
        }

        while (window.worker.on) {
            window.tablecontent();
            window.worker.askUser();
            window.model.setRowCount(0);
        }
        
        window.setVisible(false);
        window.dispose();
    }
}
