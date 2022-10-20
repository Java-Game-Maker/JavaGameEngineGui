package panels;

import components.Vector2;
import javagameengine.components.Component;
import javagameengine.msc.Debug;

import javax.swing.*;

public class Settings extends JPanel {

    Component selectedComponent;
    Vector2 pos;
    Vector2 scale;
    Vector2 rotation;
    public Settings(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    }

    public void setSelectedComponent(Component selectedComponent) {
        this.selectedComponent = selectedComponent;
        removeAll();
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
        validate();
    }

    public void update(){
        pos.vector2 = selectedComponent.getPosition();
        pos.update();


        scale.vector2 = selectedComponent.getScale();
        scale.update();

        validate();
        repaint();
    }

    public Component getSelectedComponent() {
        return selectedComponent;
    }
}
