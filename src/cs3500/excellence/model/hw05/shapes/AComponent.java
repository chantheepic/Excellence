package cs3500.excellence.model.hw05.shapes;

import java.util.ArrayList;
import java.util.List;

import cs3500.excellence.model.hw05.IMotion;
import cs3500.excellence.model.hw05.State;

public abstract class AComponent implements IComponent {
  protected final List<IMotion> motions = new ArrayList<>();

  // each object created calculates its own position when given a tick. To do so, it creates a array that is of length <endTick> where
  // each index of the array represents a tick and the content of the array represents the state of this object at tick.
  // The states of the object between startTick and endTick is calculated when a new command is given.
  // Returning a overview is as simple as fetching the index wanted from the array


  @Override
  public void addMotion(State initialState, State endState, int initialTick, int endTick){
    if(initialTick != motions.get(motions.size()-1).)


    if(initialTick > endTick){
      throw new IllegalArgumentException("end tick must be greater than begin tick");
    }

    // extend and clone array when new command has endTick that is greater than that of previous endTick
    if(this.endTick < endTick){
      this.endTick = endTick;
      State[] newStates = new State[endTick + 1];
      System.arraycopy(states, 0, newStates, 0, states.length);
      states = newStates;
      System.out.println("new array created");
      System.out.println(states.length);
    }

    // check if new command given doesn't interfere with previously generated commands
    boolean spaceFree = true;
    for(int i = initialTick; i <= endTick; i++){
      if(states[i] != null){
        spaceFree = false;
        break;
      }
      System.out.println("space allocated");
    }

    // plug in values
    if(spaceFree){
      for(int tick = initialTick; tick <= endTick; tick++){
        int posX = initialState.x() + ((endState.x() - initialState.x()) / (endTick - initialTick)) * (tick - initialTick);
        int posY = initialState.y() + ((endState.y() - initialState.y()) / (endTick - initialTick)) * (tick - initialTick);
        int width = initialState.w() + ((endState.w() - initialState.w()) / (endTick - initialTick)) * (tick - initialTick);
        int height = initialState.h() + ((endState.h() - initialState.h()) / (endTick - initialTick)) * (tick - initialTick);
        int red = initialState.red() + ((endState.red() - initialState.red()) / (endTick - initialTick)) * (tick - initialTick);
        int green = initialState.green() + ((endState.green() - initialState.green()) / (endTick - initialTick)) * (tick - initialTick);
        int blue = initialState.blue() + ((endState.blue() - initialState.blue()) / (endTick - initialTick)) * (tick - initialTick);
        states[tick] = new State(width, height, posX, posY, red, green, blue);
        System.out.println("State created for tick " + tick);
        //System.out.println(posX + " " + posY + " " + red + " " + green + " " + blue);
      }
    } else{
      throw new IllegalArgumentException("There is already a pre-allocated move during time");
    }
  }

  // Returning a overview is as simple as fetching the index wanted from the array
  // If content does not exist in array at tick t, (i.e. command given from ticks 1~3 and 5~6, tick 4 has no generated state)
  // fetch last successfully created state (as if the object isn't moving during this period)
  @Override
  public State getStateAtTick(int tick){
    if(states.length > tick && states[tick] != null){
      return states[tick];
    } else{
      return this.getStateAtTick(tick - 1);
    }
  }

  @Override
  public String getOverview(int initial, int end){
    State initialState = this.getStateAtTick(initial);
    State endState = this.getStateAtTick(end);
    return initial + " " + initialState.x() + " " + initialState.y() + " " + initialState.red() + " " + initialState.green() + " " + initialState.blue() + " " + "\n" +
        endTick + " " + endState.x() + " " + endState.y() + " " + endState.red() + " " + endState.green() + " " + endState.blue();
  }
}
