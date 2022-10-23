package com.components;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Objects;

public class Assets extends JPanel {

    public Assets(){
        setLayout(new FlowLayout());
        try{
            File folder = new File("./scripts/");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
                if (listOfFiles[i].isFile()) {
                    add(new JFile(listOfFiles[i].getName()));
                } else if (listOfFiles[i].isDirectory()) {
                    add(new JButton(listOfFiles[i].getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class JFile extends JPanel{
        public JFile(String name){
            try{
                setBackground(Color.LIGHT_GRAY);
                add(new JLabel(name));
            }catch (Exception e){
                e.printStackTrace();
            }
            addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    setBackground(Color.gray);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    setBackground(Color.LIGHT_GRAY);

                }
            });
        }



    }

}
