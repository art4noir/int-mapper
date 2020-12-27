package org.art4noir.intmapper;

import java.util.concurrent.atomic.AtomicInteger;

public class MutableIntMapperImpl implements MutableIntMapper {
    private static final int UNINITIALIZED = Integer.MIN_VALUE + 1;

    private final HashToPositions hashToPositions = new HashToPositions();
    private final DataBlock dataBlock = new DataBlock();
    private final AtomicInteger nextCode = new AtomicInteger(0);

    @Override
    public int mapWithAppend(byte[] data, int from, int to) {
        int h = hash(data, from, to);
        int mapped = map(data, from, to);
        if (mapped != NOT_EXISTS) {
            return mapped;
        }
        int newPos = dataBlock.append(data, from, to);
        hashToPositions.addPos(h, newPos);

        // check if other thread added same data same time
        int pos = HashToPositions.NO_PREV;
        do {
            pos = hashToPositions.nextPos(h, pos);
            if (pos != newPos) {
                int code = dataBlock.codeIfEquals(pos, data, from, to);
                if (code == NOT_EXISTS) {
                    continue;
                }
                if (code == UNINITIALIZED) {
                    while ((code = dataBlock.getCode(pos)) == UNINITIALIZED) {
                        Thread.yield();
                    }
                }
                return code;
            }
        } while (pos != newPos);
        int code = nextCode.getAndIncrement();
        dataBlock.setCode(newPos, code);
        return code;
    }

    @Override
    public int map(byte[] data, int from, int to) {
        // compute hash for given data
        int h = hash(data, from, to);
        // get positions of stored data with same hash
        int pos = HashToPositions.NO_PREV;
        do {
            pos = hashToPositions.nextPos(h, pos);
            int code = dataBlock.codeIfEquals(pos, data, from, to);
            if (code != NOT_EXISTS) {
                return code;
            }
        } while (pos != HashToPositions.NOT_EXIST);
        return NOT_EXISTS;
    }

    @Override
    public StoredData unmap(int code, StoredData forReuse) {
        return dataBlock.getData(code, forReuse);
    }

    private int hash(byte[] data, int from, int to) {
        int h = 0;
        for (int i = from; i < to; i++) {
            h = 31 * h + data[i];
        }
        return h;
    }

    @Override
    public int size() {
        return nextCode.get();
    }

    static class HashToPositions {
        static int NO_PREV = Integer.MIN_VALUE;
        static int NOT_EXIST = Integer.MAX_VALUE;

        int nextPos(int h, int prevPos) {
            throw new RuntimeException("Not implemented");
        }

        void addPos(int h, int pos) {
            throw new RuntimeException("Not implemented");
        }
    }

    static class DataBlock {
        int codeIfEquals(int pos, byte[] data, int from, int to) {
            throw new RuntimeException("Not implemented");
        }

        int append(byte[] data, int from, int to) {
            throw new RuntimeException("Not implemented");
        }

        void setCode(int newPos, int code) {
            throw new RuntimeException("Not implemented");
        }

        int getCode(int pos) {
            throw new RuntimeException("Not implemented");
        }

        int getPos(int code) {
            throw new RuntimeException("Not implemented");
        }

        StoredData getData(int code, StoredData forReuse) {
            throw new RuntimeException("Not implemented");
        }
    }
}
