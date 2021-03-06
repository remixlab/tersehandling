package action_driven;

import remixlab.bias.*;
import remixlab.bias.event.*;
import processing.core.*;

/**
 * Created by pierre on 12/22/16.
 */
public class ActionDrivenCallback extends PApplet {
  MouseAgent agent;
  InputHandler inputHandler;
  Ellipse [] ellipses;

  public static final int LEFT_ID = MotionShortcut.registerID(37, 2, "LEFT"), CENTER_ID = MotionShortcut
      .registerID(3, 2, "CENTER"), RIGHT_ID = MotionShortcut.registerID(39, 2, "RIGHT"), WHEEL_ID = MotionShortcut
      .registerID(8, 1, "WHEEL"), NO_BUTTON = MotionShortcut
      .registerID(BogusEvent.NO_ID, 2, "NO_BUTTON"), LEFT_CLICK_ID = ClickShortcut
      .registerID(LEFT_ID, "LEFT"), RIGHT_CLICK_ID = ClickShortcut
      .registerID(RIGHT_ID, "RIGHT"), CENTER_CLICK_ID = ClickShortcut.registerID(CENTER_ID, "CENTER");

  public void settings() {
    size(800, 800);
  }

  public void setup() {
    inputHandler = new InputHandler();
    agent = new MouseAgent(inputHandler);
    registerMethod("mouseEvent", agent);
    ellipses = new Ellipse[50];
    for (int i = 0; i < ellipses.length; i++)
      ellipses[i] = new Ellipse(this, inputHandler);
  }

  public void draw() {
    background(255);
    for (int i = 0; i < ellipses.length; i++) {
      if ( ellipses[i].grabsInput(agent) )
        ellipses[i].draw(color(255, 0, 0));
      else
        ellipses[i].draw();
    }
    inputHandler.handle();
  }

  public void keyPressed() {
    if (key == ' ') {
      agent.click2Pick = !agent.click2Pick;
      agent.resetTrackedGrabber();
      for (int i = 0; i < ellipses.length; i++)
        if (agent.click2Pick)
          ellipses[i].setMouseMoveBindings();
        else
          ellipses[i].setMouseDragBindings();
    }
  }

  public static void main(String args[]) {
    PApplet.main(new String[]{"action_driven.ActionDrivenCallback"});
  }
}
