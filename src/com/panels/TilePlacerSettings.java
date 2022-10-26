package com.panels;

import com.javagamemaker.javagameengine.components.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class TilePlacerSettings extends JPanel {

    public Sprite selectedSprite;

    public TilePlacerSettings(){
        try {

            FileInputStream fin = new FileInputStream("./animation");
            ObjectInputStream in = new ObjectInputStream(fin);
            LinkedList<LinkedList<Rectangle>> myList = (LinkedList<LinkedList<Rectangle>>) in.readObject();

            for(LinkedList<Rectangle> list : myList){
                Sprite s = new Sprite();
                s.loadAnimation(list.toArray(new Rectangle[]{}) ,"/First Asset pack.png");
                add(new Tile(s,this));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    class Tile extends JLabel{

        public Tile(Sprite sprite, TilePlacerSettings settings){
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    settings.selectedSprite = sprite;
                }
            });

            setIcon(new ImageIcon(sprite.getAnimation()));
        }

    }

}
