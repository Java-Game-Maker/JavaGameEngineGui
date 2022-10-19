

import javagameengine.JavaGameEngine;
import javagameengine.Scene;

import javax.swing.*;

public class Main extends JavaGameEngine {

    private JPanel Game;

    public Main(){

        Scene scene = new Scene();

        Game.add(scene);

        setSelectedScene(scene);
        start();
    }


    public static void main(String[] args){
        new Main();
    }

}
