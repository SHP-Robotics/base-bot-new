package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Const;

@TeleOp

public class OurTeleOp extends BaseRobot {

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

        int min = 1;
        int max = 3;

        int random_int;
        int multi_power = 0;

        String current_lap = "Starting Race...";
        int current_lap_int = 0;

        String current_powerup = "None";


        /*double rightstick_y = gamepad1.right_stick_y/2;
        double leftstick_y = gamepad1.left_stick_y/2;
        double rightstick_x = gamepad1.right_stick_x/2;

        double rightstick_y75 = 3 * (gamepad1.right_stick_y/4);
        double leftstick_y75 = 3 * (gamepad1.left_stick_y/4);
        double rightstick_x75 = 3 * (gamepad1.right_stick_x/4);*/


        tankanum_drive(gamepad1.right_stick_y, gamepad1.left_stick_y,gamepad1.right_stick_x);

        if (gamepad1.dpad_up || gamepad1.b) {
            setArmLiftMotor(-1);
        } else if (gamepad1.dpad_down || gamepad1.a) {
            setArmLiftMotor(1);
        } else {
            setArmLiftMotor(0);
        }

        if (gamepad1.dpad_left) {
            set_right_servo(0);
            set_left_servo(1);
        }
        else if (gamepad1.dpad_right) {
            set_right_servo(1);
            set_left_servo(0);
        }

        // powerup function, meant for green tape
//        if (checkGreenColor(colorBlock.green(), colorBlock.blue(), colorBlock.red())) {
//
//            timer.reset();
//
//            // refresh random_int
//            random_int = (int) (Math.random() * (max - min + 1) + min);
//
//            current_powerup = "Rolling...";
//
//        if (timer.seconds() >= 3) {
//            // Single Big Boost Power-Up
//            if (random_int == 1) {
//                current_powerup = "Single BIG Boost";
//                if (gamepad1.left_bumper || gamepad1.right_bumper) {
//                    timer.reset();
//                    tankanum_drive(gamepad1.right_stick_y, gamepad1.left_stick_y, gamepad1.right_stick_x);
//                    if (timer.seconds() >= 3) {
//                        tankanum_drive(gamepad1.right_stick_y / 2, gamepad1.left_stick_y / 2, gamepad1.right_stick_x / 2);
//                        random_int = 0;
//                    }
//                }
//            }
//
//            // Triple Normal Boost Power-Up
//            if (random_int == 2) {
//                current_powerup = "Triple Normal Boost, 3 uses left";
//                if (gamepad1.left_bumper || gamepad1.right_bumper) {
//                    timer.reset();
//                    tankanum_drive(3 * (gamepad1.right_stick_y / 4), 3 * (gamepad1.left_stick_y / 4), 3 * (gamepad1.right_stick_x / 4));
//                    if (timer.seconds() >= 2) {
//                        tankanum_drive(gamepad1.right_stick_y / 2, gamepad1.left_stick_y / 2, gamepad1.right_stick_x / 2);
//                        multi_power++;
//                        random_int = 2;
//                    }
//                }
//            }
//
//            if (multi_power == 1) {
//                current_powerup = "Triple Normal Boost, 2 uses left";
//            }
//
//            if (multi_power == 2) {
//                current_powerup = "Triple Normal Boost, 1 use left";
//            }
//
//            if (multi_power == 3) {
//                random_int = 0;
//                multi_power = 0;
//            }
//
//            // Blockade Power-Up
//            if (random_int == 3) {
//                current_powerup = "Blockade";
//                if (gamepad1.left_bumper || gamepad1.right_bumper) {
//                    timer.reset();
//                    set_leftBlocking_servo(1.0);
//                    set_rightBlocking_servo(1.0);
//                    if (timer.seconds() >= 10) {
//                        set_leftBlocking_servo(0.0);
//                        set_rightBlocking_servo(0.0);
//                        random_int = 0;
//                    }
//                }
//            }
//
//            if (random_int == 0) {
//                current_powerup = "None";
//            }
//
//        }
//        }
//
//        // lap, blue
//        if (checkBlueColor(colorBlock.green(), colorBlock.blue(), colorBlock.red())) {
//            reset_drive_encoders();
//            current_lap_int++;
//        }
//
//        if (current_lap_int == 1) {
//            current_lap = "1/3";
//        }
//
//        if (current_lap_int == 2) {
//            current_lap = "2/3";
//        }
//
//        if (current_lap_int == 3) {
//            current_lap = "3/3 FINAL LAP";
//        }
//
//        if (current_lap_int == 4) {
//            current_lap = "Finished!";
//        }
//
//        telemetry.addData("Current Power-Up: ", current_powerup);
//        telemetry.addData("Current Lap: ", current_lap);
    }
}