package raytracer.shade;

import raytracer.core.Hit;
import raytracer.core.Shader;
import raytracer.core.Trace;
import raytracer.core.LightSource;
import raytracer.math.Color;
import raytracer.math.Vec3;
import raytracer.math.Ray;

import java.util.Collection;

public class Phong implements Shader {
    private final Shader andar;
    private final Color lundd;
    private final float bhagjaa;
    private final float nahi;
    private final float chamak;

    public Phong(Shader inner, Color ambient, float diffuse, float specular, float shininess) {
        if (inner == null || ambient == null) {
            throw new IllegalArgumentException("iouDWEIUDWEHIU");
        }
        this.andar = inner;
        this.lundd = ambient;
        this.bhagjaa = diffuse;
        this.nahi = specular;
        this.chamak = shininess;
    }

    @Override
    public Color shade(Hit hit, Trace trace) {

        Color at = lundd.mul(andar.shade(hit, trace));

        Vec3 dv = trace.getRay().dir().neg();
        Vec3 seedha = hit.getNormal().normalized();

        Color talad = Color.BLACK;
        Color talas = Color.BLACK;

        Collection<LightSource> lightSources = trace.getScene().getLightSources();

        for (LightSource light : lightSources) {
            Vec3 lightDirection = light.getLocation().sub(hit.getPoint()).normalized();

            Ray shadowRay = new Ray(hit.getPoint(), lightDirection);
            Hit shadowHit = trace.getScene().hit(shadowRay);

            if (!shadowHit.hits() || shadowHit.getParameter() > hit.getPoint().sub(light.getLocation()).norm()) {

                float factor = Math.max(lightDirection.dot(seedha), 0);
                Color diffuseTerm = new Color(factor * bhagjaa, factor * bhagjaa,
                        factor * bhagjaa);
                talad = talad.add(diffuseTerm);

                Vec3 lundd = seedha.scale(2 * seedha.dot(lightDirection)).sub(lightDirection);
                float andaaa = (float) Math.pow(Math.max(lundd.dot(dv), 0), chamak);
                Color cash = new Color(andaaa * nahi, andaaa * nahi,
                        andaaa * nahi);
                talas = talas.add(cash);
            }
        }

        return at.add(talad.mul(andar.shade(hit, trace))).add(talas);
    }
}
