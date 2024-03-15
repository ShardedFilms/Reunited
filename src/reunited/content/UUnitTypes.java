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
import reunited.content.*;

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
    public static @EntityDef({Unitc.class, Legsc.class}) UnitType arcaetana;

    public static @EntityDef({Unitc.class}) UnitType zenith;
    public static void load(){

        zenith = new UnitType("zenith-boss"){{

            constructor = UnitEntity::create;
            health = 1400;
            speed = 1.8f;
            accel = 0.04f;
            drag = 0.016f;
            flying = true;
            range = 200f;
            hitSize = 40f;
            lowAltitude = true;
            forceMultiTarget = true;
            armor = 10f;
            // targetFlags = new BlockFlag[]{BlockFlag.launchPad, BlockFlag.storage, BlockFlag.battery, null};
            targetFlags = new BlockFlag[]{BlockFlag.generator, BlockFlag.turret, null};
            engineOffset = 22f;
            engineSize = 6f;
            ammoType = new ItemAmmoType(Items.graphite);

            weapons.add(new Weapon(name + "-missiles"){{
                reload = 36f;
                x = 11f;
                y = -1f;
                shootY =6f;
                rotate = true;
                shake = 1.2f;
                shoot= new ShootAlternate(){{
                    barrels=2;
                    spread =4f;
                    shots=4;
                    shotDelay =1;
                }};
                inaccuracy = 5f;
                velocityRnd = 0.1f;
                shootSound = Sounds.missile;

                bullet = new MissileBulletType(6f, 21){{
                    width = 8f;
                    height = 9f;
                    shrinkY = 0f;
                    drag = -0.003f;
                    homingRange = 80f;
                    keepVelocity = false;
                    splashDamageRadius = 32f;
                    splashDamage = 20f;
                    lifetime = 50f;
                    trailColor = Pal.unitBack;
                    backColor = Pal.unitBack;
                    frontColor = Pal.unitFront;
                    hitEffect = UFx.blastExplosion2;
                    despawnEffect = UFx.blastExplosion2;
                    weaveScale = 5f;
                    weaveMag = 1f;
                    sprite = "missile-large";
                }};
            }});
        }};

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
            immunities.add(StatusEffects.sapped);
            immunities.add(UStatusEffects.sappedMelting);
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
                                    recoil = 2f;
                                    rotate = true;
                                    shootCone = 20f;
                                    inaccuracy = 2f;
                                    shoot.shots = 2;
                                    shoot.shotDelay =3;

                                    rotateSpeed = 2f;
                                    //shootSound = Sounds.missile;
                                    bullet = new BasicBulletType(10f, 80f){{
                                        width = 20f;
                                        height = 25f;
                                        shrinkY = 0.5f;
                                        splashDamageRadius = 70f;
                                        splashDamage = 65f;
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
                                shootY = 4f;
                                reload = 40f;
                                recoil = 4f;
                                rotate = true;
                                shootCone = 20f;

                                inaccuracy = 0f;
                                rotateSpeed = 1f;
                                //shootSound = Sounds.missile;
                                bullet = new LaserBulletType(280f){{
                                    colors = new Color[]{Pal.sapBulletBack.cpy().a(0.4f), Pal.sapBullet, Color.white};
                                    length = 320f;
                                    width = 48f;
                                    sideAngle = 20f;
                                    sideWidth = 1.5f;
                                    sideLength = 100f;
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

                        x = 0f;
                        y=-16f;
                        shootY = 12f;
                        reload = 120f;
                        recoil = 12f;
                        rotate = true;
                        shootCone = 5f;
                        inaccuracy = 0f;

                        rotateSpeed = 0.6f;
                        mirror=false;
                        //shootSound = Sounds.missile;
                        bullet = new BasicBulletType(25f, 720f){{
                            lifetime = 22f;
                            splashDamageRadius = 160f;
                            splashDamage = 1100f;
                            hitEffect = Fx.sapExplosion;
                            ammoMultiplier = 4f;
                            backColor = trailColor = Pal.sapBulletBack;
                            frontColor = lightningColor = Pal.sapBullet;
                            lightning = 3;
                            lightningLength = 20;
                            smokeEffect = Fx.shootBigSmoke2;
                            hitShake = 10f;
                            lightRadius = 40f;
                            lightColor = Pal.sap;
                            lightOpacity = 0.6f;
                            width = 24f;
                            height = 48f;
                            shrinkY = 0f;
                            collidesAir = true;
                            pierceCap = 2;
                            buildingDamageMultiplier = 3f;
                            backMove = reflectable = false;
                            pierceBuilding=true;
                            pierce=true;

                            status = UStatusEffects.sappedMelting;
                            statusDuration = 60f * 10;

                            fragLifeMin=1f;
                            fragVelocityMin=0.1f;
                            fragBullets=18;
                            fragBullet = new ArtilleryBulletType(8, 30){{
                                hitEffect = Fx.sapExplosion;
                                knockback = 0.8f;
                                lifetime = 30f;
                                width = height = 30f;
                                collidesTiles = true;
                                collidesAir=true;
                                splashDamageRadius = 110f;
                                splashDamage = 90f;
                                backColor = Pal.sapBulletBack;
                                buildingDamageMultiplier = 2f;
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
