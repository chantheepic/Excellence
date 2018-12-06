README

*Overall Design*

The Model has list of Components. Each Component has a list of Motions. We use a final "primitive"
class called State to represent the current parameters of a component. The model can add Components,
add motions to components, get which components are visible at certain ticks, and get the textual
overview.


1. IModel:
    - Motions need to be added in chronological order with no overlaps, and matching end -> start
      points.
    - When implementing an IModel the getComponentsAtTick should return a copy of each IComponent.
      This is to prevent users from directly mutating the IComponents.
    - Get overview with return each Shape in alphabetical order, followed by its motions in
      chronological order.

2. IComponent
    -

3. IMotion
    -


*Design Choices*

1. The user MUST input in chronological order. This can be changed using the builder pattern to
   construct IComponents, but currently we do not want the IComponent to be in an illegal state.

2. The model returns a List<IComponents> instead of a generic collection (a set). Although there is
   no specified order, we assumed that the order should be alphabetical. This makes testing slightly
   easier because we do not need to configure an equals method YET.

3. We use a class called State to hold the parameter information. We understand that this makes
   it harder to later on add more Component parameters but it allows us to pass around that
   information in a distinct object.

4. To add more component types we need to extend AComponent, we did this to make it easier to add
   different types. We could have had an Enum representing the different available types, but that
   would require more work to extend.

5. The main utilizes a builder and a factory so that the arguments can be provided out of order.

6. Each view handles the separate drawing of the different shapes. Because the process used to
   draw the shapes is different for each view, no abstraction was possible. Even within each view
   because the parameters and calculations done to draw each shape was different
   (i.e. center point for rect and ellipse in svgView), Abstracting the calculations done to
   different shapes were difficult.

==[HW07]==

7. We decided to have the Controller catch errors that might be thrown when mutating the model. The
   controller then delegates these errors to the view to display whoever it chooses. This means
   IView has a displayError(str) method.

8. The EditView takes care of low level callbacks, when something meaningful needs to happen, it
   calls methods defined in the Features interface. This listener is currently the controller.

9. The user has the option to delete or create keyframes, we decided to not mutate the model when
   a user just wants to "insert" a keyframe. This is because it doesn't change the animation. The
   only time the model should care is when that keyframe alters the animation.

*Future Revisions*
    - Factory for creating components
    - Builder for adding motions to components

*CHANGES*

  ==[HW06]==

  - Need to hold order that shapes were added, dictionary that holds order??
      -> or need to use a list instead of a map.
      ->UPDATE: linked hashmap is what we need.

  - New method needed to retrieve all components of a shape. The views need a list of components.

  ==[HW07]==

  - The model now stores keyframes as its data structure. It still has methods that can return
    motions. We did this because holding on to motions is slightly less space efficient. In addition
    mutating the model with respect to keyframes is easier when the data is already in keyframe form.

  - Have a new class called Keyframe which holds onto a State and a tick. This class is completely
    immutable.

  - IView added: tick(int), setListener(listener), displayError(str). Each of these methods were
    added for the edit view. displayError(str) is used in the other views when they want to display
    an error. tick(int) and setListener(listener) are do absolutely nothing in the text, svg  and
    visual view.

