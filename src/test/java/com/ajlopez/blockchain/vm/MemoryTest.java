package com.ajlopez.blockchain.vm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ajlopez on 26/11/2017.
 */
public class MemoryTest {
    @Test
    public void getZeroUndefinedValue() {
        Memory memory = new Memory();

        Assert.assertEquals(0, memory.getValue(0));
        Assert.assertEquals(0, memory.getValue(10));
        Assert.assertEquals(0, memory.getValue(100));
        Assert.assertEquals(0, memory.getValue(1_000_000));
    }

    @Test
    public void setAndGetValue() {
        Memory memory = new Memory();

        memory.setValue(10, (byte)42);

        Assert.assertEquals(0, memory.getValue(0));
        Assert.assertEquals(42, memory.getValue(10));
        Assert.assertEquals(0, memory.getValue(100));
    }

    @Test
    public void setAndGetValues() {
        Memory memory = new Memory();

        memory.setValues(10, new byte[] { 0x01, 0x02, 0x03 });

        Assert.assertEquals(0, memory.getValue(0));
        Assert.assertEquals(1, memory.getValue(10));
        Assert.assertEquals(2, memory.getValue(11));
        Assert.assertEquals(3, memory.getValue(12));
        Assert.assertEquals(0, memory.getValue(100));

        Assert.assertArrayEquals(new byte[] { 0x00, 0x01, 0x02, 0x03, 0x00 }, memory.getValues(9, 5));
    }
}
