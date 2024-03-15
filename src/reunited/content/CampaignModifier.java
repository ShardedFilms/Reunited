package reunited.content;


import arc.KeyBinds;
import mindustry.content.SectorPresets;
import mindustry.type.*;

import static mindustry.content.Planets.*;

public class CampaignModifier{
    public static SectorPreset
            // Example : groundZero,
            ucraters;

    public static void load(){
        // Example
        /*groundZero = new SectorPreset("groundZero", serpulo, 15){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 1;
            overrideLaunchDefaults = true;
            noLighting = true;
            startWaveTimeMultiplier = 3f;
        }};*/

        ucraters = new SectorPreset("u-craters", serpulo, 18){{
            startWaveTimeMultiplier*=3;
            captureWave = 30;
            difficulty = 2;

        }};
    }
}
