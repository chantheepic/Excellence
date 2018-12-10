package cs3500.animator.provider;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.IModel;
import cs3500.animator.model.IModelToProviderAdapter;
import cs3500.animator.model.Model;
import cs3500.animator.model.State;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for IModelToProviderAdapter.
 */

public class IModelToProviderAdapterTest {

  IModel basicModel;
  State s;
  State t;
  IModelToProviderAdapter adapter;

  @Before
  public void setUp() {
    basicModel = Model.builder().build();
    s = new State(1, 2, 3, 4, 5, 6, 7);
    t = new State(11, 12, 13, 14, 15, 16, 17);
    adapter = new IModelToProviderAdapter(basicModel, 5);
  }

  @Test
  public void mutateIDs() {

    basicModel.addComponent("R", "rectangle",0);
    assertEquals(1, basicModel.getAllIds().size());
    basicModel.getAllIds().clear();
    assertEquals(1, basicModel.getAllIds().size());

  }

  @Test
  public void testSupported() {
    assertEquals(adapter.getTempo(), 5);
    assertEquals(adapter.getBounds().getHeight(), 500);
    assertEquals(adapter.getBounds().getWidth(), 500);
    assertEquals(adapter.getBounds().getX(), 0);
    assertEquals(adapter.getBounds().getY(), 0);
  }
}
