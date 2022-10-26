package com.panels;

import com.javagamemaker.javagameengine.components.Sprite;
import com.javagamemaker.javagameengine.msc.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class ImageRectSettings extends JPanel {

    public LinkedList<LinkedList<Rectangle>> animations = new LinkedList<>();
    public LinkedList<Rectangle> currentAnimation = new LinkedList<>();
    public ImageRectSettings(){


        JButton importAnimation = new JButton("Import Animation");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        importAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fos = new FileInputStream("animation");
                    ObjectInputStream oos = new ObjectInputStream(fos);
                    animations = (LinkedList<LinkedList<Rectangle>>) oos.readObject();
                    update();

                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        });

        add(importAnimation);
        JButton newAnimation = new JButton("New Animation");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        newAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAnimation = new LinkedList<>();
                animations.add(currentAnimation);
            }
        });

        add(newAnimation);
        JButton saveAnimation = new JButton("Save Animation");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        saveAnimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    FileOutputStream fout = new FileOutputStream("animation");
                    ObjectOutputStream out = new ObjectOutputStream(fout);
                    out.writeObject(animations);
                }catch (Exception ee){ee.printStackTrace();}

            }
        });

        add(saveAnimation);

        animations.add(currentAnimation);

    }

    public void update(){

        for(Component c : getComponents()){
            try{
                Animation a = (Animation) c;
                remove(a);
            }catch (Exception e){

            }
        }

        for(LinkedList<Rectangle> an : animations){
            add(new Animation(an));

            add(Box.createRigidArea(new Dimension(5, 0)));

        }
        validate();
        repaint();
    }

    class Animation extends JPanel{

        public Animation(LinkedList<Rectangle> animation){
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            setBackground(Color.ORANGE);


            setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            for(Rectangle r : animation){
                add(new JLabel("rec"));
            }
        }

    }

}
