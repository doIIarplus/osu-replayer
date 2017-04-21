/**
 * Created by Jasper on 4/20/2017.
 */
public class ReplayFrame {
    public long milisSinceLastAction;
    public float xCoord;
    public float yCoord;
    public int buttonsPressed;

    public ReplayFrame(long ms, float x, float y, int button)
    {
        this.milisSinceLastAction = ms;
        this.xCoord = x;
        this.yCoord = y;
        this.buttonsPressed = button;
    }
}
