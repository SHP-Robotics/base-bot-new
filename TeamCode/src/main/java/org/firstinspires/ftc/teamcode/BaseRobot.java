package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//import com.qualcomm.robotcore.hardware.ColorSensor;

public class BaseRobot extends OpMode {

    public DcMotor leftBackDriveMotor, rightBackDriveMotor, leftFrontDriveMotor, rightFrontDriveMotor, armLiftMotor; //armLiftMotor2, armClampMotor;
    public Servo right_servo, left_servo;
//    public ColorSensor colorBlock;
    public ElapsedTime timer = new ElapsedTime();
    //Created by Chun on 1/26/19 for 10023. Adapted by Team 13981.


    @Override
    public void init() {
        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftBackDriveMotor");
        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBackDriveMotor");
        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftFrontDriveMotor");
        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFrontDriveMotor");
        armLiftMotor = hardwareMap.get(DcMotor.class, "armLiftMotor");
        /*armLiftMotor2 = hardwareMap.get(DcMotor.class, "armLiftMotor2");
        armClampMotor = hardwareMap.get(DcMotor.class, "armClampMotor");*/
//        colorBlock = hardwareMap.get(ColorSensor.class, "colorSensorBlock");
        right_servo = hardwareMap.get(Servo.class,"right_servo");
        left_servo= hardwareMap.get(Servo.class,"left_servo");
    }


    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();
        reset_armLiftMotor_encoders();
//        reset_armLiftMotor2_encoders();
//        colorBlock.enableLed(true);
    }

    public void stop() {
        timer.reset();
        reset_drive_encoders();
        reset_armLiftMotor_encoders();
//        reset_armLiftMotor2_encoders();
    }

    @Override
    public void loop() {

        telemetry.addData("D00 Left Front Drive Motor Enc: ", get_left_front_drive_motor_enc());
        telemetry.addData("D01 Right Front Drive Motor Enc: ", get_right_front_drive_motor_enc());
        telemetry.addData("D02 Left Back Drive Motor Enc: ", get_left_back_drive_motor_enc());
        telemetry.addData("D03 Right Back Drive Motor Enc: ", get_right_back_drive_motor_enc());

        telemetry.addData("D04 Arm Lift Motor Enc: ", get_armLiftMotor_enc());
//        telemetry.addData("D05 Arm Lift Motor 2 Enc: ", get_armLiftMotor2_enc());
//        telemetry.addData("D010 Arm Clamp Motor Enc: ", get_armClampMotor_enc());

    }

    public void setArmLiftMotor(double power) {
        double speed = Range.clip(power, -1, 1);
        armLiftMotor.setPower(speed);
    }
/*
    public void setArmLiftMotor2(double power) {
        double speed = Range.clip(power, -1, 1);
        armLiftMotor2.setPower(speed);

    }

    public void setArmClampMotor(double power) {
        double speed = Range.clip(power, -1, 1);
        armClampMotor.setPower(speed);

    }*/

    public boolean checkBlackColor(int red, int blue) {
        return blue > (3.0/4)*red;
    }

    public boolean checkGreenColor(int green, int blue, int red) {
        if (green > blue + red){
            return true;
        } else return false;
    }

    public boolean checkBlueColor(int green, int blue, int red) {
        if (blue > green + red){
            return true;
        } else return false;
    }

    public boolean checkRedColor(int green, int blue, int red) {
        if (red > blue + green){
            return true;
        } else return false;
    }

    public boolean auto_drive(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_PPIN_DRIVE * inches;
        telemetry.addData("Target_enc: ", TARGET_ENC);
        double left_speed = -power;
        double right_speed = power;
        /*double error = -get_left_front_drive_motor_enc() - get_right_front_drive_motor_enc();

        error /= ConstantVariables.K_DRIVE_ERROR_P;
        left_speed += error;
        right_speed -= error;*/

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        leftFrontDriveMotor.setPower(left_speed);
        leftBackDriveMotor.setPower(left_speed);
        rightFrontDriveMotor.setPower(right_speed);
        rightBackDriveMotor.setPower(right_speed);

        if (Math.abs(get_right_front_drive_motor_enc()) >= TARGET_ENC) {
            leftFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);
            return true;
        }
        return false;
    }

    /**
     * @param power:   the speed to turn at. Negative for left.
     * @param degrees: the number of degrees to turn.
     * @return Whether the target angle has been reached.
     */
    public boolean auto_turn(double power, double degrees) {
        double TARGET_ENC = Math.abs(ConstantVariables.K_PPDEG_DRIVE * degrees);
        telemetry.addData("D99 TURNING TO ENC: ", TARGET_ENC);

        double speed = Range.clip(power, -1, 1);
        leftFrontDriveMotor.setPower(-speed);
        leftBackDriveMotor.setPower(-speed);
        rightFrontDriveMotor.setPower(-speed);
        rightBackDriveMotor.setPower(-speed);

        if (Math.abs(get_right_front_drive_motor_enc()) >= TARGET_ENC) {
            leftFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);
            return true;
        } else {
            return false;
        }
    }

    //positive for right, negative for left
    public boolean auto_mecanum(double power, double inches) {
        double TARGET_ENC = ConstantVariables.K_PPIN_DRIVE * inches;
        telemetry.addData("Target_enc: ", TARGET_ENC);

        double leftFrontPower = Range.clip(0 - power, -1.0, 1.0);
        double leftBackPower = Range.clip(0 + power, -1.0, 1.0);
        double rightFrontPower = Range.clip(0 - power, -1.0, 1.0);
        double rightBackPower = Range.clip(0 + power, -1.0, 1.0);

        leftFrontDriveMotor.setPower(leftFrontPower);
        leftBackDriveMotor.setPower(leftBackPower);
        rightFrontDriveMotor.setPower(rightFrontPower);
        rightBackDriveMotor.setPower(rightBackPower);

        // once you have reached destination, kill motors and return true
        if (Math.abs(get_right_front_drive_motor_enc()) >= TARGET_ENC) {
            leftFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);
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

    /*public void tank_drive(double leftPwr, double rightPwr) {
        double leftPower = Range.clip(leftPwr, -1.0, 1.0);
        double rightPower = Range.clip(rightPwr, -1.0, 1.0);

        leftFrontDriveMotor.setPower(leftPower);
        leftBackDriveMotor.setPower(leftPower);
        rightFrontDriveMotor.setPower(-rightPower);
        rightBackDriveMotor.setPower(-rightPower);
    }
*/
    public void set_right_servo(double pos) {
        double position = Range.clip(pos, 0, 1.0);
        right_servo.setPosition(position);
    }

    public void set_left_servo(double pos) {
        double position = Range.clip(pos, 0, 1.0);
        left_servo.setPosition(position);
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

   public void reset_armLiftMotor_encoders() {
        armLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    /*public void reset_armLiftMotor2_encoders() {
        armLiftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLiftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }*/

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

    public int get_armLiftMotor_enc() {
        if (armLiftMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            armLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return armLiftMotor.getCurrentPosition();
    }
/*
    public int get_armLiftMotor2_enc() {
        if (armLiftMotor2.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            armLiftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return armLiftMotor2.getCurrentPosition();
    }

    public int get_armClampMotor_enc() {
        if (armClampMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            armClampMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return armClampMotor.getCurrentPosition();
    }
    public void reset_armClampMotor_encoders() {
        armClampMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armClampMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
*/

}


