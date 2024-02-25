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
    public static UnitType arcaetana;
    public static void load(){

        arcaetana = new UnitType("arcaetana"){{
            drag = 0.1f;
            speed = 0.88f;
            hitSize = 80f;
            health = 192000;
            armor = 26f;
            lightRadius = 350f;

            rotateSpeed = 1.2f;
            drownTimeMultiplier = 10f;
            weapons.add(
new Weapon(name + "-gunner"){{

                                    x = 28f;
                                    y = 24f;
                                    shootY = 8f;
                                    reload = 6f;
                                    recoil = 8f;
                                    rotate = true;
                                    shootCone = 20f;
                                    inaccuracy = 2f;
                                    shoot.shots = 2;
                                    shoot.shotDelay =3;

                                    rotateSpeed = 2f;
                                    //shootSound = Sounds.missile;

                                    mirror=true;
                                    alternate=true;
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
                                        lifetime = 36;
                                        lightning = 3;
                                        lightningLength = 10;

                                        status = StatusEffects.sapped;
                                        statusDuration = 120f;
                                    }};
                                }},
                            new Weapon(name + "-laser-cannon"){{

                                x = 24f;
                                y = 0f;
                                shootY = 24f;
                                reload = 40f;
                                recoil = 8f;
                                rotate = true;
                                shootCone = 20f;

                                inaccuracy = 0f;
                                rotateSpeed = 1f;
                                //shootSound = Sounds.missile;

                                mirror=true;
                                alternate=true;
                                bullet = new LaserBulletType(280f){{
                                    colors = new Color[]{Pal.sapBulletBack.cpy().a(0.4f), Pal.sapBullet, Color.white};
                                    length = 320f;
                                    width = 30f;
                                    sideAngle = 20f;
                                    sideWidth = 2.5f;
                                    sideLength = 90f;
                                    shootEffect = UFx.sapPlasmaShoot;
                                    hitColor = lightColor = lightningColor = Pal.sapBullet;
                                    status = StatusEffects.sapped;
                                    statusDuration = 180f;
                                    lightningSpacing = 16f;
                                    lightningDelay = 0.1f;
                                    lightningDamage = 28f;
                                    lightningLength = 5;
                                    lightningLengthRand = 2;
                                    lightningAngleRand = 15f;
                                    ammoMultiplier = 2f;
                                }};
                            }}
            
            );

            legCount = 8;
            legGroupSize = 2;
            legMoveSpace = 0.7f;
            legPairOffset = 0.2f;
            legLength = 192f;
            legExtension = -20f;
            legBaseOffset = 16f;
            rippleScale = 3.4f;
            legSplashDamage = 260f;
            legSplashRange = 120f;
            ammoType = new ItemAmmoType(Items.silicon, 12);

            hovering = true;
            shadowElevation = 1.05f;
            groundLayer = Layer.legUnit + 0.03f;
            constructor = LegsUnit::create;
        }};
    }
}
