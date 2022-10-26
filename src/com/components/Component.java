package com.components;


import javax.swing.tree.DefaultMutableTreeNode;

public class Component extends DefaultMutableTreeNode {

    public com.javagamemaker.javagameengine.components.Component component;
    public Component(){
        setUserObject(component.getClass().getSimpleName());
    }
    public Component(com.javagamemaker.javagameengine.components.Component c){
        this.component = c;
        setUserObject(component.getClass().getSimpleName());
    }


}
