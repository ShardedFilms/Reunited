package reunited.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import ent.anno.Annotations.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

/**
 * Content by Sharded.
 * You can give any sprites and I will code your imagination.
 */

public class UUnitTypes {
    public static @EntityDef({Unitc.class, Legsc.class}) UnitType arcaetana;
    public static void load(){

        arcaetana = new UnitType("arcaetana"){{
            drag = 0.1f;
            speed = 0.88f;
            hitSize = 80f;
            health = 192000;
            armor = 13f;
            lightRadius = 350f;

            rotateSpeed = 2f;
            drownTimeMultiplier = 10f;
            weapons.add(
                    weapons.add(new Weapon(name + "-gunner"){{
                                    layerOffset = -0.01f;

                                    x = 28f;
                                    y = 24f;
                                    shootY = 8f;
                                    reload = 6f;
                                    recoil = 8f;
                                    rotate = true;
                                    shootCone = 20f;
                                    inaccuracy = 2f;
                                    shoot.shots = 1;

                                    rotateSpeed = 2f;
                                    //shootSound = Sounds.missile;

                                    bullet = new BasicBulletType(10f, 24f){{
                                        width = 20f;
                                        height = 25f;
                                        shrinkY = 0.5f;
                                        splashDamageRadius = 80f;
                                        splashDamage = 100f;
                                        ammoMultiplier = 5f;
                                        hitEffect = Fx.sapExplosion;
                                        despawnEffect = Fx.sapExplosion;
                                        backColor = trailColor = Pal.sapBulletBack;
                                        frontColor = lightningColor = lightColor = Pal.sapBullet;
                                        lifetime = 26;
                                        lightning = 3;
                                        lightningLength = 10;
                                        health*=1.5f;


                                        status = StatusEffects.sapped;
                                        statusDuration = 120f;
                                    }};
                                }}
            )
            );

            legCount = 8;
            legGroupSize = 2;
            legMoveSpace = 0.7f;
            legPairOffset = 0.2f;
            legLength = 192f;
            legExtension = -30f;
            legBaseOffset = 12f;
            rippleScale = 3.4f;
            legSplashDamage = 260f;
            legSplashRange = 80f;
            ammoType = new ItemAmmoType(Items.silicon, 12);

            hovering = true;
            shadowElevation = 1.05f;
            groundLayer = Layer.legUnit + 0.03f;
            constructor = LegsUnit::create;
        }};
    }
}
