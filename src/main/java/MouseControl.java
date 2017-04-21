import java.awt.*;

/**
 * Created by Jasper on 4/19/2017.
 */
public class MouseControl {

    private Robot mouseController;

    public MouseControl()
    {
        try {
            mouseController = new Robot();
        } catch (Exception e)
        {
            System.out.println("SHIET");
        }
    }

    public void moveMouse(int x, int y)
    {
        mouseController.mouseMove(x, y);
    }
}
