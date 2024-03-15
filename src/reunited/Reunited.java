package reunited;

import arc.Core;
import mindustry.*;
import mindustry.mod.*;
import reunited.content.*;
import reunited.gen.*;
import reunited.tools.UnityPixmaps;

public class Reunited extends Mod{
    public static final boolean DEBUGGING_SPRITE = true;
    public static String modname = "reunited";
    public static String author = "sharded_dev";




    @Override
    public void loadContent(){
        EntityRegistry.register();
        UStatusEffects.load();
        UUnitTypes.load();
        CampaignModifier.load();
    }

    public void generate() {
            Vars.content.units().each(u -> {
                if(u.name.contains(Reunited.modname)){
                    UnityPixmaps.saveUnitPixmap(Core.atlas.getPixmap(u.fullIcon).crop(), u);
                }
            });

            UnityPixmaps.saveAddProcessed();

    }
}
