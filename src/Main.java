

import javagameengine.JavaGameEngine;
import javagameengine.Scene;
import javagameengine.components.Collider;
import javagameengine.components.Component;
import javagameengine.components.GameObject;
import javagameengine.components.PhysicsBody;
import javagameengine.msc.Debug;
import panels.Settings;
import panels.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main extends JavaGameEngine {

    private JPanel Game;
    private JPanel asd;
    private JPanel ga;
    private JPanel settings;
    private JPanel treeholder;
    private JTree tree1;
    private JButton button1;
    private JButton newCompButton;
    public Settings settingPanel = new Settings();
    public Tree tree = new Tree();

    public Main(){
        settings.add(settingPanel);
        treeholder.add(tree);

        newCompButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu newComp = new JPopupMenu();
                JMenuItem ph = new JMenuItem("PhysicsBody");
                ph.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getSelectedScene().getSelectedComponent().add(new PhysicsBody());
                    }
                });
                JMenuItem cl = new JMenuItem("Collider");
                cl.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getSelectedScene().getSelectedComponent().add(new Collider());

                    }
                });
                newComp.add(ph);
                newComp.add(cl);
                settingPanel.add(newComp);
                newComp.setVisible(true);
                newComp.show((java.awt.Component) gameWindow, (int) (gameWindow.getSize().width/2), (int) (gameWindow.getSize().getHeight()/2));
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //setSelectedScene(new Scene());

                getSelectedScene().setDebugMode(!getSelectedScene().isDebugMode());
                getSelectedScene().load();
                getSelectedScene().start();
                if(getSelectedScene().isDebugMode()){
                    button1.setText("Start");
                } else{
                    button1.setText("Stop...");
                }
            }
        });

        Scene scene = new Scene(){
            @Override
            public void setSelectedComponent(Component selectedComponent) {
                super.setSelectedComponent(selectedComponent);
                settingPanel.setSelectedComponent(selectedComponent);

            }

        /*    @Override
            public void instantiate(Component component) {
                super.instantiate(component);
                tree.root.removeAllChildren();
                LinkedList<Component> list = getSelectedScene().getComponents1(); // adds new component
                list.add(component);
                tree.loadComponents(tree.root, list);
                ((DefaultTreeModel)tree.tree.getModel()).reload();

                tree.expandAllNodes(tree.tree, 0, tree.tree.getRowCount());
            }*/
        };
        scene.getCamera().start();
        GameObject s = new GameObject();
        s.add(new PhysicsBody(true));

        scene.add(s);

        ga.add(gameWorld);

        setSelectedScene(scene);
        tree.loadComponents(tree.root,getSelectedScene().getComponents1());
        tree.expandAllNodes(tree.tree, 0, tree.tree.getRowCount());
        scene.load();

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

            if(!Objects.equals(getSelectedScene().getComponents1().toString(), (main.tree.comps))){
                main.tree.comps = getSelectedScene().getComponents1().toString();
                main.tree.root.removeAllChildren();
                main.tree.loadComponents(main.tree.root, getSelectedScene().getComponents1());
                ((DefaultTreeModel)main.tree.tree.getModel()).reload();

                main.tree.expandAllNodes(main.tree.tree, 0,main.tree.tree.getRowCount());
                if(main.settingPanel.getSelectedComponent()!=null)
                    main.settingPanel.update();
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
