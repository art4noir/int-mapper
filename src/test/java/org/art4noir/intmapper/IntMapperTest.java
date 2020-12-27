package org.art4noir.intmapper;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class IntMapperTest {

    @Test
    public void testStandardWork() throws IOException {
        IntMapper mapper = new IntMapper();

        Assert.assertEquals(mapper.size(), 0);
        Assert.assertEquals(mapper.map("0"), IntMapper.NOT_EXISTS);

        int code0 = mapper.mapWithAppend("0");
        Assert.assertEquals(code0, 0);
        int code1 = mapper.mapWithAppend("1");
        Assert.assertEquals(code1, 1);
        int code2 = mapper.mapWithAppend("2");
        Assert.assertEquals(code2, 2);
        Assert.assertEquals(mapper.size(), 3);

        Assert.assertEquals(mapper.unmapAsString(0), "0");
        Assert.assertEquals(mapper.unmapAsString(1), "1");
        Assert.assertEquals(mapper.unmapAsString(2), "2");
        Assert.assertEquals(mapper.unmapAsString(3), "3");
    }
}
