package panels;

import components.Vector2;
import javagameengine.components.Component;
import javagameengine.components.PhysicsBody;
import javagameengine.msc.Debug;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class Settings extends JPanel {

    Component selectedComponent;
    Vector2 pos;
    Vector2 scale;
    Vector2 rotation;
    public Settings(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    }

    public static List<Field> getFieldsUpTo( Class<?> startClass,
                                                 Class<?> exclusiveParent) {
        if(startClass == exclusiveParent){
          return List.of(startClass.getDeclaredFields());
        }else{
            List<Field> my = new java.util.ArrayList<>(List.of(startClass.getDeclaredFields()));
            my.addAll(getFieldsUpTo(startClass.getSuperclass(),exclusiveParent));

            return my;
        }
    }
    /*
    plan is to scan fields and create swing elements and bide the data togheter so boolean -> checkbox
     */
    public void loadFields() throws IllegalAccessException {
            List<Field> fields = getFieldsUpTo(selectedComponent.getClass(),Component.class);
            int nField = 1;
            System.out.println("1. List of all fields in a "+selectedComponent.getClass()+" class");
            for (Field field : fields) {
           //     System.out.println(field);
                // type field.getAnnotatedType()
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                if (field.getModifiers() == 1 || field.getModifiers() == 4) {
                    System.out.println(new Vector2().getClass());
                    if (field.getType() == Color.class)
                        add(new JLabel("color"));
                    if (field.getType() == javagameengine.msc.Vector2.class){
                        System.out.println("asd");
                        field.setAccessible(true);
                        JPanel p = new JPanel();
                        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
                        p.add(new JLabel(field.getName()));
                        p.add(new Vector2(((javagameengine.msc.Vector2) field.get(selectedComponent))));
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
                                    Debug.log(box.isSelected());
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
                        p.add(text);
                        add(p);
                    }
                    field.setAccessible(accessible);

                }
                validate();
                repaint();
            }
    }

    public void setSelectedComponent(Component selectedComponent) {
        this.selectedComponent = selectedComponent;
        removeAll();
        try {
            loadFields();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
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

    public Component getSelectedComponent() {
        return selectedComponent;
    }
}
