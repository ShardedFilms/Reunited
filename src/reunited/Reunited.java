package reunited;

import mindustry.mod.*;
import reunited.gen.*;

public class Reunited extends Mod{
    public static final boolean DEBUGGING_SPRITE = false;
    public static String modname = "reunited";
    @Override
    public void loadContent(){
        EntityRegistry.register();
    }
}
