package com.ajlopez.blockchain.vm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ajlopez on 20/11/2017.
 */
public class StorageTest {
    @Test
    public void getUndefinedValue() {
        Storage storage = new Storage();

        Assert.assertNull(storage.getValue(new byte[] { 0x01, 0x02, 0x03 }));
    }

    @Test
    public void setAndGetValue() {
        byte[] key = new byte[] { 0x01, 0x02, 0x03 };
        byte[] value = new byte[] { 0x04, 0x05 };

        Storage storage = new Storage();

        storage.setValue(key, value);
        Assert.assertArrayEquals(value, storage.getValue(key));
    }

    @Test
    public void setAndGetValueUsingDifferentKeys() {
        byte[] key = new byte[] { 0x01, 0x02, 0x03 };
        byte[] value = new byte[] { 0x04, 0x05 };
        byte[] key1 = new byte[] { 0x00, 0x01, 0x02, 0x03 };
        byte[] key2 = new byte[] { 0x00, 0x00, 0x01, 0x02, 0x03 };

        Storage storage = new Storage();

        storage.setValue(key, value);
        Assert.assertArrayEquals(value, storage.getValue(key));
        Assert.assertArrayEquals(value, storage.getValue(key1));
        Assert.assertArrayEquals(value, storage.getValue(key2));
    }
}
