package org.art4noir.intmapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

public interface ImmutableIntMapper {
    int NOT_EXISTS = Integer.MIN_VALUE;

    int size();

    int map(byte[] data, int from, int to) throws IOException;

    default int map(byte[] data) throws IOException {
        return map(data, 0, data.length);
    }

    default int map(String data) throws IOException {
        byte[] bytes = data.getBytes(Charset.defaultCharset().name());
        return map(bytes, 0, bytes.length);
    }

    default int map(String data, String charset) throws IOException {
        byte[] bytes = data.getBytes(charset);
        return map(bytes, 0, bytes.length);
    }

    StoredData unmap(int code, StoredData forReuse) throws IOException;

    default byte[] unmapAsBytes(int code) throws IOException {
        return unmap(code, null).asBytes();
    }

    default byte[] unmapAsBytes(int code, StoredData forReuse) throws IOException {
        return unmap(code, forReuse).asBytes();
    }

    default String unmapAsString(int code) throws IOException {
        return unmap(code, null).asString();
    }

    default String unmapAsString(int code, StoredData forReuse) throws IOException {
        return unmap(code, forReuse).asString();
    }

    default String unmapAsString(int code, Charset charset) throws IOException {
        return unmap(code, null).asString(charset);
    }

    default String unmapAsString(int code, Charset charset, StoredData forReuse) throws IOException {
        return unmap(code, forReuse).asString(charset);
    }

    class StoredData {
        public StoredData(byte[] data, int from, int to) {
            this.data = data;
            this.from = from;
            this.to = to;
        }

        private final byte[] data;
        private final int from;
        private final int to;

        public byte[] asBytes() {
            if (from == 0 && to == data.length) {
                return data;
            }
            return Arrays.copyOfRange(data, from, to);
        }

        public String asString() {
            return new String(data, from, to - from);
        }

        public String asString(Charset charset) {
            return new String(data, from, to - from, charset);
        }
    }
}