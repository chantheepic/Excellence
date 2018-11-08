package cs3500.excellence.model;

import java.util.List;
import java.util.Set;

import cs3500.excellence.model.components.IROComponent;

public interface IROModel {


  /**
   * Returns a copied list of components that are visible at a given tick.
   *
   * @param tick - the given tick.
   */
  List<IROComponent> getComponentsAtTick(int tick);

  /**
   * Gets a list of all Component IDs.
   */
  Set<String> getAllIds();

  /**
   * Gets the component based on a given ID. Cannot mutate component and have side effects reflected
   * in model.
   *
   * @param id - unique identifier to search for
   * @return Component if it exists
   * @throws IllegalArgumentException if id doesn't exist
   */
  IROComponent getComponentByID(String id) throws IllegalArgumentException;


  List<IROComponent> getAllComponents();

  int[] getBoundary();

  String getOverview();

}
