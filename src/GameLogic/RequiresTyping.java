package GameLogic;

/**
 * This interface identifies classes which would like to receive universal keypresses
 * The object will be forwarded key presses if it has a fleetingLock from the InputManager
 * @author Wesley Chiou
 *
 */
public interface RequiresTyping{
	public abstract void processUniversalKeyPress(int key,char c);
}