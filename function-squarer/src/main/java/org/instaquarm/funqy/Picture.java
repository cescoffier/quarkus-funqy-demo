package org.instaquarm.funqy;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Picture {

    public final long time;
    public final byte[] picture;

    public Picture(long time, byte[] picture) {
        this.time = time;
        this.picture = picture;
    }
}
