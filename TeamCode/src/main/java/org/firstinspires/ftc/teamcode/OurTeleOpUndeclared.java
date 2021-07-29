package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlock;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlockList;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyCam;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@TeleOp

public class OurTeleOpUndeclared extends BaseRobot {

    public static PixyBlockList blocks1;
    PixyBlockList blocks2;
    PixyBlockList blocks3;
    ElapsedTime elapsedTime = new ElapsedTime();
    ElapsedTime elapsedTime2 = new ElapsedTime();
    ElapsedTime elapsedTime3 = new ElapsedTime();


    PrintWriter file;

    @Override
    public void init() {
        try {
            file = new PrintWriter("/sdcard/pixyResults.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        super.init();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        if (elapsedTime3.milliseconds() > 100) {
            elapsedTime3.reset();
            blocks1 = pixyCam.getBiggestBlocks(1);
//            blocks2 = pixyCam.getBiggestBlocks(2);
//            blocks3 = pixyCam.getBiggestBlocks(3);
            telemetry.addData("Counts", "%d", blocks1.totalCount);
            file.println("----------------------------");
//            file.format("Elapsed: %s Counts: %d/%d\n", elapsedTime2.toString(), blocks2.totalCount, blocks3.totalCount);
            for (int i = 0; i < blocks1.size(); i++) {
                PixyBlock block = blocks1.get(i);
                if (!block.isEmpty()) {
                    telemetry.addData("Block 1[" + i + "]", block.toString());
                }
            }
        super.loop();
        }

//        int min = 1;
//        int max = 3;
//
//        int random_int;
//        int multi_power = 0;
//
//        String current_lap = "Starting Race...";
//        int current_lap_int = 0;
//
//        String current_powerup = "None";


        double rightstick_y = gamepad1.right_stick_y/2;
        double leftstick_y = gamepad1.left_stick_y/2;
        double rightstick_x = gamepad1.right_stick_x/2;

        double rightstick_y75 = 3 * (gamepad1.right_stick_y/4);
        double leftstick_y75 = 3 * (gamepad1.left_stick_y/4);
        double rightstick_x75 = 3 * (gamepad1.right_stick_x/4);



        tankanum_drive(gamepad1.right_stick_y, gamepad1.left_stick_y,gamepad1.right_stick_x);

//        if (gamepad1.dpad_up || gamepad1.b) {
//            setArmLiftMotor(-1);
//        } else if (gamepad1.dpad_down || gamepad1.a) {
//            setArmLiftMotor(1);
//        } else {
//            setArmLiftMotor(0);
//        }
//
//        if (gamepad1.dpad_left) {
//            set_right_servo(0);
//            set_left_servo(1);
//        }
//        else if (gamepad1.dpad_right) {
//            set_right_servo(1);
//            set_left_servo(0);
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
//        telemetry.addData();
        telemetry.update();
    }

    @Override
    public void stop() {
        file.close();
    }

}