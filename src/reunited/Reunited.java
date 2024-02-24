package reunited;

import mindustry.mod.*;
import reunited.content.UUnitTypes;
import reunited.gen.*;

public class Reunited extends Mod{
    public static final boolean DEBUGGING_SPRITE = false;
    public static String modname = "reunited";
    public static String author = "sharded_dev";
    @Override
    public void loadContent(){
        EntityRegistry.register();

        UUnitTypes.load();
    }
}
