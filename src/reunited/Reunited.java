package reunited;

import mindustry.mod.*;
import reunited.content.*;
import reunited.gen.*;

public class Reunited extends Mod{
    public static final boolean DEBUGGING_SPRITE = true;
    public static String modname = "reunited";
    public static String author = "sharded_dev";
    @Override
    public void loadContent(){
        EntityRegistry.register();
        UUnitTypes.load();
    }
}
