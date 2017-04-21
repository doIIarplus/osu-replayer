import SevenZip.Compression.RangeCoder.Encoder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main implements KeyListener{

    public static boolean isRunning = false;

    public static void main(String[] args) {
        MouseControl mc = new MouseControl();

        Replay replay = new Replay("fileName");

        String[] replayDataArray = replay.replayData.split(",");

        ArrayList<ReplayFrame> replayFrames = new ArrayList<ReplayFrame>();

        for (String s : replayDataArray)
        {
            //System.out.println(s);
            String[] split = s.split("\\|");
            System.out.println(split[0] + " " + split[1] + " " + split[2] + " " + split[3]);
            replayFrames.add(new ReplayFrame(Long.parseLong(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]), Integer.parseInt(split[3])));
        }

        for (int i = 0; i < replayFrames.size(); i++)
        {
            mc.moveMouse((int)(170.6667 + replayFrames.get(i).xCoord * 2.5625),(int) (56 + replayFrames.get(i).yCoord * 2.65625));

            if (i + 1 != replayFrames.size())
            {
                try{
                    Thread.sleep(replayFrames.get(i+1).milisSinceLastAction);
                } catch(Exception e)
                {

                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }
}
