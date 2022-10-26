package com.panels;

import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.Component;
import com.javagamemaker.javagameengine.components.GameObject;
import com.javagamemaker.javagameengine.components.Sprite;
import com.javagamemaker.javagameengine.input.Input;
import com.javagamemaker.javagameengine.input.Keys;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ImageRect extends Scene {

    public Sprite img = new Sprite();
    public boolean imgeCreater = true;
    public ImageRectSettings settings;
    public ImageRect(JButton b, ImageRectSettings settings){
        this.settings = settings;
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                img.loadAnimation(new String[]{"/First Asset pack.png"});
                img.setScale(new Vector2(img.getAnimation().getWidth(),img.getAnimation().getHeight()));
                img.setPosition(new Vector2(img.getAnimation().getWidth()/2,img.getAnimation().getHeight()/2));

            }
        });
        add(img);
    }

    @Override
    public void start() {
        super.start();
        getCamera().setScale(new Vector2(1/2f,1/2f));
        getCamera().setPosition(new Vector2(-200,-200));
    }

    Vector2 start = null;
    Rectangle currentRec = new Rectangle();
    @Override
    public void update() {
        super.update();
        if(Input.isMouseDown()){
            if(start == null){
                start = Input.getMousePosition();
                currentRec.x = (int) start.getX();
                currentRec.y = (int) start.getY();
            }else{
                Vector2 pos = Input.getMousePosition().subtract(start);
                currentRec.width = (int) pos.getX();
                currentRec.height = (int) pos.getY();
            }
        }
        else if(start != null){
            start = null;
        }

        if(currentRec != null && Input.isKeyPressed(Keys.Q)){
            settings.currentAnimation.add(new Rectangle(currentRec.x,currentRec.y,currentRec.width,currentRec.height));
            currentRec = new Rectangle();
            settings.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Rectangle rectangle : settings.currentAnimation)
            g.drawRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
        g.drawRect(currentRec.x,currentRec.y,currentRec.width,currentRec.height);
    }

    @Override
    public boolean inside(Component component) {
        return true;
    }
}
