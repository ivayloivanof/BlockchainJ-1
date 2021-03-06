package com.ajlopez.blockchain.vm;

/**
 * Created by ajlopez on 12/08/2017.
 */
public class OpCodes {
    private OpCodes() { }
    
    public static final byte OP_PUSH = 1;
    public static final byte OP_POP = 2;
    public static final byte OP_ADD = 3;
    public static final byte OP_SUBTRACT = 4;
    public static final byte OP_MULTIPLY = 5;
    public static final byte OP_DIVIDE = 6;
    public static final byte OP_MOD = 7;
    public static final byte OP_DIVMOD = 8;

    public static final byte OP_DUP = 16;
    public static final byte OP_SWAP = 17;
    public static final byte OP_SSTORE = 32;
    public static final byte OP_SLOAD = 33;
    public static final byte OP_MSTORE = 34;
    public static final byte OP_MLOAD = 35;

    public static final byte OP_EQUAL = 48;
}
