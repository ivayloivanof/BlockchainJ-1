package com.ajlopez.blockchain.encoding;

import com.ajlopez.blockchain.core.Address;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ajlopez on 28/09/2017.
 */
public class AddressEncoderTest {
    @Test
    public void encodeDecodeAddress() {
        Address address = new Address();

        byte[] encoded = AddressEncoder.encode(address);

        Assert.assertNotNull(encoded);

        Address result = AddressEncoder.decode(encoded);

        Assert.assertNotNull(result);
        Assert.assertArrayEquals(address.getBytes(), result.getBytes());
    }
}
