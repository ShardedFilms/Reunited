package reunited.content;

import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public final class UFx{
    public static final Effect
    // particle

            sapMelt = new Effect(12, e -> {
        color(Pal.sapBullet);
        stroke(e.fout() * 2f);

        randLenVectors(e.id, 6, e.finpow() * 18f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1f);
        });
    }),
         sapBurning = new Effect(35f, e -> {
             color(Pal.sapBullet, Pal.sapBulletBack, e.fin());

             randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
                 Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
             });
         }),

    // Shoot

            sapPlasmaShoot = new Effect(25f, e -> {
        color(Color.white, Pal.sapBullet, e.fin());
        randLenVectors(e.id, 13, e.finpow() * 20f, e.rotation, 23f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
            Fill.circle(e.x + x / 1.2f, e.y + y / 1.2f, e.fout() * 3f);
        });
    }),

    // Trail

            coloredRailgunTrail = new Effect(30f, e -> {
        for(int i = 0; i < 2; i++){
            int sign = Mathf.signs[i];
            color(e.color);
            Drawf.tri(e.x, e.y, 10f * e.fout(), 24f, e.rotation + 90f + 90f * sign);
        }
    }),

    coloredRailgunSmallTrail = new Effect(30f, e -> {
        for(int i = 0; i < 2; i++){
            int sign = Mathf.signs[i];
            color(e.color);
            Drawf.tri(e.x, e.y, 5f * e.fout(), 12f, e.rotation + 90f + 90f * sign);
        }
    }),

    coloredArrowTrail = new Effect(40f, 80f, e -> {
        Tmp.v1.trns(e.rotation, 5f * e.fout());
        color(e.color);
        for(int s : Mathf.signs){
            Tmp.v2.trns(e.rotation - 90f, 9f * s * ((e.fout() + 2f) / 3f), -20f);
            Fill.tri(Tmp.v1.x + e.x, Tmp.v1.y + e.y, -Tmp.v1.x + e.x, -Tmp.v1.y + e.y, Tmp.v2.x + e.x, Tmp.v2.y + e.y);
        }
    });
    // AssertionError
    private UFx(){
        throw new AssertionError();
    }
}