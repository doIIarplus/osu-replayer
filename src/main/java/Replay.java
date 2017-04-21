import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.primitives.Ints;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jasper on 4/19/2017.
 */
public class Replay {

    public Enums.GameMode gameMode;
    public String fileName;
    public int fileFormat;
    public String mapHash;
    public String playerName;
    public String replayHash;
    public int totalScore;
    public int count300;
    public int count100;
    public int count50;
    public int countGeki;
    public int countKatu;
    public int countMiss;
    public int maxCombo;
    public boolean isPerfect;
    public Enums.Mods mods;
    public ArrayList<LifeFrame> lifeFrames = new ArrayList<LifeFrame>();
    public Date playTime;
    public int seed;

    private LittleEndianDataInputStream fileData;

    private boolean isHeaderLoaded;
    private boolean isFullyLoaded;

    public String replayData;

    public Replay(String file)
    {
        fileName = file;
        isFullyLoaded = false;

        try {
            fileData = new LittleEndianDataInputStream(new FileInputStream(fileName));
        } catch (Exception e)
        {
            System.out.println("SHIET");
        }

        loadHeader();

        if (!isFullyLoaded)
            load();
    }


    private void loadHeader()
    {
        try {
            gameMode = Enums.GameMode.parse(fileData.readByte());
            fileFormat = fileData.readInt();
            mapHash = readString(fileData, getNextStringLength(fileData), 0);

            playerName = readString(fileData, getNextStringLength(fileData), 0);

            replayHash = readString(fileData, getNextStringLength(fileData), 0);
            count300 = fileData.readShort();
            count100 = fileData.readShort();
            count50 = fileData.readShort();
            countGeki = fileData.readShort();
            countKatu = fileData.readShort();
            countMiss = fileData.readShort();
            totalScore = fileData.readInt();
            maxCombo = fileData.readShort();
            isPerfect = fileData.readBoolean();
            mods = Enums.Mods.parse(fileData.readInt());


            System.out.println("Game mode: " + gameMode);
            System.out.println("Game Version: " + fileFormat);
            System.out.println("Map Hash: " + mapHash);
            System.out.println("Player: " + playerName);
            System.out.println("Replay Hash: " + replayHash);
            System.out.println("Count 300: " + count300);
            System.out.println("Count 100: " + count100);
            System.out.println("Count 50: " + count50);
            System.out.println("Count Geki: " + countGeki);
            System.out.println("Count Katu: " + countKatu);
            System.out.println("Count Miss: " + countMiss);
            System.out.println("Total Score: " + totalScore);
            System.out.println("Max Combo: " + maxCombo);
            System.out.println("Is Perfect: " + isPerfect);
            System.out.println("Mods: " + mods);

            isHeaderLoaded = true;

        } catch (IOException e)
        {
            System.out.println("shoot em");
        }
    }

    private void load()
    {
        if (!isHeaderLoaded)
            loadHeader();
        if (isFullyLoaded)
            return;



        try {
            // Life
            Integer lifeDataLength = getNextStringLength(fileData);
            String lifeData = readString(fileData, lifeDataLength, 0);

            long ticks = fileData.readLong();
            playTime = new Date(ticks);

            // Replay data
            int replayLength = fileData.readInt();
            byte[] compressedReplayData = new byte[replayLength];
            fileData.readFully(compressedReplayData);

            replayData = LZMAUtil.decompress(compressedReplayData);

            System.out.println(replayData);
            System.out.println(lifeData);
            System.out.println(playTime);
            System.out.println(replayLength);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private int getNextStringLength(LittleEndianDataInputStream is)
    {
        try {
            byte b = is.readByte();

            if (b != 0x0b)
            {
                return 0;
            }

            int s = LEB128.readUnsignedLeb128(is);
            System.out.println(s);
            return s;
        } catch (Exception e)
        {
            System.out.println("ohoho");
            return 0;
        }
    }

    private String readString(LittleEndianDataInputStream is, int length, int offset)
    {
        byte b[] = new byte[length + offset];

        try{
            is.readFully(b);
        } catch (Exception e)
        {
            System.out.println("SHIET");
        }


        String s = new String(b).substring(offset);

        return s;
    }

}
