package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp

public class Gamepad3 extends LinearOpMode {
    private API.Motor fl = API.Motor.M1;
    private API.Motor fr = API.Motor.M2;
    private API.Motor bl = API.Motor.M0;
    private API.Motor br = API.Motor.M3;
    private API.Servo intake = API.Servo.S0;
    private double speed = 1;
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
        
        intake.setPosition(0);
        
        while (opModeIsActive()) {
            if (gamepad1.a) {
                intake.setPosition(0);
            } else if (gamepad1.b) {
                intake.setPosition(1);
            }
            
            if (gamepad1.dpad_left) {
                strafeLeft();
            } else if (gamepad1.dpad_right) {
                strafeRight();
            } else if (gamepad1.dpad_up) {
                forwards();
            } else if (gamepad1.dpad_down) {
                backwards();
            } else {
                double y = gamepad1.left_stick_y * -speed;
                double x = gamepad1.left_stick_x * -speed;
                double leftPower =  y - x;
                double rightPower = y + x;
                fl.start(leftPower);
                fr.start(rightPower);
                bl.start(leftPower);
                br.start(rightPower);
            }
            
            if (gamepad1.right_bumper) {
                speed = Math.min(speed+0.005, 1);
            } else if (gamepad1.left_bumper) {
                speed = Math.max(speed-0.005, 0.2);
            }
            String s = "Speed: " + speed + System.lineSeparator() +
            "Front Left: " + fl.getPosition() + System.lineSeparator() +
            "Front Right: " + fr.getPosition() + System.lineSeparator() +
            "Back Left: " + bl.getPosition() + System.lineSeparator() +
            "Back Right: " + br.getPosition();
            API.print(s);
        }
    }
    
    private void forwards() {
        fl.start(speed);
        fr.start(speed);
        br.start(speed);
        bl.start(speed);
    }
    
    private void backwards() {
        fl.start(-speed);
        fr.start(-speed);
        br.start(-speed);
        bl.start(-speed);
    }
    
    private void strafeLeft() {
        fl.start(-speed);
        fr.start(speed);
        br.start(-speed);
        bl.start(speed);
    }
    
    private void strafeRight() {
        fl.start(speed);
        fr.start(-speed);
        br.start(speed);
        bl.start(-speed);
    }
}
