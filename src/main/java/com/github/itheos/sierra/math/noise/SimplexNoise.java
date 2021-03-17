package com.github.itheos.sierra.math.noise;

import org.jetbrains.annotations.NotNull;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public class SimplexNoise extends Noise {

    private static final int PSIZE = 2048;
    private static final int PMASK = 2047;

    private short[] perm;
    private Grad2[] permGrad2;

    public SimplexNoise(int seed) {
        super(seed);

        perm = new short[PSIZE];
        permGrad2 = new Grad2[PSIZE];

        short[] source = new short[PSIZE];

        for (short i = 0; i < PSIZE; i++)
            source[i] = i;

        long lSeed = seed;
        for (int i = PSIZE - 1; i >= 0; i--) {
            lSeed = lSeed * 6364136223846793005L + 1442695040888963407L;

            int r = (int) ((lSeed + 31) % (i + 1));
            if (r < 0)
                r += (i + 1);

            perm[i] = source[r];
            permGrad2[i] = GRADIENTS_2D[perm[i]];
            source[r] = source[i];
        }
    }

    /*
     * Noise Evaluators
     */

    /**
     * 2D Simplex noise, standard lattice orientation
     *
     * @return a value between -1 and 1
     */
    @NotNull
    @Override
    public float[] evaluate(float x, float y) {
        // Get points for A2* lattice
        float s = 0.366025403784439f * (x + y);
        float xs = x + s, ys = y + s;

        return new float[] { noise2_Base(xs, ys), 0.0f, 0.0f };
    }

    @Override
    public @NotNull float[] evaluate(float x, float y, float z) {
        return new float[0];
    }

    /**
     * 2D Simplex noise base.
     * Lookup table implementation inspired by DigitalShadow.
     */
    private float noise2_Base(float xs, float ys) {
        float value = 0;

        // Get base points and offsets
        int xsb = (int) Math.floor(xs), ysb = (int) Math.floor(ys);
        float xsi = xs - xsb, ysi = ys - ysb;

        // Index to point list
        int index = (int)((ysi - xsi) / 2 + 1);

        float ssi = (xsi + ysi) * -0.211324865405187f;
        float xi = xsi + ssi, yi = ysi + ssi;

        // Point contributions
        for (int i = 0; i < 3; i++) {
            LatticePoint2D c = LOOKUP_2D[index + i];

            float dx = xi + c.dx, dy = yi + c.dy;
            float attn = 0.5f - dx * dx - dy * dy;
            if (attn <= 0) continue;

            int pxm = (xsb + c.xsv) & PMASK, pym = (ysb + c.ysv) & PMASK;
            Grad2 grad = permGrad2[perm[pxm] ^ pym];
            float extrapolation = grad.dx * dx + grad.dy * dy;

            attn *= attn;
            value += attn * attn * extrapolation;
        }

        return value;
    }

    /*
     * Definitions
     */

    private static final LatticePoint2D[] LOOKUP_2D;

    static {
        LOOKUP_2D = new LatticePoint2D[4];

        LOOKUP_2D[0] = new LatticePoint2D(1, 0);
        LOOKUP_2D[1] = new LatticePoint2D(0, 0);
        LOOKUP_2D[2] = new LatticePoint2D(1, 1);
        LOOKUP_2D[3] = new LatticePoint2D(0, 1);
    }

    private static class LatticePoint2D {
        int xsv, ysv;
        float dx, dy;

        public LatticePoint2D(int xsv, int ysv) {
            this.xsv = xsv; this.ysv = ysv;
            float ssv = (xsv + ysv) * -0.211324865405187f;
            this.dx = -xsv - ssv;
            this.dy = -ysv - ssv;
        }
    }

    /*
     * Gradients
     */

    private static class Grad2 {
        float dx, dy;

        public Grad2(float dx, float dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private static final float N2 = 0.01001634121365712f;
    private static final Grad2[] GRADIENTS_2D;

    static {
        GRADIENTS_2D = new Grad2[PSIZE];
        Grad2[] grad2 = {
                new Grad2(0.130526192220052f, 0.99144486137381f),
                new Grad2(0.38268343236509f, 0.923879532511287f),
                new Grad2(0.608761429008721f, 0.793353340291235f),
                new Grad2(0.793353340291235f, 0.608761429008721f),
                new Grad2(0.923879532511287f, 0.38268343236509f),
                new Grad2(0.99144486137381f, 0.130526192220051f),
                new Grad2(0.99144486137381f, -0.130526192220051f),
                new Grad2(0.923879532511287f, -0.38268343236509f),
                new Grad2(0.793353340291235f, -0.60876142900872f),
                new Grad2(0.608761429008721f, -0.793353340291235f),
                new Grad2(0.38268343236509f, -0.923879532511287f),
                new Grad2(0.130526192220052f, -0.99144486137381f),
                new Grad2(-0.130526192220052f, -0.99144486137381f),
                new Grad2(-0.38268343236509f, -0.923879532511287f),
                new Grad2(-0.608761429008721f, -0.793353340291235f),
                new Grad2(-0.793353340291235f, -0.608761429008721f),
                new Grad2(-0.923879532511287f, -0.38268343236509f),
                new Grad2(-0.99144486137381f, -0.130526192220052f),
                new Grad2(-0.99144486137381f, 0.130526192220051f),
                new Grad2(-0.923879532511287f, 0.38268343236509f),
                new Grad2(-0.793353340291235f, 0.608761429008721f),
                new Grad2(-0.608761429008721f, 0.793353340291235f),
                new Grad2(-0.38268343236509f, 0.923879532511287f),
                new Grad2(-0.130526192220052f, 0.99144486137381f)
        };

        for (Grad2 value : grad2) {
            value.dx /= N2;
            value.dy /= N2;
        }

        for (int i = 0; i < PSIZE; i++)
            GRADIENTS_2D[i] = grad2[i % grad2.length];
    }

}
