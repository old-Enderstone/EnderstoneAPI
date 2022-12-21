package net.enderstone.api.common.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IStreamSerializable {

    void serialize(final OutputStream output) throws IOException;
    void deserialize(final InputStream data) throws IOException;

}
