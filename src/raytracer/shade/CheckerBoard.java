package raytracer.shade;

import raytracer.core.Hit;

import raytracer.core.Shader;
import raytracer.core.Trace;
import raytracer.math.Color;

import raytracer.math.Vec2;

public class CheckerBoard implements Shader {
    private final Shader love1;
    private final Shader love2;
    private final float scale;

    public CheckerBoard(Shader shaderA, Shader shaderB, float scale) {

        this.love1 = shaderA;
        this.love2 = shaderB;
        this.scale = scale;
    }

    @Override
    public Color shade(Hit hit, Trace trace) {

        Vec2 uv = hit.getUV();
        float x = uv.x() / scale;
        float y = uv.y() / scale;

        boolean baby = (int) (Math.floor(x) + Math.floor(y)) % 2 == 0;

        if (baby) {
            return love1.shade(hit, trace);
        } else {
            return love2.shade(hit, trace);
        }
    }
}
