import com.asd.javagameengine.components.Component;
import com.asd.javagameengine.msc.Vector2;
import com.asd.javagameengine.input.Input;
import com.asd.javagameengine.input.Keys;

public class MyScript extends Component {
	public float speed = 1f;

	@Override
	public void update(){
		super.update();
		if(Input.isKeyDown(Keys.LEFTARROW))
			getParent().setPosition(getParent().getPosition().add(Vector2.left.multiply(speed)));
		if(Input.isKeyDown(Keys.RIGHTARROW))
			getParent().setPosition(getParent().getPosition().add(Vector2.right.multiply(speed)));
		if(Input.isKeyDown(Keys.DOWNARROW))
			getParent().setPosition(getParent().getPosition().add(Vector2.down.multiply(speed)));
		if(Input.isKeyDown(Keys.UPARROW))
			getParent().setPosition(getParent().getPosition().add(Vector2.up.multiply(speed)));
	}
}