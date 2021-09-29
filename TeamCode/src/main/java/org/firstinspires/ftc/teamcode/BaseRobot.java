package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

//Created by Chun on 1/26/19 for 10023. Adapted by Ben Co 2021.


public class BaseRobot extends OpMode {
    public DcMotor leftBackDriveMotor, rightBackDriveMotor, leftFrontDriveMotor, rightFrontDriveMotor, armLiftMotor; //armLiftMotor2, armClampMotor;
//    public Servo right_servo, left_servo;
    public ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftBackDriveMotor");
        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBackDriveMotor");
        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftFrontDriveMotor");
        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFrontDriveMotor");

    }


    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();

    }

    public void stop() {
        timer.reset();
        reset_drive_encoders();

    }

    @Override
    public void loop() {
        telemetry.addData("D00 Left Front Drive Motor Enc: ", get_left_front_drive_motor_enc());
        telemetry.addData("D01 Right Front Drive Motor Enc: ", get_right_front_drive_motor_enc());
        telemetry.addData("D02 Left Back Drive Motor Enc: ", get_left_back_drive_motor_enc());
        telemetry.addData("D03 Right Back Drive Motor Enc: ", get_right_back_drive_motor_enc());
    }

    //left front power, left back power, right front power, right back power
    public void drive(double lf, double lb, double rf, double rb) {
        lf = Range.clip(lf, -1, 1);
        lb = Range.clip(lb, -1, 1);
        rf = Range.clip(rf, -1, 1);
        rb = Range.clip(rb, -1, 1);

        leftFrontDriveMotor.setPower(lf);
        leftBackDriveMotor.setPower(lb);
        rightFrontDriveMotor.setPower(rf);
        rightBackDriveMotor.setPower(rb);
    }
    /**
     * @param desiredHeading: the heading to turn to.
     * @param currentHeading: the current imu heading. (angles.firstAngle)
     * @return Whether the target angle has been reached.
     */
    public boolean auto_turn_imu(float desiredHeading, float currentHeading) {
        float difference;
//        if (currentHeading < 0)
//            currentHeading = 180 + Math.abs(currentHeading);
        if (currentHeading <= -90 && desiredHeading >= 90)
            difference = desiredHeading - (360+currentHeading);
        else if (currentHeading >= 90 && desiredHeading <= -90)
            difference = desiredHeading + (360-currentHeading);
        else
            difference = desiredHeading - currentHeading;

        if (Math.abs(difference) < 15) {//5
            drive(0,0,0,0);
            return true;
        } else {
            if (difference<0) {
                difference -= 20;
            } else {
                difference += 20;
            }
            float speed = Range.clip(difference/120, -1, 1); //diff/120
            drive(speed, speed, speed, speed);
            return false;
        }
    }

    /**
     * @param power:   the speed to turn at.
     * @param degrees: the number of degrees to turn.
     * @param side:    the side of the drivetrain that moves. 1 for right, -1 for left.
     * @return Whether the target angle has been reached.
     */
    public boolean auto_side(double power, double degrees, int side) {
        double TARGET_ENC = Math.abs(ConstantVariables.K_CPDEG_DRIVE * degrees);
        telemetry.addData("D99 TURNING TO ENC: ", TARGET_ENC);

        double speed = Range.clip(power, -1, 1);
        if (Math.abs(get_motor_enc(leftFrontDriveMotor))/ConstantVariables.K_CPDEG_DRIVE >= degrees-20 ||
                Math.abs(get_motor_enc(rightFrontDriveMotor))/ConstantVariables.K_CPDEG_DRIVE >= degrees-20) {
            speed /= 2;
        }

        if (side == 1) {
            drive(0,0,speed,speed);
        } else if (side == -1) {
            drive(-speed,-speed,0,0);
        }

        if (Math.abs(get_motor_enc(leftFrontDriveMotor)) >= TARGET_ENC || Math.abs(get_motor_enc(rightFrontDriveMotor)) >= TARGET_ENC) {
            drive(0,0,0,0);
            return true;
        } else {
            return false;
        }
    }
    /**
     * @param power:   the speed to drive at. Negative for reverse.
     * @param inches:  the distance to drive.
     * @param desiredHeading:  the desired angle.
     * @param currentHeading:  the current angle. (angles.firstAngle)
     * @return Whether the target distance has been reached.
     */

    public boolean auto_drive_imu(double power, double inches, float desiredHeading, float currentHeading) {
        double TARGET_ENC = ConstantVariables.K_CPIN_DRIVE * inches;
        telemetry.addData("Target_enc: ", TARGET_ENC);
        double speed = power;
        if (Math.abs(get_motor_enc(leftFrontDriveMotor))/ConstantVariables.K_CPIN_DRIVE >= inches - 5 ||
                Math.abs(get_motor_enc(rightFrontDriveMotor))/ConstantVariables.K_CPIN_DRIVE >= inches - 5) {
            speed /= 2;
        }

        float difference;
        if (currentHeading <= -90 && desiredHeading >= 90)
            difference = desiredHeading - (360+currentHeading);
        else if (currentHeading >= 90 && desiredHeading <= -90)
            difference = desiredHeading + (360-currentHeading);
        else
            difference = desiredHeading - currentHeading;

        double left_speed = speed - (difference/100);
        double right_speed = speed + (difference/100);

        left_speed *= -1; //left motors turn opposite directions

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);

        drive(left_speed, left_speed, right_speed, right_speed);

        if (Math.abs(get_motor_enc(leftFrontDriveMotor)) >= TARGET_ENC || Math.abs(get_motor_enc(rightFrontDriveMotor)) >= TARGET_ENC) {
            drive(0,0,0,0);
            return true;
        } else {
            return false;
        }
    }
    public boolean auto_drive(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_CPIN_DRIVE * inches;
        telemetry.addData("Target_enc: ", TARGET_ENC);
        double speed = power;
        if (Math.abs(get_motor_enc(leftFrontDriveMotor))/ConstantVariables.K_CPIN_DRIVE >= inches - 5 ||
                Math.abs(get_motor_enc(rightFrontDriveMotor))/ConstantVariables.K_CPIN_DRIVE >= inches - 5) {
            speed /= 2;
        }

        double left_speed = speed;
        double right_speed = speed;

        //double error = get_motor_enc(rightFrontDriveMotor) + get_motor_enc(leftFrontDriveMotor);
        //positive means right turned more, negative means left turned more

        //error /= ConstantVariables.K_DRIVE_ERROR_P;
        //left_speed += error;
        //right_speed -= error;

        left_speed *= -1; //left motors turn opposite directions


        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);

        drive(left_speed, left_speed, right_speed, right_speed);

        if (Math.abs(get_motor_enc(leftFrontDriveMotor)) >= TARGET_ENC || Math.abs(get_motor_enc(rightFrontDriveMotor)) >= TARGET_ENC) {
            drive(0,0,0,0);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param power:   the speed to turn at. Positive for right, negative for left.
     * @param degrees: the number of degrees to turn.
     * @return Whether the target angle has been reached.
     */
    public boolean auto_turn(double power, double degrees) {
        double TARGET_ENC = Math.abs(ConstantVariables.K_CPDEG_DRIVE * degrees);
        telemetry.addData("D99 TURNING TO ENC: ", TARGET_ENC);

        double speed = Range.clip(power, -1, 1);
        if (Math.abs(get_motor_enc(leftFrontDriveMotor))/ConstantVariables.K_CPDEG_DRIVE >= degrees-20 ||
                Math.abs(get_motor_enc(rightFrontDriveMotor))/ConstantVariables.K_CPDEG_DRIVE >= degrees-20) {
            speed /= 2;
        }

        drive(-speed, -speed, -speed, -speed);

        if (Math.abs(get_motor_enc(leftFrontDriveMotor)) >= TARGET_ENC || Math.abs(get_motor_enc(rightFrontDriveMotor)) >= TARGET_ENC) {
            drive(0,0,0,0);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param power:   the speed to drive at. Positive for right, negative for left.
     * @param inches:  the distance to drive.
     * @return Whether the target distance has been reached.
     * updated to chuns code
     */
    public boolean auto_mecanum(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_CPIN_DRIVE * inches * 1.414;
        telemetry.addData("Target_enc: ", TARGET_ENC);
        double speed = power;
        if (Math.abs(get_motor_enc(leftFrontDriveMotor))/ConstantVariables.K_CPIN_DRIVE >= inches - 3 ||
                Math.abs(get_motor_enc(rightFrontDriveMotor))/ConstantVariables.K_CPIN_DRIVE >= inches - 3) {
            speed /= 2;
        }

        double leftFrontPower = Range.clip(0 - speed, -1.0, 1.0);
        double leftBackPower = Range.clip(0 + speed, -1.0, 1.0);
        double rightFrontPower = Range.clip(0 - speed, -1.0, 1.0);
        double rightBackPower = Range.clip(0 + speed, -1.0, 1.0);

        drive(leftFrontPower,leftBackPower,rightFrontPower,rightBackPower);

        if (Math.abs(get_motor_enc(leftFrontDriveMotor)) >= TARGET_ENC || Math.abs(get_motor_enc(rightFrontDriveMotor)) >= TARGET_ENC) {
            drive(0,0,0,0);
            return true;
        } else {
            return false;
        }
    }

    public void tankanum_drive(double rightPwr, double leftPwr, double lateralpwr) {
        rightPwr *= -1;

        double leftFrontPower = Range.clip(leftPwr - lateralpwr, -1.0, 1.0);
        double leftBackPower = Range.clip(leftPwr + lateralpwr, -1.0, 1.0);
        double rightFrontPower = Range.clip(rightPwr - lateralpwr, -1.0, 1.0);
        double rightBackPower = Range.clip(rightPwr + lateralpwr, -1.0, 1.0);

        leftFrontDriveMotor.setPower(leftFrontPower);
        leftBackDriveMotor.setPower(leftBackPower);
        rightFrontDriveMotor.setPower(rightFrontPower);
        rightBackDriveMotor.setPower(rightBackPower);
    }

    public void tank_drive(double leftPwr, double rightPwr) {
        double leftPower = Range.clip(leftPwr, -1.0, 1.0);
        double rightPower = Range.clip(rightPwr, -1.0, 1.0);

        leftFrontDriveMotor.setPower(leftPower);
        leftBackDriveMotor.setPower(leftPower);
        rightFrontDriveMotor.setPower(-rightPower);
        rightBackDriveMotor.setPower(-rightPower);
    }



    public void reset_drive_encoders() {
        leftFrontDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int get_motor_enc(DcMotor motor) {
        if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return motor.getCurrentPosition();
    }

    public int get_left_front_drive_motor_enc() {
        if (leftFrontDriveMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return leftFrontDriveMotor.getCurrentPosition();
    }

    public int get_right_front_drive_motor_enc() {
        if (rightFrontDriveMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return rightFrontDriveMotor.getCurrentPosition();
    }


    public int get_left_back_drive_motor_enc() {
        if (leftBackDriveMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return leftBackDriveMotor.getCurrentPosition();
    }

    public int get_right_back_drive_motor_enc() {
        if (rightBackDriveMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return rightBackDriveMotor.getCurrentPosition();
    }


}


