package com.kapware.spinkit;

/**
 * Ported from js:
 * http://greweb.me/2012/02/bezier-curve-based-easing-functions-from-concept-to-implementation/
 */
class KeySpline {
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public KeySpline(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float get(float aX) {
        if (this.x1 == this.y1 && this.x2 == this.y2) {
            return aX; // linear
        }
        return calcBezier(getTForX(aX), this.y1, this.y2);
    }

    private float A(float aA1, float aA2) {
        return 1.0f - 3.0f * aA2 + 3.0f * aA1;
    }
    private float B(float aA1, float aA2) {
        return 3.0f * aA2 - 6.0f * aA1;
    }
    private float C(float aA1) {
        return 3.0f * aA1;
    }

    // Returns x(t) given t, x1, and x2, or y(t) given t, y1, and y2.
    private float calcBezier(float aT, float aA1, float aA2) {
        return ((A(aA1, aA2)*aT + B(aA1, aA2))*aT + C(aA1))*aT;
    }

    // Returns dx/dt given t, x1, and x2, or dy/dt given t, y1, and y2.
    private float getSlope(float aT, float aA1, float aA2) {
        return 3.0f * A(aA1, aA2)*aT*aT + 2.0f * B(aA1, aA2) * aT + C(aA1);
    }

    private float getTForX(float aX) {
        // Newton Raphson iteration
        float aGuessT = aX;
        for (int i = 0; i < 4; i++) {
            float currentSlope = getSlope(aGuessT, this.x1, this.x2);
            if (currentSlope == 0.0) {
                return aGuessT;
            }
            float currentX = calcBezier(aGuessT, this.x1, this.x2) - aX;
            aGuessT -= currentX / currentSlope;
        }
        return aGuessT;
    }
}