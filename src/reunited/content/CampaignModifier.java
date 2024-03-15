package reunited.content;

import mindustry.type.*;

import static mindustry.content.Planets.*;

public class CampaignModifier{
    public static SectorPreset
            // Example : groundZero,
            craters;

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


        craters = new SectorPreset("u-craters", serpulo, 18){{
            startWaveTimeMultiplier*=3;
            captureWave = 30;
            difficulty = 2;
        }};

    }
}
