/**
 * Created by Jasper on 4/19/2017.
 */
public class Enums {

    public enum GameMode{
        OSU     (0),
        TAIKO   (1),
        CTB     (2),
        MANIA   (3);

        private final int val;

        GameMode(int val)
        {
            this.val = val;
        }

        public int value()
        {
            return this.val;
        }

        public static GameMode parse(Byte val)
        {
            for (GameMode g : GameMode.values())
            {
                if (g.value() == val)
                {
                    return g;
                }
            }

            return OSU;
        }
    }

    public enum Mods{
        NONE        (0),
        NOFAIL      (1 << 0),
        EASY        (1 << 1),
        NOVIDEO     (1 << 2),
        HIDDEN      (1 << 3),
        HARDROCK    (1 << 4),
        SUDDENDEATH (1 << 5),
        DOUBLETIME  (1 << 6),
        RELAX       (1 << 7),
        HALFTIME    (1 << 8),
        NIGHTCORE   (1 << 9),
        FLASHLIGHT  (1 << 10),
        AUTO        (1 << 11),
        SPUNOUT     (1 << 12),
        AUTOPILOT   (1 << 13),
        PERFECT     (1 << 14),
        MANIA4K     (1 << 15),
        MANIA5K     (1 << 16),
        MANIA6K     (1 << 17),
        MANIA7K     (1 << 18),
        MANIA8K     (1 << 19);

        private final int val;

        Mods(int val)
        {
            this.val = val;
        }

        public int value()
        {
            return this.val;
        }

        public static Mods parse(int value)
        {
            for (Mods m : Mods.values())
            {
                if (m.value() == value)
                {
                    return m;
                }
            }

            return NONE;
        }
    }

    public enum Keys{
        NONE    (0),
        M1      (1 << 0),
        M2      (1 << 1),
        K1      ((1 << 2) | (1 << 0)),
        K2      ((1 << 3) | (1 << 1));

        private final int val;

        Keys(int val)
        {
            this.val = val;
        }

        public int value()
        {
            return this.val;
        }
    }

}
