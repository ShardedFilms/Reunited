package reunited.content;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.*;

public class UStatusEffects {

    public static StatusEffect sappedMelting;
    public static void load() {

        sappedMelting = new StatusEffect("sapped-melting"){{
            color = Pal.sapBulletBack;
            speedMultiplier = 0.6f;
            healthMultiplier = 0.6f;
            damage = 1f;
            effect = UFx.sapMelt;
            effectChance = 0.2f;

            init(() -> {
                opposite(StatusEffects.wet, StatusEffects.freezing);
                affinity(StatusEffects.tarred, (unit, result, time) -> {
                    unit.damagePierce(8f);
                    Fx.burning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(StatusEffects.melting, Math.min(time + result.time, 200f));
                });
                affinity(StatusEffects.sapped, (unit, result, time) -> {
                    unit.damagePierce(10f);
                    UFx.sapBurning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                });
            });
        }};
    }
    }
