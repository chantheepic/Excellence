import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.excellence.model.hw05.BasicMotion;
import cs3500.excellence.model.hw05.IModel;
import cs3500.excellence.model.hw05.Model;
import cs3500.excellence.model.hw05.State;
import cs3500.excellence.model.hw05.components.IComponent;
import cs3500.excellence.model.hw05.components.Rectangle;

import static org.junit.Assert.assertEquals;

public class BasicMoveModelTest {

  IModel basicModel;

  @Before
  public void setUp() {
    basicModel = new Model();
  }

  @Test
  public void mutateIDs() {

    basicModel.addComponent("R", new Rectangle());
    assertEquals(1,basicModel.getAllIds().size());
    basicModel.getAllIds().clear();
    assertEquals(1,basicModel.getAllIds().size());

  }

  @Test
  public void mutateThruByID() {

    basicModel.addComponent("R", new Rectangle());

    assertEquals(false, basicModel.getComponentByID("R").hasMotionAtTick(1));

    basicModel.getComponentByID("R").addMotion(new BasicMotion(new State(0,0,0,0,0,0,0),
            new State(0,0,0,0,0,0,0),1 ,1));
    assertEquals(false, basicModel.getComponentByID("R").hasMotionAtTick(1));
  }

}
