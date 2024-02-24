package reunited.tools;

import arc.Core;
import arc.files.Fi;
import arc.func.Boolf;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.PixmapIO;
import arc.graphics.Pixmaps;
import arc.graphics.g2d.PixmapRegion;
import arc.graphics.g2d.TextureAtlas;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.struct.ObjectMap;
import arc.util.Log;
import arc.util.OS;
import arc.util.noise.Noise;
import arc.util.noise.Ridged;
import arc.util.noise.VoronoiNoise;
import mindustry.Vars;
import mindustry.gen.Tankc;
import mindustry.graphics.MultiPacker;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import reunited.Reunited;

import java.io.IOException;

import static mindustry.io.SaveIO.save;


/**
 *
 * This was taken from NewHorizonMod to generate full.
 *
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class UnityPixmaps{
    public static final String PCD_SUFFIX = "-processed";

    public static boolean isDebugging(){return Reunited.DEBUGGING_SPRITE;}

    public static ObjectMap<String, Pixmap> processed = new ObjectMap<>();

    public static Pixmap addProcessed(String name, Pixmap pixmap){
        processed.put(name, pixmap);

        return pixmap;
    }

    public static Pixmap outLineAndAdd(String name, PixmapRegion pixmap, Color color, int radius){
        return addProcessed(name, Pixmaps.outline(pixmap, color, radius));
    }

    public static Pixmap outLineAndAdd(String name, TextureRegion pixmap, Color color, int radius){
        return outLineAndAdd(name, Core.atlas.getPixmap(pixmap), color, radius);
    }

    public static void packOutlineAndAdd(MultiPacker packer, String name, TextureRegion pixmap, Color color, int radius){
        packer.add(MultiPacker.PageType.main, name, outLineAndAdd(name, pixmap, color, radius));
    }

    public static void packAndAdd(MultiPacker packer, String name, Pixmap pixmap){
        packer.add(MultiPacker.PageType.main, name, addProcessed(name, pixmap));
    }

    public static final Color OUTLINE_COLOR = Color.valueOf("565666");

    public static void outlineLegs(MultiPacker packer, UnitType type){
        if(isDebugging() && !Vars.headless){
            Color color = type.outlineColor;
            if(type.legRegion.found())packer.add(MultiPacker.PageType.main, type.name + "-leg", Pixmaps.outline(Core.atlas.getPixmap(type.legRegion), color, type.outlineRadius));
            if(type.jointRegion.found())packer.add(MultiPacker.PageType.main, type.name + "-joint", Pixmaps.outline(Core.atlas.getPixmap(type.jointRegion), color, type.outlineRadius));
            if(type.baseJointRegion.found())packer.add(MultiPacker.PageType.main, type.name + "-joint-base", Pixmaps.outline(Core.atlas.getPixmap(type.baseJointRegion), color, type.outlineRadius));
            if(type.footRegion.found())packer.add(MultiPacker.PageType.main, type.name + "-foot", Pixmaps.outline(Core.atlas.getPixmap(type.footRegion), color, type.outlineRadius));
            if(type.legBaseRegion.found())packer.add(MultiPacker.PageType.main, type.name + "-leg-base", Pixmaps.outline(Core.atlas.getPixmap(type.legBaseRegion), color, type.outlineRadius));
            if(type.baseRegion.found())packer.add(MultiPacker.PageType.main, type.name + "-base", Pixmaps.outline(Core.atlas.getPixmap(type.baseRegion), color, type.outlineRadius));
        }
    }

    public static void createIcons(MultiPacker packer, UnitType type){
        if(!Vars.headless && isDebugging()){
            TextureAtlas.AtlasRegion t = (TextureAtlas.AtlasRegion)type.region;

            PixmapRegion r = Core.atlas.getPixmap(Core.atlas.find(type.name));

            Pixmap base = new Pixmap(type.region.width, type.region.height);

            if(type.constructor.get() instanceof Tankc){
                base.draw(Core.atlas.getPixmap(type.treadRegion));
            }

//			base.draw(r.crop(), true);
            base.draw(replaceColor(r.crop(), type.outlineColor, Color.clear), true);

            base.draw(replaceColor(Core.atlas.getPixmap(type.cellRegion), ObjectMap.of((Boolf<Color>) c -> c.equals(Color.white), Color.valueOf("ffa664"), (Boolf<Color>) c -> c.equals(Color.valueOf("dcc6c6")), Color.valueOf("dc804e"))), 0, 0, true);

            for(Weapon w : type.weapons){
                if(w.top)continue;
                drawWeaponPixmap(base, w, false, type.outlineColor, type.outlineRadius);
            }

            base = Pixmaps.outline(new PixmapRegion(base), type.outlineColor, type.outlineRadius);

            for(Weapon w : type.weapons){
                if(!w.top)continue;
                drawWeaponPixmap(base, w, true, type.outlineColor, type.outlineRadius);
            }

            if(Core.settings.getBool("linear")){
                Pixmaps.bleed(base);
            }

            //used to debug

            packAndAdd(packer, type.name + "-full", base);

            Rand rand = new Rand();
            rand.setSeed(type.name.hashCode());

            //generate random wrecks

            int splits = 3;
            float degrees = rand.random(360f);
            float offsetRange = Math.max(type.region.width, type.region.height) * 0.15f;
            Vec2 offset = new Vec2(1, 1).rotate(rand.random(360f)).setLength(rand.random(0, offsetRange)).add(type.region.width/2f, type.region.height/2f);

            Pixmap[] wrecks = new Pixmap[splits];
            for(int i = 0; i < wrecks.length; i++){
                wrecks[i] = new Pixmap(type.region.width, type.region.height);
            }

            VoronoiNoise vn = new VoronoiNoise(type.id, true);

            type.region.scl((int x, int y) -> {
                //add darker cracks on top
                boolean rValue = Math.max(Ridged.noise2d(1, x, y, 3, 1f / (20f + type.region.width/8f)), 0) > 0.16f;
                //cut out random chunks with voronoi
                boolean vval = vn.noise(x, y, 1f / (14f + type.region.width/40f)) > 0.47;

                float dst =  offset.dst(x, y);
                //distort edges with random noise
                float noise = (float) Noise.rawNoise(dst / (9f + type.region.width/70f)) * (60 + type.region.width/30f);
                int section = (int)Mathf.clamp(Mathf.mod(offset.angleTo(x, y) + noise + degrees, 360f) / 360f * splits, 0, splits - 1);
                if(!vval) wrecks[section].setRaw(x, y, Color.muli(packer.getRaw(x, y), rValue ? 0.7f : 1f));
            });

            for(int i = 0; i < wrecks.length; i++){
                packAndAdd(wrecks[i], "../rubble/" + type.name + "-wreck" + i);
            }



        }
    }

    public static Pixmap replaceColor(Pixmap pixmap, Color from, Color to){

        int f = from.rgba8888();
        int t = to.rgba8888();

        for(int y = 0; y < pixmap.height; ++y){
            for(int x = 0; x < pixmap.width; ++x){
                int c = pixmap.get(x, y);
                if(c == f)pixmap.set(x, y, t);
            }
        }

        return pixmap;
    }

    public static Pixmap replaceColor(PixmapRegion pixmap, ObjectMap<Boolf<Color>, Color> map){
        Pixmap base = new Pixmap(pixmap.width, pixmap.height);
        Color color = new Color();

        for(int y = 0; y < pixmap.height; ++y){
            for(int x = 0; x < pixmap.width; ++x){
                pixmap.get(x, y, color);
                if(Mathf.zero(color.a))continue;
                for(Boolf<Color> filter : map.keys()){
                    if(filter.get(color)){
                        base.set(x, y, map.get(filter));
                        break;
                    }
                }
            }
        }

        return base;
    }

    public static Pixmap fillColor(PixmapRegion pixmap, Color replaceColor){
        Pixmap base = new Pixmap(pixmap.width, pixmap.height);
        Color color = new Color();

        if(color.a < 1.0F){
            for(int y = 0; y < pixmap.height; ++y){
                for(int x = 0; x < pixmap.width; ++x){
                    pixmap.get(x, y, color);
                    base.set(pixmap.width - x, y, color.mul(replaceColor));
                }
            }
        }

        return base;
    }

    public static void mulColor(Pixmap pixmap, Color color){
        Color c = new Color();

        for(int y = 0; y < pixmap.height; ++y){
            for(int x = 0; x < pixmap.width; ++x){
                c.set(pixmap.get(x, y));
                pixmap.set(x, y, c.mul(color));
            }
        }
    }

    @Deprecated
    public static void drawWeaponPixmap(Pixmap base, Weapon w, boolean outline, Color outlineColor, int radius){
        TextureRegion region = Core.atlas.find(w.name + "-preview", w.region);

        if(region != null && region.found() && region instanceof TextureAtlas.AtlasRegion){
            TextureAtlas.AtlasRegion t = (TextureAtlas.AtlasRegion)region;
            if(!t.found())return;

            Pixmap wRegion = region != w.region ? Pixmaps.outline(Core.atlas.getPixmap(t), outlineColor, radius) : Core.atlas.getPixmap(t).crop();

            if(w.mirror){
                Pixmap wRegion2 = wRegion.flipX();
                base.draw(wRegion, getCenter(base, wRegion, true, outline) + (int)(w.x * 4), getCenter(base, wRegion, false, outline) - (int)(w.y * 4), true);
                base.draw(wRegion2, getCenter(base, wRegion2, true, outline) - (int)(w.x * 4), getCenter(base, wRegion2, false, outline) - (int)(w.y * 4), true);
            }else{
                base.draw(wRegion, getCenter(base, wRegion, true, outline) + (int)(w.x * 4), getCenter(base, wRegion, false, outline) - (int)(w.y * 4), true);
            }
        }
    }

    public static int getCenter(Pixmap base, Pixmap above, boolean WorH, boolean outline){
        return (WorH ? (base.getWidth() - above.getWidth()) / 2 : (base.getHeight() - above.getHeight()) / 2);
    }

    public static String rootPath(){
        return OS.env("MDT_SPRITE_HOME");
    }

    public static String path(String p){
        return rootPath() + p;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static Fi processedDir(){
        Fi dic = new Fi(path("/pre-processed"));
        if(!dic.exists())dic.mkdirs();
        return dic;
    }

    public static Fi processedPng(String fileName, String suffix){
        return new Fi(rootPath() + "/pre-processed/" + fileName.replaceAll(Reunited.modname + "-", "") + suffix + ".png");
    }

    public static void saveUnitPixmap(Pixmap pixmap, UnitType type){
        Fi dic = new Fi(rootPath() + "/pre-processed");
        if(!dic.exists())dic.mkdirs();
        if(dic.exists()){
            Fi n = processedPng(type.name, "-full");
            if(n.exists())return;
            if(!n.exists())try{n.file().createNewFile();}catch(IOException e){Log.err(e);}
            Fi w1 = processedPng(type.name, "-wreck1");
            if(w1.exists())return;
            if(!w1.exists())try{n.file().createNewFile();}catch(IOException e){Log.err(e);}
            Fi w2 = processedPng(type.name, "-wreck2");
            if(w2.exists())return;
            if(!w2.exists())try{n.file().createNewFile();}catch(IOException e){Log.err(e);}
            Fi w3 = processedPng(type.name, "-wreck3");
            if(w3.exists())return;
            if(!w3.exists())try{n.file().createNewFile();}catch(IOException e){Log.err(e);}
            PixmapIO.writePng(n, pixmap);
            Log.info("Created Icon: " + type.localizedName);
        }

    }


    public static void saveAddProcessed(){
        processedDir();

        for(ObjectMap.Entry<String, Pixmap> entry : processed.entries()){
            Fi n = processedPng(entry.key, "");
            PixmapIO.writePng(n, entry.value);
            Log.info("Created Icon: " + entry.key);
        }

    }
}