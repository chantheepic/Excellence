package cs3500.animator.provider.controller.interfaces;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.event.ChangeListener;

/**
 * Represents an interface that controllers must implement. It currently has no public methods as
 * all our methods were designed in relation to our implementation that was to be used with our
 * model. All controllers should use functions of ItemListeners, ActionListeners, KeyListeners,
 * and ChangeListeners however.
 */
public interface Controller extends ItemListener, ActionListener, KeyListener, ChangeListener,
    MouseListener {

}
