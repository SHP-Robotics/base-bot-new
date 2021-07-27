package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


// Template created by Chun on 1/26/19 for 10023. Made by Team 13981 on 10/11/19



@Autonomous

public class TripleBoostTest extends BaseRobot {
    private int stage = 0;


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
        switch (stage) {

            case 0:
                timer.reset();
                auto_drive(0.75, 1000);
                if (timer.seconds() >= 6) {
                    stage++;
                }
                break;

            case 1:
                auto_drive(0,0);
                break;

            default:
                break;
        }
    }
}

