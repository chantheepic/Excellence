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

*Future Revisions*
    - Factory for creating components
    - Builder for adding motions to components

*CHANGES*

    - Need to hold order that shapes were added, dictionary that holds order??
        -> or need to use a list instead of a map.
        ->UPDATE: linked hashmap is what we need.