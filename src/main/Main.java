

import javagameengine.JavaGameEngine;
import javagameengine.Scene;
import javagameengine.components.*;
import javagameengine.msc.Debug;
import panels.Settings;
import panels.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Objects;

public class Main extends JavaGameEngine {

    private JPanel Game;
    private JPanel asd;
    private JPanel ga;
    private JPanel settings;
    private JPanel treeholder;
    private JTree tree1;
    private JButton button1;
    private JButton newCompButton;
    private JButton changeCodeButton;
    public Settings settingPanel = new Settings();
    public Tree tree = new Tree();

    public Main(){
        settings.add(settingPanel);
        treeholder.add(tree);

        changeCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a File object on the root of the directory containing the class file
                File file = new File("./scripts");

                try {
                    // Convert File to a URL
                    URL url = file.toURI().toURL();          // file:/c:/myclasses/
                    URL[] urls = new URL[]{url};

                    // Create a new class loader with the directory
                    ClassLoader cl = new URLClassLoader(urls);

                    // Load in the class; MyClass.class should be located in
                    // the directory file:/c:/myclasses/com/mycompany
                    Class cls = cl.loadClass("MyScript");
                    Component a = (Component) cls.newInstance();

                    getSelectedScene().getSelectedComponent().add(a);


                } catch (MalformedURLException | ClassNotFoundException ee) {
                    ee.printStackTrace();
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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
                JMenuItem sc = new JMenuItem("Script");
                sc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            BufferedWriter bw = new BufferedWriter(new FileWriter("./scripts/MyScript.java"));
                            bw.write("package components;\nimport javagameengine.components.Script;\n\npublic class MyScript extends Script {\n\n}");
                            bw.close();

                        }catch (Exception ee){}

                    }
                });

                newComp.add(sc);
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
                getSelectedScene().getComponents1().clear();
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
