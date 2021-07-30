package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlock;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlockList;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyCam;


// Created by Ben Co and Ayaan Govil

@Autonomous

public class DriveToObject extends BaseRobot {
    ElapsedTime timer = new ElapsedTime();

    PixyCam pixyCam;
    PixyBlockList blocks1;

    private int stage = 0;
    ElapsedTime elapsedTime3 = new ElapsedTime();

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();

        blocks1 = pixyCam.getBiggestBlocks(1);

        PixyBlock biggestBlock = blocks1.get(0);

        int X = biggestBlock.x;
        int Y = biggestBlock.y;
        int width = biggestBlock.width;
        int height = biggestBlock.height;

        double direction = 1.0;
        boolean objectFound = false;
        boolean objectCentered = false;
        boolean objectClose = false; // need to test and implement this

        telemetry.addData("x: ", X);
        telemetry.addData("y: ", Y);
        telemetry.addData("width: ", width);
        telemetry.addData("height: ", height);
        telemetry.update();

        if (X <= 160 && X > 0) {
            direction = -1.0;
            objectFound = true;
        } else if (X >= 160 && X < 0) {
            objectFound = true;
        }

        if (X >= 140 && X <= 180) {
            objectCentered = true;
        }

        if (X > 320 && X < 0) {
//            throw new IllegalArgumentException("X Coordinate not between 0 and 320 pixels");
            telemetry.addData("X Coordinate not between 0 and 320 pixels", X + ',' + Y);
            telemetry.update();
            stage = 0;
        }

        switch (stage) {

            // turn until Pixy Sees Blocks
            case 0:
                if (objectFound) {
                    reset_drive_encoders();
                    stage++;
                } else auto_turn(direction, 1);
                break;

            // turn until centered
            case 1:
                if (objectCentered) {
                    reset_drive_encoders();
                    stage++;
                } else auto_turn(direction, 1);
                break;

            // move forward until block is close
            case 2:
                if (objectClose) {
                    reset_drive_encoders();
                    stage++;
                } // else move forward
                break;
            //backup
//            case 3:
//                if (timer.seconds() >= 0.2) {
//                    if (auto_drive(-0.4, 9)) {
//                        reset_drive_encoders();
//                        stage++;
//                    }
//                }
//                break;
//            //turn to face other side of field
//            case 4:
//                if (auto_turn(-1, 90)) {
//                    reset_drive_encoders();
//                    stage++;
//                }
//                break;
//            //drive accross bridge and drop block
//            case 5:
//                if (auto_drive(1, 48)) {
////                    setArmClampMotor(-1);
//                    reset_drive_encoders();
//                    timer.reset();
//                    stage++;
//                }
//                break;
//            //fold arm motor and backup to other side of field to get 2nd block
//            case 6:
//                if (timer.seconds() > 1) {
//                    if (auto_drive(-1, 69)) {
////                        setArmClampMotor(0);
//                        reset_drive_encoders();
//                        timer.reset();
//                        stage++;
//                    }
//                }
//                break;
//            //turn to face blocks
//            case 7:
//                if (auto_turn(1, 70)) {
//                    reset_drive_encoders();
//                    timer.reset();
//                    stage++;
//                }
//                break;
//            //go forward towards second block and grab it
//            case 8:
//                if (timer.seconds() > 0.5) {
//                    if (auto_drive(1, 26)) {
////                        setArmClampMotor(1);
//                        reset_drive_encoders();
//                        timer.reset();
//                        stage++;
//                    }
//                }
//                break;
//            //backup
//            case 9:
//                if (timer.seconds() > 1) {
//                    if (auto_drive(-0.9, 27)) {
//                        reset_drive_encoders();
//                        timer.reset();
//                        stage++;
//                    }
//                }
//                break;
//            //face bridge
//            case 10:
//                if (auto_turn(-1, 135)) {
//                    reset_drive_encoders();
//                    timer.reset();
//                    stage++;
//                }
//                break;
//            //drive accross and drop block
//            case 11:
//                if (auto_drive(1, 60)) {
////                    setArmClampMotor(-1);
//                    reset_drive_encoders();
//                    stage++;
//                }
//                break;
//            //park under bridge
//            case 12:
//                if (auto_drive(-1, 12)) {
//                    reset_drive_encoders();
//                    stage++;
//                }
            default:
                break;

        }

    }

//    @Override
//    public void stop() {
//        file.close();
//    }
}