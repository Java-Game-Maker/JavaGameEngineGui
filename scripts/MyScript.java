import javagameengine.components.*;
import javagameengine.msc.*;

public class MyScript extends Script {

    Component g;
    public float fart = 1;
    @Override
    public void start(){
        g = getParent();
    }

    @Override
    public void update(){
        super.update();
        if(this.g != null){
            this.g.setPosition(g.getPosition().add(Vector2.left.multiply(fart)));
        }

    }

}