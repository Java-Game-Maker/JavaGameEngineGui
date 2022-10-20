

import javagameengine.JavaGameEngine;
import javagameengine.Scene;
import javagameengine.components.Component;
import javagameengine.components.GameObject;
import javagameengine.msc.Debug;
import javagameengine.msc.Vector2;
import panels.Settings;
import panels.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Main extends JavaGameEngine {

    private JPanel Game;
    private JPanel asd;
    private JPanel ga;
    private JPanel settings;
    private JPanel treeholder;
    private JTree tree1;
    public Settings settingPanel = new Settings();
    public Tree tree = new Tree();

    public Main(){
        settings.add(settingPanel);
        treeholder.add(tree);

        Scene scene = new Scene(){
            @Override
            public void setSelectedComponent(Component selectedComponent) {
                super.setSelectedComponent(selectedComponent);
                settingPanel.setSelectedComponent(selectedComponent);

            }

            @Override
            public void instantiate(Component component) {
                super.instantiate(component);
                tree.root.removeAllChildren();
                LinkedList<Component> list = getSelectedScene().getComponents1(); // adds new component 
                list.add(component);
                tree.loadComponents(tree.root, list);
                ((DefaultTreeModel)tree.tree.getModel()).reload();

                tree.expandAllNodes(tree.tree, 0, tree.tree.getRowCount());
            }
        };
        scene.getCamera().start();

        GameObject s = new GameObject();
        s.add(new GameObject());
        s.getChildren().get(0).setParentOffset(new Vector2(100,100));
        s.add(new GameObject());
        scene.add(s);

        ga.add(gameWorld);

        setSelectedScene(scene);
        tree.loadComponents(tree.root,getSelectedScene().getComponents1());
        tree.expandAllNodes(tree.tree, 0, tree.tree.getRowCount());

        start(this);

    }


    public static void start(Main main) {
        gameWindow.setSize((int)size.getX(), (int)size.getY());
        gameWindow.setContentPane(main.asd);
        gameWindow.setDefaultCloseOperation(3);
        gameWindow.setVisible(true);
        getSelectedScene().start();

        while(true) {
            update();

            if(getSelectedScene().getSelectedComponent() != null){
               // main.settingPanel.update();
            }


        }
    }


    public static void main(String[] args){
        new Main();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
