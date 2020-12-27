package org.art4noir.intmapper;

import java.io.IOException;

public interface MutableIntMapper extends ImmutableIntMapper {
    int mapWithAppend(byte[] data, int from, int to) throws IOException;

    default int mapWithAppend(byte[] data) throws IOException {
        return mapWithAppend(data, 0, data.length);
    }

    default int mapWithAppend(String data) throws IOException {
        byte[] bytes = data.getBytes();
        return mapWithAppend(bytes, 0, bytes.length);
    }

    default int mapWithAppend(String data, String charset) throws IOException {
        byte[] bytes = data.getBytes(charset);
        return mapWithAppend(bytes, 0, bytes.length);
    }
}
