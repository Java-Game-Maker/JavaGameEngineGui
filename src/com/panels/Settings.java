package com.panels;

import com.asd.javagameengine.components.Component;
import com.components.Vector2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Settings extends JPanel {

    com.asd.javagameengine.components.Component selectedComponent;

    public Settings(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    }

    public static List<Field> getFieldsUpTo( Class<?> startClass,
                                                 Class<?> exclusiveParent) {
        if(startClass == exclusiveParent){
          return Arrays.asList(startClass.getDeclaredFields());
        }else{
            List<Field> my = new java.util.ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));
            my.addAll(getFieldsUpTo(startClass.getSuperclass(),exclusiveParent));

            return my;
        }
    }
    /*
    plan is to scan fields and create swing elements and bide the data togheter so boolean -> checkbox
     */
    public void loadFields() throws IllegalAccessException {
            List<Field> fields = getFieldsUpTo(selectedComponent.getClass(), com.asd.javagameengine.components.Component.class);
            int nField = 1;
            for (Field field : fields) {
           //     System.out.println(field);
                // type field.getAnnotatedType()
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                if (field.getModifiers() == 1 || field.getModifiers() == 4) {
                    if (field.getType() == Color.class)
                        add(new JLabel("color"));
                    if (field.getType() == com.asd.javagameengine.msc.Vector2.class){
                        field.setAccessible(true);
                        JPanel p = new JPanel();
                        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
                        p.add(new JLabel(field.getName()));
                        p.add(new Vector2(((com.asd.javagameengine.msc.Vector2) field.get(selectedComponent))){
                            @Override
                            public void update() {
                                super.update();
                                if(field.getName() == "position") getSelectedComponent().setPosition(vector2);
                                if(field.getName() == "scale") getSelectedComponent().setScale(vector2);

                            }
                        });
                        add(p);
                        field.setAccessible(false);

                    }
                    if (field.getType() == float.class) {
                        JPanel p = new JPanel();
                        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
                        p.add(new JLabel(field.getName()));
                        JSpinner spinner = new JSpinner();
                        spinner.setValue(field.get(selectedComponent));
                        spinner.addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {

                                try {
                                    field.set(selectedComponent, spinner.getValue());
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        });
                        p.setMaximumSize(new Dimension(1000, 20));
                        spinner.setMinimumSize(new Dimension(100, 20));
                        p.add(spinner);
                        add(p);
                    }
                    if (field.getType() == boolean.class) {
                        JCheckBox box = new JCheckBox(field.getName());
                        field.setAccessible(true);
                        box.setSelected((Boolean) field.get(selectedComponent));
                        field.setAccessible(false);

                        box.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    boolean accessible = field.isAccessible();
                                    field.setAccessible(true);
                                    com.asd.javagameengine.msc.Debug.log(box.isSelected());
                                    field.set(selectedComponent, box.isSelected());
                                    field.setAccessible(accessible);
                                }catch (Exception ee){}
                            }
                        });

                        add(box);


                    }
                    if (field.getType() == int.class) {
                        JPanel p = new JPanel();
                        p.setMaximumSize(new Dimension(1000, 20));

                        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
                        p.add(new JLabel(field.getName()));
                        JSpinner spinner = new JSpinner();
                        field.setAccessible(true);
                        spinner.setValue(field.get(selectedComponent));
                        field.setAccessible(false);
                        spinner.addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                try {
                                    boolean accessible = field.isAccessible();
                                    field.setAccessible(true);
                                    field.set(selectedComponent, spinner.getValue());
                                    field.setAccessible(accessible);
                                }catch (Exception ee){}
                            }
                        });
                        p.setMaximumSize(new Dimension(1000, 20));
                        p.add(spinner);
                        add(p);
                    }
                    if(field.getType() == String.class){
                        JPanel p = new JPanel();
                        p.setMaximumSize(new Dimension(1000, 20));

                        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
                        p.add(new JLabel(field.getName()));
                        JTextField text = new JTextField();
                        field.setAccessible(true);
                        text.setText(String.valueOf(field.get(selectedComponent)));
                        field.setAccessible(false);
                        text.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    boolean accessible = field.isAccessible();
                                    field.setAccessible(true);
                                    field.set(selectedComponent, text.getText());
                                    field.setAccessible(accessible);
                                }catch (Exception ee){}
                            }
                        });
                        if(field.getType() == Component.class){
                            JButton b = new JButton("Open "+field.getName());
                            b.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                }
                            });
                            add(b);
                        }

                        p.add(text);
                        add(p);
                    }
                    field.setAccessible(accessible);

                }
                validate();
                repaint();
            }
    }

    public void setSelectedComponent(com.asd.javagameengine.components.Component selectedComponent) {
        this.selectedComponent = selectedComponent;
        removeAll();
        try {
            if(selectedComponent!=null)
                loadFields();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
/*
        pos = new Vector2(selectedComponent.getPosition()){
            @Override
            public void update() {
                super.update();
                if(selectedComponent.getParent() == null)
                    selectedComponent.setPosition(getVector2());
                else
                    selectedComponent.setParentOffset(getVector2());
            }
        };
        scale = new Vector2(selectedComponent.getScale()){
            @Override
            public void update() {
                super.update();
                selectedComponent.setScale(getVector2());
            }
        };

        rotation = new Vector2(new javagameengine.msc.Vector2(selectedComponent.getAngle(),selectedComponent.getAngle()));
        add(pos);
        add(scale);
        add(rotation);
        repaint();
        validate();*/
    }

    public void update(){

        removeAll();
        try {
            loadFields();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        validate();
        repaint();
    }

    public com.asd.javagameengine.components.Component getSelectedComponent() {
        return selectedComponent;
    }
}
