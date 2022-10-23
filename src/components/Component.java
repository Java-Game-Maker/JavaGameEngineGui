package components;

import javagameengine.components.Script;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class Component extends DefaultMutableTreeNode {

    public javagameengine.components.Component component;
    public Component(){
        setUserObject(component.getClass().getSimpleName());
    }
    public Component(javagameengine.components.Component c){
        this.component = c;
        setUserObject(component.getClass().getSimpleName());
    }


}
