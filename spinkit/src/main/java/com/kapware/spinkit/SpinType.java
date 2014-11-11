package com.kapware.spinkit;

public enum SpinType {
    WANDERING_CUBES(0),
    WANDERING_CIRCLES(1)
    ;

    private int mId;

    private SpinType(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public static SpinType findById(int needle) {
        for (SpinType spinType: values()) {
            if (spinType.getId() == needle) {
                return spinType;
            }
        }
        return WANDERING_CUBES;
    }
}
