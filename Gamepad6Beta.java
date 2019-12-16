package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp

public class Gamepad6Beta extends LinearOpMode {
    private API.Motor fl = API.Motor.M1;
    private API.Motor fr = API.Motor.M2;
    private API.Motor bl = API.Motor.M0;
    private API.Motor br = API.Motor.M3;
    private double speed = 1;
    private double imuOut = 0;
    private long time;
    @Override
    public void runOpMode() {
        API.init(this);
        
        fr.setDirection(API.Direction.REVERSE);
        br.setDirection(API.Direction.REVERSE);
        fr.stop();
        br.stop();
        
        fl.resetEncoder(true);
        fr.resetEncoder(true);
        bl.resetEncoder(true);
        br.resetEncoder(true);
        
        waitForStart();
        
        API.imu.reset();
        
        while (opModeIsActive()) {
            long ms = System.currentTimeMillis()+15;
            //double s = Math.abs(gamepad1.right_stick_x)>=0.2 ? Math.min(speed, 0.35) : speed;
            
            imuOut = API.imu.adjustAngle(API.imu.getYaw());
            if (!gamepad1.y && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x != 0) {
                move(gamepad1.left_stick_y, imuOut/180, gamepad1.right_stick_x, speed, true);
                if (time>System.currentTimeMillis()) {
                    bl.start(speed*0.90);
                    br.start(speed*0.90);
                }
            } else {
                move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, speed, true);
                API.imu.reset();
                time = System.currentTimeMillis()+1000;
            }
            
            if (gamepad1.right_bumper) {
                speed = Math.min(speed+0.01, 1);
            } else if (gamepad1.left_bumper) {
                speed = Math.max(speed-0.01, 0.2);
            } else if (gamepad1.x) {
                speed = 0.35;
            }
            ms-=System.currentTimeMillis();
            if (ms>5) try {
                Thread.sleep(ms);
            } catch (InterruptedException ie) {}
        }
    }
    private void move(double power, double turn, double strafe, double speed, boolean verbose) {
        double ffl = (-power + turn + strafe);
        double fbl = (-power + turn - strafe);
        double ffr = (-power - turn - strafe);
        double fbr = (-power - turn + strafe);
            
        double largest = 1.0;
        largest = Math.max(largest, Math.abs(ffl));
        largest = Math.max(largest, Math.abs(fbl));
        largest = Math.max(largest, Math.abs(ffr));
        largest = Math.max(largest, Math.abs(fbr));
            
        ffl/=largest;
        fbl/=largest;
        ffr/=largest;
        fbr/=largest;
            
        ffl*=speed;
        fbl*=speed;
        ffr*=speed;
        fbr*=speed;
            
        fl.start(ffl);
        bl.start(fbl);
        fr.start(ffr);
        br.start(fbr);
            
        if (verbose) API.print("Speed: " + speed + System.lineSeparator() +
        "Rotation (degrees, IMU): " + imuOut + System.lineSeparator() +
        "Front Left: " + ffl + System.lineSeparator() +
        "Back Left: " + fbl + System.lineSeparator() +
        "Front Right: " + ffr + System.lineSeparator() +
        "Back Right: " + fbr + System.lineSeparator() +
        "IMU Active: " + !gamepad1.y);
    }
}
