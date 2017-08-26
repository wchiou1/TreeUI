package GameLogic;

/**
 * This class identifies classes which would like to receive universal keypresses
 * The object will be foc
 * @author Wesley Chiou
 *
 */
public interface RequiresTyping{
	public abstract void processUniversalKeyPress(int key,char c);
}