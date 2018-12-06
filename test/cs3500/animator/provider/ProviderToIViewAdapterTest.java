package cs3500.animator.provider;

import static org.junit.Assert.assertEquals;

import cs3500.animator.provider.view.interfaces.EditorView;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests the ProviderToIViewAdapter. Only two methods are used so two methods are tested.
 */
public class ProviderToIViewAdapterTest {

  private EditorView adaptee = new EditorViewMock();


  @Test(expected = IllegalArgumentException.class)
  public void simpleTest() throws IOException {
    adaptee.display();
    adaptee.setTempo(10);
    EditorViewMock e = (EditorViewMock) adaptee;
    assertEquals(e.returnOutput(), "display on \n"
        + "tempo set to 10\n");
  }
}
