package components;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Component extends DefaultMutableTreeNode {

    public javagameengine.components.Component component;
    public Component(){
        setUserObject("GameObject");
    }
    public Component(javagameengine.components.Component c){
        this.component = c;
        setUserObject("GameObject");
    }
}
