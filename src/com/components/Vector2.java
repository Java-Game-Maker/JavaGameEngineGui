package com.components;

import com.javagamemaker.javagameengine.msc.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vector2 extends JPanel {

    public com.javagamemaker.javagameengine.msc.Vector2 vector2 = com.javagamemaker.javagameengine.msc.Vector2.zero;
    JTextField x;
    JTextField y;
    public Vector2(){
        init();
    }

    public Vector2(com.javagamemaker.javagameengine.msc.Vector2 value){
        vector2 = value;
        init();
    }

    public void init(){
        setMaximumSize(new Dimension(1000,20));

        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        x = new JTextField();
        x.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vector2.setX(Float.parseFloat(x.getText()));
                update();
            }
        });

        y = new JTextField();
        y.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vector2.setY(Float.parseFloat(y.getText()));
                update();
            }
        });

        if(vector2 != null){
            x.setText(String.valueOf(vector2.getX()));
            y.setText(String.valueOf(vector2.getY()));
        }

        add(new JLabel("X: "));
        add(x);
        add(new JLabel("Y: "));
        add(y);
    }

    public void update(){
        x.setText(String.valueOf(vector2.getX()));
        y.setText(String.valueOf(vector2.getY()));

        repaint();
        validate();
    }

    public com.javagamemaker.javagameengine.msc.Vector2 getVector2() {
        return vector2;
    }

    public void setVector2(com.javagamemaker.javagameengine.msc.Vector2 vector2) {
        this.vector2 = vector2;
    }
}
