package io.github.ignorelicensescn.minimizefactory.datastorage.database.operators.abstracts;

import io.github.ignorelicensescn.minimizefactory.datastorage.database.operators.preparedstatementoperations.implementations.SerializeFriendlyBlockLocationSetter;
import io.github.ignorelicensescn.minimizefactory.datastorage.machinenetwork.SerializeFriendlyBlockLocation;

import static io.github.ignorelicensescn.minimizefactory.datastorage.database.types.Column.KEY_COLUMN;

public class LocationBasedColumnRemover extends ColumnRemover<SerializeFriendlyBlockLocation>{
    public LocationBasedColumnRemover(String keyName) {
        super(keyName, SerializeFriendlyBlockLocationSetter.INSTANCE);
    }
}
