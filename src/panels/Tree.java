package panels;

import components.Component;
import javagameengine.input.Keys;
import javagameengine.msc.Debug;
import testing.Main;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class Tree extends JPanel {

    public DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
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
                }catch (Exception ee){}
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
    public void loadComponents(DefaultMutableTreeNode root, LinkedList<javagameengine.components.Component> children){
        for(javagameengine.components.Component c : children){
            Component newNode = new Component(c);
            if(c.getChildren().size() > 0){
                loadComponents(newNode, c.getChildren());
            }
            root.add(newNode);
        }
        Debug.log(root.toString());
    }
}
