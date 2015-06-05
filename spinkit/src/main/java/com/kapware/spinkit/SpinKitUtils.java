package com.kapware.spinkit;

class SpinKitUtils {

    static float circular(float progress) {
        return progress - Math.round(progress + 0.5) + 1;
    }

    static float ease(KeySpline keySpline, float progress) {
        float progressEased = progress;
        if (progress < 0.25f) {
            progressEased = keySpline.get(progress) * 2;
            // TODO: Investigate why:
            if (progressEased > 0.25f) {
                progressEased = 0.25f;
            }
        } else if (progress < 0.5f) {
            progressEased = 0.25f + keySpline.get(progress - 0.25f) * 2;
            // TODO: Investigate why:
            if (progressEased > 0.5f) {
                progressEased = 0.5f;
            }

        } else if (progress < 0.75f) {
            progressEased = 0.5f + keySpline.get(progress - 0.5f) * 2;
            // TODO: Investigate why:
            if (progressEased > 0.75f) {
                progressEased = 0.75f;
            }

        } else if (progress < 1f) {
            progressEased = 0.75f + keySpline.get(progress - 0.75f) * 2;
            // TODO: Investigate why:
            if (progressEased > 1f) {
                progressEased = 1f;
            }
        }
        return progressEased;
    }

}
