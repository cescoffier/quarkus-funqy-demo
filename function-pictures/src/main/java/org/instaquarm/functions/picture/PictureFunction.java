package org.instaquarm.functions.picture;

import io.quarkus.funqy.Funq;
import jakarta.inject.Inject;

public class PictureFunction {

    @Inject PictureRepository repository;

    @Funq
    public Picture getLast() {
        return repository.getLast();
    }

}
