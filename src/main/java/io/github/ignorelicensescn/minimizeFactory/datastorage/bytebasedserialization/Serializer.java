package io.github.ignorelicensescn.minimizeFactory.datastorage.bytebasedserialization;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer<T> {

     void serialize(T t, OutputStream outTo);

     T deserialize(InputStream from);
}
