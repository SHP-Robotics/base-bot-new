package org.firstinspires.ftc.teamcode;

/**
 * Created by Chun on Jan 26, 2019 for 10023. Adapted Team 13981 on Oct 14, 2019.
 */

public class ConstantVariables {

    public static final int K_PPR_DRIVE = 1120;
    public static final double K_DRIVE_WHEEL_DIA = 4;
    public static final double K_DRIVE_DIA = 16.5;//Dont know what this is.

    public static final double K_DRIVE_WHEEL_CIRCUMFERENCE = K_DRIVE_WHEEL_DIA * Math.PI; //12.56637
    public static final double K_PPIN_DRIVE = K_PPR_DRIVE / K_DRIVE_WHEEL_CIRCUMFERENCE; //89.1267725

    public static final double K_TURN_CIRCUMFERENCE = K_DRIVE_DIA * Math.PI;
    public static final double K_PPTURN_DRIVE = K_PPIN_DRIVE * K_TURN_CIRCUMFERENCE;
    public static final double K_PPDEG_DRIVE = K_PPTURN_DRIVE / 360;

    public static final double K_MAX_CLIBER = 1465;

    public static final double K_DRIVE_ERROR_P = 250; // higher = less sensitive
    public static final byte   PIXY_INITIAL_ARRAYSIZE = 0;
    public static final short PIXY_MAXIMUM_ARRAYSIZE = 130;
    public static final int PIXY_START_WORD = 0xaa55; //for regular color recognition
    public static final int PIXY_START_WORD_CC = 0xaa56; //for color code - angle rotation recognition
    public static final int PIXY_START_WORDX = 0x55aa; //regular color another way around
    public static final byte PIXY_MAX_SIGNATURE = 7;
    public static final int PIXY_DEFAULT_ARGVAL = 0xffff;

    // Pixy x-y position values
    public static final long PIXY_MIN_X = 0L;   //x: 0~319 pixels, y:0~199 pixels. (0,0) starts at bottom left
    public static final long PIXY_MAX_X = 319L;
    public static final long PIXY_MIN_Y = 0L;
    public static final long PIXY_MAX_Y = 199L;
}
