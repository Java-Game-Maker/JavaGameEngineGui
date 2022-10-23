package com.panels;

import com.asd.javagameengine.msc.Debug;
import com.asd.testing.car.Main;
import com.components.Component;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.LinkedList;

public class Tree extends JPanel {

    public Component root = new Component(com.main.Main.getSelectedScene().getCamera());
    DefaultMutableTreeNode selected = new DefaultMutableTreeNode();
    public JTree tree;
    public Tree(){
        tree = new JTree(root);


        loadComponents(root, Main.getSelectedScene().getComponents1());

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                try{
                    Main.getSelectedScene().setSelectedComponent(((Component)tree.getLastSelectedPathComponent()).component);
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        });

        expandAllNodes(tree, 0, tree.getRowCount());

        add(tree);
    }
    public void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            tree.expandRow(i);
        }

        if(tree.getRowCount()!=rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
    public String comps = "";
    public void loadComponents(DefaultMutableTreeNode root, LinkedList<com.asd.javagameengine.components.Component> children){
        for(com.asd.javagameengine.components.Component c : children){
            Component newNode = new Component(c);
            if(c.getChildren().size() > 0){
                loadComponents(newNode, c.getChildren());
            }
            root.add(newNode);
        }
    }
}
