/*package reunited.type;

import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.environment.*;
import reunited.entities.*;
import reunited.gen.*;
import reunited.type.decal.*;
import reunited.type.decal.DecorationUnitType.*;
import reunited.util.*;

import static arc.Core.*;
import static mindustry.Vars.content;

@SuppressWarnings("unchecked")
public class UnityUnitType extends UnitType{
    public final Seq<Weapon> segWeapSeq = new Seq<>();

    public TextureRegion segmentRegion, tailRegion, segmentCellRegion, segmentOutline, tailOutline,
            legBackRegion, legBaseBackRegion, footBackRegion, legMiddleRegion, legShadowRegion, legShadowBaseRegion,
            payloadCellRegion;
    public Seq<Weapon> bottomWeapons = new Seq<>();


    // Worms
    public WormDecal wormDecal;
    public int segmentLength = 9, maxSegments = -1;
    //Should reduce the "Whip" effect.
    public int segmentCast = 4;
    public float segmentOffset = 23f, headOffset = 0f;
    public float angleLimit = 30f;
    public float regenTime = -1f, healthDistribution = 0.1f;
    public float segmentDamageScl = 6f;
    public float anglePhysicsSmooth = 0f;
    public float jointStrength = 1f;
    public float barrageRange = 150f;
    // Hopefully make segment movement more consistent
    public boolean counterDrag = false;
    // Attempt to prevent angle drifting due to the inaccurate Atan2
    public boolean preventDrifting = false;
    public boolean splittable = false, chainable = false;
    public Sound splitSound = Sounds.door, chainSound = Sounds.door;
    /**
     * Weapons for each segment.
     * Last item of the array is the tail's weapon.
     */
/*
    public Seq<Weapon>[] segmentWeapons;

    public Func<Unit, Trail> trailType = unit -> new Trail(trailLength);
    // Decoration
    public Seq<DecorationUnitType> decorations = new Seq<>();


    // Transforms
    public Func<Unit, UnitType> toTrans;
    public Boolf<Unit> transPred = unit -> {
        Floor floor = unit.floorOn();
        return floor.isLiquid && !(floor instanceof ShallowLiquid) ^ unit instanceof WaterMovec;
    };
    public float transformTime;
    // Decoration
    public float rotorDeathSlowdown = 0.01f;
    public float fallRotateSpeed = 2.5f;

    // For shoot armor ability
    public FloatSeq weaponXs = new FloatSeq();

    // Legs extra
    protected static Vec2 legOffsetB = new Vec2();
    protected static float[][] jointOffsets = new float[2][2];


    public boolean customBackLegs = false;
    public boolean legShadows = false;

    // Worm Rendering
    private final static Rect viewport = new Rect(), viewport2 = new Rect();
    private final static int chunks = 4;

    // Linked units
    public UnityUnitType linkType;
    public int linkCount = 1;
    public float rotationSpeed = 20f;

    // Monolith units
    public int maxSouls = 3;

    protected boolean immuneAll = false;

    boolean wormCreating = false;

    // Imber units
    public float laserRange = -1f;
    public int maxConnections = -1;

    // World units
    public int worldWidth, worldHeight;
    public boolean forceWreckRegion;

    public UnityUnitType(String name){
        super(name);
        outlines = false;
    }

    @Override
    public Unit create(Team team){
        Unit unit = super.create(team);

        Class<?> caller = ReflectUtils.classCaller();
        boolean fromWave = caller != null && SpawnGroup.class.isAssignableFrom(caller);


        if(!wormCreating && unit instanceof Wormc){
            wormCreating = true;
            var cur = (Unit & Wormc)unit;
            int cid = unit.id;
            //Tmp.v1.trns(unit.rotation + 180f, segmentOffset + headOffset).add(unit);
            for(int i = 0; i < segmentLength; i++){
                var t = (Unit & Wormc)create(team);
                t.elevation = unit.elevation;

                t.layer(1f + i);
                t.head(unit);
                t.parent(cur);

                cur.child(t);
                cur.childId(cid);
                cur.headId(unit.id);

                int idx = i >= segmentLength - 1 ? segmentWeapons.length - 1 : i % Math.max(1, segmentWeapons.length - 1);

                t.weaponIdx((byte)idx);
                t.setupWeapons(this);
                cid = t.id;
                cur = t;
            }

            wormCreating = false;
        }

        return unit;
    }

    @Override
    public void load(){
        super.load();

        //worm
        if(wormDecal != null) wormDecal.load();
        segmentRegion = atlas.find(name + "-segment");
        segmentCellRegion = atlas.find(name + "-segment-cell", cellRegion);
        tailRegion = atlas.find(name + "-tail");
        segmentOutline = atlas.find(name + "-segment-outline");
        tailOutline = atlas.find(name + "-tail-outline");
        legBackRegion = atlas.find(name + "-leg-back");
        legBaseBackRegion = atlas.find(name + "-leg-base-back");
        footBackRegion = atlas.find(name + "-foot-back");
        legMiddleRegion = atlas.find(name + "-leg-middle", legRegion);

        legShadowRegion = atlas.find(name + "-leg-shadow", legRegion);
        legShadowBaseRegion = atlas.find(name + "-leg-base-shadow", legBaseRegion);

        payloadCellRegion = atlas.find(name + "-cell-payload", cellRegion);

        //abilities


        decorations.each(DecorationUnitType::load);
        if(segmentWeapons == null){
            segWeapSeq.each(Weapon::load);
        }else{
            for(Seq<Weapon> seq : segmentWeapons){
                seq.each(Weapon::load);
            }
        }
    }

    @Override
    public void init(){
        super.init();

        weapons.each(w -> weaponXs.add(w.x));

        //worm
        if(segmentWeapons == null){
            sortSegWeapons(segWeapSeq);
            segmentWeapons = new Seq[]{segWeapSeq};
        }else{
            for(Seq<Weapon> seq : segmentWeapons){
                sortSegWeapons(seq);
            }
        }

        Seq<Weapon> addBottoms = new Seq<>();
        for(Weapon w : weapons){
            if(bottomWeapons.contains(w) && w.otherSide != -1){
                addBottoms.add(weapons.get(w.otherSide));
            }
        }

        bottomWeapons.addAll(addBottoms.distinct());

        if(immuneAll){
            immunities.addAll(content.getBy(ContentType.status));
        }
    }

    public void sortSegWeapons(Seq<Weapon> weaponSeq){
        Seq<Weapon> mapped = new Seq<>();
        for(int i = 0, len = weaponSeq.size; i < len; i++){
            Weapon w = weaponSeq.get(i);
            if(w.recoilTime < 0f){
                w.recoilTime = w.reload;
            }
            mapped.add(w);

            if(w.mirror){
                Weapon copy = w.copy();
                copy.x *= -1;
                copy.shootX *= -1;
                copy.flipSprite = !copy.flipSprite;
                mapped.add(copy);

                w.reload *= 2;
                copy.reload *= 2;
                w.recoilTime *= 2;
                copy.recoilTime *= 2;
                w.otherSide = mapped.size - 1;
                copy.otherSide = mapped.size - 2;
            }
        }

        weaponSeq.set(mapped);
    }

    public <T extends Unit & Wormc> void drawWorm(T unit){
        Mechc mech = unit instanceof Mechc ? (Mechc)unit : null;
        float z = (unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f)) - (unit.layer() * 0.00001f);

        if(unit.isFlying() || shadowElevation > 0){
            TextureRegion tmpShadow = shadowRegion;
            if(!unit.isHead() || unit.isTail()){
                shadowRegion = unit.isTail() ? tailRegion : segmentRegion;
            }

            Draw.z(Math.min(Layer.darkness, z - 1f));
            drawShadow(unit);
            shadowRegion = tmpShadow;
        }

        Draw.z(z - 0.02f);
        if(mech != null){
            drawMech(mech);

            //side
            legOffsetB.trns(mech.baseRotation(), 0f, Mathf.lerp(Mathf.sin(mech.walkExtend(true), 2f/Mathf.PI, 1) * mechSideSway, 0f, unit.elevation));

            //front
            legOffsetB.add(Tmp.v1.trns(mech.baseRotation() + 90, 0f, Mathf.lerp(Mathf.sin(mech.walkExtend(true), 1f/Mathf.PI, 1) * mechFrontSway, 0f, unit.elevation)));

            unit.trns(legOffsetB.x, legOffsetB.y);
        }
        if(unit instanceof Legsc){
            drawLegs((Unit & Legsc)unit);
        }

        Draw.z(Math.min(z - 0.01f, Layer.bullet - 1f));

        if(unit instanceof Payloadc){
            drawPayload((Unit & Payloadc)unit);
        }

        drawSoftShadow(unit);

        Draw.z(z);

        TextureRegion tmp = region, tmpOutline = outlineRegion, tmpCell = cellRegion;
        if(!unit.isHead() || unit.isTail()){
            region = unit.isTail() ? tailRegion : segmentRegion;
            outlineRegion = unit.isTail() ? tailOutline : segmentOutline;
        }
        if(!unit.isHead()) cellRegion = segmentCellRegion;

        drawOutline(unit);
        drawWeaponOutlines(unit);

        if(unit.isTail() && unit.layer() < maxSegments){
            Draw.draw(z, () -> {
                Tmp.v1.trns(unit.rotation + 180f, segmentOffset).add(unit);
                Drawf.construct(Tmp.v1.x, Tmp.v1.y, tailRegion, unit.rotation - 90f, unit.regenTime() / regenTime, 1f, unit.regenTime());
            });
        }

        drawBody(unit);
        if(drawCell && !unit.isTail()) drawCell(unit);
        if(wormDecal != null) wormDecal.draw(unit, unit.parent());

        cellRegion = tmpCell;
        region = tmp;
        outlineRegion = tmpOutline;

        drawWeapons(unit);

        if(unit.shieldAlpha > 0 && drawShields){
            drawShield(unit);
        }

        if(mech != null){
            unit.trns(-legOffsetB.x, -legOffsetB.y);
        }

        if(unit.abilities.length > 0){
            for(Ability a : unit.abilities){
                Draw.reset();
                a.draw(unit);
            }

            Draw.reset();
        }
    }

    @Override
    public void draw(Unit unit){
        if(unit instanceof Wormc w && !w.isHead()){
            drawWorm((Unit & Wormc)w);
        }else{
            super.draw(unit);
        }

    }

    @Override
    public Color cellColor(Unit unit){
            return super.cellColor(unit);
    }

    @Override
    public void drawCell(Unit unit) {
        if(unit.isAdded()){
            super.drawCell(unit);
        }else{
            applyColor(unit);

            Draw.color(cellColor(unit));
            Draw.rect(payloadCellRegion, unit.x, unit.y, unit.rotation - 90);
            Draw.reset();
        }
    }



    @Override
    public void drawShadow(Unit unit){
        super.drawShadow(unit);
        if(unit instanceof Worm wormUnit) wormUnit.drawShadow();
    }

    @Override
    public void drawSoftShadow(Unit unit){

        super.drawSoftShadow(unit);
        //worm
        if(!(unit instanceof Worm wormUnit)) return;
        /*for(WormSegmentUnit s : wormUnit.segmentUnits){
            wormUnit.type.drawSoftShadow(s);
        }*/
/*
        float z = Draw.z();
        for(int i = 0; i < wormUnit.segmentUnits.length; i++){
            Draw.z(z - (i + 1.1f) / 10000f);
            wormUnit.type.drawSoftShadow(wormUnit.segmentUnits[i]);
        }
        Draw.z(z);
    }

    @Override
    public void drawOutline(Unit unit){
        if(unit instanceof Decorationc d){
            for(UnitDecoration decor : d.decors()){
                if(!decor.type.top) decor.type.draw(unit, decor);
            }
        }
        super.drawOutline(unit);
    }

    @Override
    public void drawBody(Unit unit){
        float z = Draw.z();
        super.drawBody(unit);
        //worm
        if(unit instanceof Worm wormUnit){
            camera.bounds(viewport);
            int index = -chunks;
            for(int i = 0; i < wormUnit.segmentUnits.length; i++){
                if(i >= index + chunks){
                    index = i;
                    Unit seg = wormUnit.segmentUnits[index];
                    Unit segN = wormUnit.segmentUnits[Math.min(index + chunks, wormUnit.segmentUnits.length - 1)];
                    float grow = wormUnit.regenAvailable() && (index + chunks) >= wormUnit.segmentUnits.length - 1 ? seg.clipSize() : 0f;
                    Tmp.r3.setCentered(segN.x, segN.y, segN.clipSize());
                    viewport2.setCentered(seg.x, seg.y, seg.clipSize()).merge(Tmp.r3).grow(grow + (seg.clipSize() / 2f));
                }
                if(viewport.overlaps(viewport2)){
                    Draw.z(z - (i + 1f) / 10000f);
                    if(wormUnit.regenAvailable() && i == wormUnit.segmentUnits.length - 1){
                        int finalI = i;
                        Draw.draw(z - (i + 2f) / 10000f, () -> {
                            Tmp.v1.trns(wormUnit.segmentUnits[finalI].rotation + 180f, segmentOffset).add(wormUnit.segmentUnits[finalI]);
                            Drawf.construct(Tmp.v1.x, Tmp.v1.y, tailRegion, wormUnit.segmentUnits[finalI].rotation - 90f, wormUnit.repairTime / regenTime, 1f, wormUnit.repairTime);
                        });
                    }
                    wormUnit.segmentUnits[i].drawBody();
                    drawWeapons(wormUnit.segmentUnits[i]);
                }
            }
        }

        Draw.z(z);
    }

    @Override
    public void drawWeapons(Unit unit){
        float z = Draw.z();

        applyColor(unit);
        for(WeaponMount mount : unit.mounts){
            Weapon weapon = mount.weapon;
            if(bottomWeapons.contains(weapon)) Draw.z(z - 0.0001f);

            weapon.draw(unit, mount);
            Draw.z(z);
        }

        Draw.reset();
    }

}*/
