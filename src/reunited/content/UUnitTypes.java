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
import reunited.entities.bullet.SlowRailBulletType;

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
                                recoil = 12f;
                                rotate = true;
                                shootCone = 20f;

                                inaccuracy = 0f;
                                rotateSpeed = 1f;
                                //shootSound = Sounds.missile;
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
                                    ammoMultiplier = 4f;
                                }};
                            }},
                    new Weapon(name + "-arcane"){{

                        x = -16f;
                        y=0f;
                        shootY = 12f;
                        reload = 84f;
                        recoil = 24f;
                        rotate = true;
                        shootCone = 5f;
                        inaccuracy = 0f;

                        rotateSpeed = 0.6f;
                        mirror=false;
                        //shootSound = Sounds.missile;
                        bullet = new SlowRailBulletType(20f, 720f){{
                            lifetime = 20f;
                            splashDamageRadius = 160f;
                            splashDamage = 1100f;
                            hitEffect = Fx.sapExplosion;
                            ammoMultiplier = 4f;
                            trailEffect = UFx.coloredRailgunSmallTrail;
                            trailSpacing = 15f;
                            backColor = trailColor = Pal.sapBulletBack;
                            frontColor = lightningColor = Pal.sapBullet;
                            lightning = 3;
                            lightningLength = 20;
                            smokeEffect = Fx.shootBigSmoke2;
                            hitShake = 10f;
                            lightRadius = 40f;
                            lightColor = Pal.sap;
                            lightOpacity = 0.6f;
                            width = 13f;
                            height = 27f;
                            shrinkY = 0f;
                            collidesAir = false;
                            scaleLife = true;
                            pierceCap = 2;

                            status = UStatusEffects.sappedMelting;
                            statusDuration = 60f * 10;

                            fragVelocityMin=1f;
                            fragLifeMin=0.2f;
                            fragBullets=18;
                            fragBullet = new ArtilleryBulletType(8, 30){{
                                hitEffect = Fx.sapExplosion;
                                knockback = 0.8f;
                                lifetime = 30f;
                                width = height = 30f;
                                collidesTiles = false;
                                splashDamageRadius = 110f;
                                splashDamage = 90f;
                                backColor = Pal.sapBulletBack;
                                frontColor = lightningColor = Pal.sapBullet;
                                lightning = 2;
                                lightningLength = 5;
                                smokeEffect = Fx.shootBigSmoke2;
                                hitShake = 5f;
                                lightRadius = 30f;
                                lightColor = Pal.sap;
                                lightOpacity = 0.5f;

                                status = StatusEffects.sapped;
                                statusDuration = 60f * 10;
                            }};
                        }};
                    }}

            );

        }};
    }
}
