package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp

public class Vex393 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        API.init(this);
        waitForStart();
        API.print("test");
        API.Servo.S0.start(0.75);
        API.pause(10000);
    }
}
