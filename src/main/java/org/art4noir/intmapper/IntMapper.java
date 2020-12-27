package org.art4noir.intmapper;

public class IntMapper implements MutableIntMapper {
    private final MutableIntMapperImpl mutable = new MutableIntMapperImpl();

    @Override
    public int size() {
        return mutable.size();
    }

    @Override
    public int map(byte[] data, int from, int to) {
        return mutable.map(data, from, to);
    }

    @Override
    public int mapWithAppend(byte[] data, int from, int to) {
        return mutable.mapWithAppend(data, from, to);
    }

    @Override
    public ImmutableIntMapper.StoredData unmap(int code, ImmutableIntMapper.StoredData forReuse) {
        return mutable.unmap(code, forReuse);
    }
}
