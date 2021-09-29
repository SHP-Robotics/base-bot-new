package org.firstinspires.ftc.teamcode;

/**
 * Created by Chun on Jan 26, 2019 for 10023. Adapted Team 13981 on Oct 14, 2019.
 */

public class ConstantVariables {

    public static final int K_CPR_DRIVE = 560; //1120
    public static final double K_DRIVE_WHEEL_DIA = 4;
    public static final double K_DRIVE_WIDTH = 16.5;
    public static final double K_DRIVE_HEIGHT = 11.5;
    public static final double K_DRIVE_DIA = Math.sqrt(Math.pow(K_DRIVE_WIDTH,2) + Math.pow(K_DRIVE_HEIGHT,2));


    public static final double K_DRIVE_WHEEL_CIRCUMFERENCE = K_DRIVE_WHEEL_DIA * Math.PI;
    public static final double K_CPIN_DRIVE = K_CPR_DRIVE / K_DRIVE_WHEEL_CIRCUMFERENCE;

    public static final double K_TURN_CIRCUMFERENCE = K_DRIVE_DIA * Math.PI;
    public static final double K_CPTURN_DRIVE = K_CPIN_DRIVE * K_TURN_CIRCUMFERENCE;
    public static final double K_CPDEG_DRIVE = K_CPTURN_DRIVE / 360;


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
    public static final long PIXY_MIN_X = 0L;   //x: 0~255 pixels, y:0~199 pixels. (0,0) starts at bottom left
    public static final long PIXY_MAX_X = 255L;
    public static final long PIXY_MIN_Y = 0L;
    public static final long PIXY_MAX_Y = 199L;
}
