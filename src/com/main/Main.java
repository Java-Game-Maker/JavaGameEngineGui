package com.main;

import com.asd.javagameengine.msc.Debug;
import com.components.Assets;
import com.asd.javagameengine.JavaGameEngine;
import com.asd.javagameengine.Scene;
import com.asd.javagameengine.components.*;
import com.asd.javagameengine.msc.Vector2;
import com.panels.Settings;
import com.panels.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
    private JPanel assets;
    private JButton button2;
    public Settings settingPanel = new Settings();
    public Tree tree = new Tree();

    public Main(){

        assets.add(new Assets());
        size = new Vector2(1920/1.5f,1080/1.5f);
        settings.add(settingPanel);
        treeholder.add(tree);

        changeCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("./out/artifacts/JavaGameEngineGui_jar/scripts");

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
                        getSelectedScene().getSelectedComponent().add(new Collider(getSelectedScene().getSelectedComponent().getLocalVertices()));

                    }
                });
                JMenuItem sc = new JMenuItem("Script");
                sc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            String resp = "Hello";

                            String input = JOptionPane.showInputDialog(null, resp);


                            BufferedWriter bw = new BufferedWriter(new FileWriter("./scripts/"+input+".java"));
                            bw.write("import javagameengine.com.components.Component;\n\npublic class MyScript extends Component {\n\n}");
                            bw.close();

                            //javac -cp ..\JavaGameEngineNew\out\artifacts\JavaGameEngineNew_jar\JavaGameEngineNew.jar .\scripts\MyScript.java


                            try {
                                Runtime rt = Runtime.getRuntime();
                                Process pr = rt.exec("javac -cp ..\\JavaGameEngineNew\\out\\artifacts\\JavaGameEngineNew_jar\\JavaGameEngineNew.jar .\\scripts\\MyScript.java", new String[] {}, new File("./"));
                                // pr.waitFor(); // Intentionally not using
                            } catch(Exception ee) {
                                ee.printStackTrace();
                            }

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
                getSelectedScene().load();
                getSelectedScene().start();
                if(getSelectedScene().isDebugMode()){
                    button1.setText("Start");
                } else{
                    button1.setText("Stop...");
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("./out/artifacts/JavaGameEngineGui_jar/scripts");

                try {
                    // Convert File to a URL
                    URL url = file.toURI().toURL();          // file:/c:/myclasses/
                    URL[] urls = new URL[]{url};

                    // Create a new class loader with the directory
                    ClassLoader cl = new URLClassLoader(urls);


                    Class cls = cl.loadClass("MyScript");
                    Component a = (Component) cls.newInstance();
                    for(Component c : getSelectedScene().getComponents1()){
                        for(Component child : c.getChildren()){
                            Debug.log(String.valueOf(child.getClass()));

                            if((String.valueOf(child.getClass()).equals(String.valueOf(a.getClass())))){
                                // clone values from child to a
                                c.getChildren().remove(child);
                                c.add(a);
                            }
                        }
                    }
                    //getSelectedScene().getSelectedComponent().add(a);


                } catch (MalformedURLException | ClassNotFoundException ee) {
                    ee.printStackTrace();
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
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

        gameWindow.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
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

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

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



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
  /*  public static void main(String[] args){
        new Main();
    }*/

}
