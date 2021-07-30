package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlock;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlockList;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyCam;

@TeleOp

public class TeleOpPixy extends BaseRobot {
    PixyCam pixyCam;
    PixyBlockList blocks1;

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

        telemetry.addData("x: ", X);
        telemetry.addData("y: ", Y);
        telemetry.addData("width: ", width);
        telemetry.addData("height: ", height);
        telemetry.update();

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
    }
}