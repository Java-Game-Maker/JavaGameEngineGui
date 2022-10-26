package com.panels;

import com.javagamemaker.javagameengine.JavaGameEngine;
import com.javagamemaker.javagameengine.Scene;
import com.javagamemaker.javagameengine.components.Component;
import com.javagamemaker.javagameengine.components.Sprite;
import com.javagamemaker.javagameengine.input.Input;
import com.javagamemaker.javagameengine.input.Keys;
import com.javagamemaker.javagameengine.msc.Debug;
import com.javagamemaker.javagameengine.msc.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TilePlacer extends Scene {

    public TilePlacerSettings settings;

    public TilePlacer(TilePlacerSettings settings){
        load();
        Debug.log(getComponents1().size());
        this.settings = settings;

    }

    public boolean objectAt(Vector2 pos){
        for(Component c : getComponents1()){
            try{
                if(c.getPosition().equals(pos))
                    return true;
            }catch (Exception e){

            }
        }
        return false;
    }

    @Override
    public void update() {
        float gridCubeWidth = JavaGameEngine.getSelectedScene().gridSnapping.getX(), gridCubeHeight = JavaGameEngine.getSelectedScene().gridSnapping.getY();

        float x = Math.round(Input.getMousePosition().getX() / gridCubeWidth) * gridCubeWidth;
        float y = Math.round(Input.getMousePosition().getY() / gridCubeHeight) * gridCubeHeight;
        Vector2 pos = new Vector2(x,y);
        Sprite s = settings.selectedSprite.clone();
        if(Input.isMouseDown(Keys.MIDDLECLICK) && !objectAt(new Vector2(x,y))){

            s.setPosition(pos);
            add(s);

        }
        super.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(settings.selectedSprite != null)
            g.drawImage((Image) settings.selectedSprite.getAnimation(), (int) Input.getMousePosition().getX(), (int) Input.getMousePosition().getY(),null);
    }
}
