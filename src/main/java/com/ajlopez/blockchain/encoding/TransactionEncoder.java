package com.ajlopez.blockchain.encoding;

import com.ajlopez.blockchain.core.Address;
import com.ajlopez.blockchain.core.Transaction;
import com.ajlopez.blockchain.utils.ByteUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajlopez on 04/10/2017.
 */
public class TransactionEncoder {
    private TransactionEncoder() {}

    public static byte[] encode(Transaction transaction) {
        byte[] rlpSender = RLP.encode(transaction.getSender().getBytes());
        byte[] rlpReceiver = RLP.encode(transaction.getReceiver().getBytes());
        byte[] rlpValue = RLP.encode(transaction.getValue().toByteArray());
        byte[] rlpNonce = RLP.encode(ByteUtils.longToBytes(transaction.getNonce()));

        return RLP.encodeList(rlpSender, rlpReceiver, rlpValue, rlpNonce);
    }

    public static byte[] encode(List<Transaction> transactions) {
        byte[][] rlpTransactions = new byte[transactions.size()][];

        for (int k = 0; k < rlpTransactions.length; k++)
            rlpTransactions[k] = TransactionEncoder.encode(transactions.get(k));

        return RLP.encodeList(rlpTransactions);
    }

    public static Transaction decode(byte[] encoded) {
        byte[][] bytes = RLP.decodeList(encoded);
        Address sender = new Address(RLP.decode(bytes[0]));
        Address receiver = new Address(RLP.decode(bytes[1]));
        BigInteger value = new BigInteger(1, RLP.decode(bytes[2]));
        long nonce = ByteUtils.bytesToLong(RLP.decode(bytes[3]));

        return new Transaction(sender, receiver, value, nonce);
    }

    public static List<Transaction> decodeList(byte[] encoded) {
        byte[][] encodedtxs = RLP.decodeList(encoded);

        List<Transaction> txs = new ArrayList<>();

        for (int k = 0; k < encodedtxs.length; k++)
            txs.add(TransactionEncoder.decode(encodedtxs[k]));

        return txs;
    }
}
