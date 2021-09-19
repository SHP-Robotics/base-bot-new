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
    private int stage = 0;

    boolean objectFound = false;
    boolean objectCentered = false;
    boolean objectClose = false;

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

        PixyBlock biggestBlock = pixyCam.getBiggestBlock();

        int X = biggestBlock.x;
        int Y = biggestBlock.y;
        int width = biggestBlock.width;
        int height = biggestBlock.height;


        double direction = 0.1;

        if (X <= 160 && X > 0) {
            direction = -direction;
            objectFound = true;
        } else if (X >= 160 && X < 320) {
            objectFound = true;
        }

        if (X >= 120 && X <= 200) {
            objectCentered = true;
        }

        if (width > 200) {
            objectClose = true;
        }

        if (X > 320 && X < 0) {
//            throw new IllegalArgumentException("X Coordinate not between 0 and 320 pixels");
            telemetry.addData("X Coordinate not between 0 and 320 pixels", X + ',' + Y);
            telemetry.update();
            stage = 0;
        }

        telemetry.addData("x: ", X);
        telemetry.addData("y: ", Y);
        telemetry.addData("width: ", width);
        telemetry.addData("height: ", height);
        telemetry.addData("found: ", objectFound);
        telemetry.addData("centered: ", objectCentered);
        telemetry.addData("close: ", objectClose);
        telemetry.addData("stage: ", stage);
        telemetry.update();

        switch (stage) {

            // turn until Pixy Sees Blocks
            case 0:
//                if (timer.seconds() > 1) {
                stage++;
//                }
//                if (objectFound) {
//                    timer.reset();
//                    if (timer.seconds() > 5) {
//                        reset_drive_encoders();
//                        stage++;
//                    }
//                } else auto_turn(direction, 1);
                break;

            // turn until centered
            case 1:
                if (objectCentered) {
                    leftFrontDriveMotor.setPower(0);
                    leftBackDriveMotor.setPower(0);
                    rightFrontDriveMotor.setPower(0);
                    rightBackDriveMotor.setPower(0);
                    timer.reset();
                    stage++;
                } else {
                    if (objectFound) {
                        leftFrontDriveMotor.setPower(-direction);
                        leftBackDriveMotor.setPower(-direction);
                        rightFrontDriveMotor.setPower(-direction);
                        rightBackDriveMotor.setPower(-direction);
                    }
                }
                break;

            // move forward until block is close
            case 2:
                if (timer.seconds() > 3) {
                    if (objectClose) {
                        leftFrontDriveMotor.setPower(0);
                        leftBackDriveMotor.setPower(0);
                        rightFrontDriveMotor.setPower(0);
                        rightBackDriveMotor.setPower(0);
                        stage++;
                    } else {
                        if (objectFound) {
                            leftFrontDriveMotor.setPower(-0.1);
                            leftBackDriveMotor.setPower(-0.1);
                            rightFrontDriveMotor.setPower(0.1);
                            rightBackDriveMotor.setPower(0.1);
                        }
                    }// else move forward
                }

                break;
            case 3:
                auto_drive(0, 0);
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
