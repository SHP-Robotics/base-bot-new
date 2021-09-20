package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlock;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyCam;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


// Template created by Chun on 1/26/19 for 10023. Made by Team 13981 on 10/11/19

@Autonomous

public class  DriveToBlockAuto extends BaseRobot {
    PixyCam pixyCam;
    public static PixyBlock blocks1;
    public final int pixyBlockCenterTolerance = 20; //in pixels and should be even
    public final int rightPixelStartBoundry = 159+(pixyBlockCenterTolerance/2);
    public final int leftPixelStartBoundry = 159-(pixyBlockCenterTolerance/2);

    boolean isObjectRight;
    boolean isObjectLeft;
    double xCord;
    private int stage = 0;
    PrintWriter file;
    ElapsedTime elapsedTime = new ElapsedTime();

    @Override
    public void init() {
        pixyCam = hardwareMap.get(PixyCam.class, "pixycam");
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

        if (elapsedTime.milliseconds() > 100) { //check every tenth of a second
            elapsedTime.reset();
            blocks1 = pixyCam.getBiggestBlock(1);
            xCord = blocks1.x;
            if (xCord > 0 && xCord >= rightPixelStartBoundry) {
                isObjectRight = true;
            } else if (xCord > 0 && xCord < leftPixelStartBoundry) {
                isObjectLeft = true;

            } else {

            }
            telemetry.addData("x: ", xCord);
            telemetry.update();
            super.loop();
        }

        super.loop();
        switch (stage) {

            // Drive forward to row of blocks

            case 0:
                if (auto_drive(1, 28)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            //center robot with block using pixy
            case 1:
                if (blocks1.isEmpty() == false) {
                    while (isObjectLeft) {
                        auto_mecanum(0.3, 1); //drive one inch at atime to while it is left
                    }
                    while (isObjectRight) {
                        auto_mecanum(-0.3, 1);
                    }
                    reset_drive_encoders();
                    stage++;
                } else {
                    stage++;
                }

                break;
            //mecanum to center robot
            case 2:
                if (auto_mecanum(0.3, 3)) {
                    reset_drive_encoders();
                    stage++;
                }
                break; //new
            case 3:
                // adjust the robot to the block by moving forward and grab block

                if (auto_drive(0.2, 2)) {
                    reset_drive_encoders();
                    timer.reset();
                    stage++;
                }
                break;
            //backup
        }


    }

    @Override
    public void stop() {
        file.close();
    }
}