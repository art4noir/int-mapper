package org.art4noir.intmapper;

public class IntMapper implements MutableIntMapper {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public int map(byte[] data, int from, int to) {
        return NOT_EXISTS;
    }

    @Override
    public int mapWithAppend(byte[] data, int from, int to) {
        return NOT_EXISTS;
    }

    @Override
    public ImmutableIntMapper.StoredData unmap(int code, ImmutableIntMapper.StoredData forReuse) {
        return null;
    }
}
