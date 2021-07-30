package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlock;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyBlockList;
import org.firstinspires.ftc.teamcode.PixyWorking.PixyCam;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


// Template created by Chun on 1/26/19 for 10023. Made by Team 13981 on 10/11/19

@Autonomous

public class DriveToBlockAuto extends BaseRobot {
    PixyCam pixyCam;
    public static PixyBlockList blocks1;

    private int stage = 0;
    PrintWriter file;
    ElapsedTime elapsedTime3 = new ElapsedTime();

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
        PixyBlock biggestBlock;


        if (elapsedTime3.milliseconds() > 100) {
            elapsedTime3.reset();
            blocks1 = pixyCam.getBiggestBlocks(1);
//            telemetry.addData("Counts", "%d", blocks1.totalCount);
//            file.println("----------------------------");
//            for (int i = 0; i < blocks1.size(); i++) {
//                PixyBlock block = blocks1.get(i);
//                if (!block.isEmpty()) {
//                    telemetry.addData("Block 1[" + i + "]", block.toString());
//                }
//            }
            biggestBlock = blocks1.get(0);
            super.loop();
        }
        boolean isObjectRight;
        boolean isObjectLeft;
        biggestBlock = blocks1.get(0);
        int X = biggestBlock.x;
        int Y = biggestBlock.y;
        telemetry.addData("x: ", X);
        telemetry.update();

        if (X >= 160) {
            isObjectRight = true;
            isObjectLeft = false;
        } else if (X < 160) {
            isObjectRight = false;
            isObjectLeft = true;
        } else if (X > 320 && X < 0) {
            throw new IllegalArgumentException("X Coordinate not between 0 and 320 pixels");
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
            //mecanum until Pixy Sees Blocks
            case 1:
                if(true)
               {
                    reset_drive_encoders();
                    stage++;
                }
                else auto_mecanum(0.3, 46);

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
            case 4:
                if (timer.seconds() >= 0.2) {
                    if (auto_drive(-0.4, 9)) {
                        reset_drive_encoders();
                        stage++;
                    }
                }
                break;
            //turn to face other side of field
            case 5:
                if (auto_turn(-1, 90)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;
            //drive accross bridge and drop block
            case 6:
                if (auto_drive(1, 48)) {
//                    setArmClampMotor(-1);
                    reset_drive_encoders();
                    timer.reset();
                    stage++;
                }
                break;
            //fold arm motor and backup to other side of field to get 2nd block
            case 7:
                if (timer.seconds() > 1) {
                    if (auto_drive(-1, 69)) {
//                        setArmClampMotor(0);
                        reset_drive_encoders();
                        timer.reset();
                        stage++;
                    }
                }
                break;
            //turn to face blocks
            case 8:
                if (auto_turn(1, 70)) {
                    reset_drive_encoders();
                    timer.reset();
                    stage++;
                }
                break;
            //go forward towards second block and grab it
            case 9:
                if(timer.seconds() > 0.5) {
                    if (auto_drive(1, 26)) {
//                        setArmClampMotor(1);
                        reset_drive_encoders();
                        timer.reset();
                        stage++;
                    }
                }
                break;
            //backup
            case 10:
                if(timer.seconds() >1) {
                    if (auto_drive(-0.9, 27)) {
                        reset_drive_encoders();
                        timer.reset();
                        stage++;
                    }
                }
                break;
            //face bridge
            case 11:
                if (auto_turn(-1, 135)) {
                    reset_drive_encoders();
                    timer.reset();
                    stage++;
                }
                break;
            //drive accross and drop block
            case 12:
                if(auto_drive(1,60)){
//                    setArmClampMotor(-1);
                    reset_drive_encoders();
                    stage++;
                }
                break;
            //park under bridge
            case 13: if(auto_drive(-1,12))    {
                reset_drive_encoders();
                stage++;
            }
            default:
                break;

        }

    }

    @Override
    public void stop() {
        file.close();
    }
}