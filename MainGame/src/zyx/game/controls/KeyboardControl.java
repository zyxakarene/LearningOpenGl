package zyx.game.controls;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Keyboard;

public class KeyboardControl
{

    private static final HashMap<Integer, Boolean> keyHoldingMap = new HashMap<>();
    private static final boolean[] presses = new boolean[Keyboard.KEYBOARD_SIZE];

    static
    {
        Arrays.fill(presses, false);
    }

    public static void listenForHolding(int keyId)
    {
        keyHoldingMap.put(keyId, false);
    }

    public static boolean wasKeyPressed(int keyId)
    {
        return presses[keyId];
    }

    public static boolean isKeyDown(int keyId)
    {
        if (keyHoldingMap.containsKey(keyId))
        {
            return keyHoldingMap.get(keyId);
        }

        return false;
    }

    public static void checkKeys()
    {
        resetKeys();
        checkNewKeys();
    }

    private static void resetKeys()
    {
        Arrays.fill(presses, false);

        for (Map.Entry<Integer, Boolean> entry : keyHoldingMap.entrySet())
        {
            Integer key = entry.getKey();

            keyHoldingMap.put(key, false);
        }
    }

    private static void checkNewKeys()
    {
        while (Keyboard.next())
        {
            if (Keyboard.getEventKeyState())
            {
                Integer keyId = Keyboard.getEventKey();
                presses[keyId] = true;
            }
        }
        
        for (Map.Entry<Integer, Boolean> entry : keyHoldingMap.entrySet())
        {
            Integer key = entry.getKey();

            keyHoldingMap.put(key, Keyboard.isKeyDown(key));
        }
    }
}
