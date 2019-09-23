package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="GamepadV2", group="Linear OpMode")

public class GamepadV2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        API.init(hardwareMap);
        while (opModeIsActive()) {
            float left = gamepad1.left_stick_y+gamepad1.left_stick_x;
            float right = gamepad1.left_stick_y-gamepad1.left_stick_x;
            telemetry.addLine("Left: " + left + ", Right: " + right);
            telemetry.update();
            API.Motor.M2.start(-left);
            API.Motor.M3.start(right);   
        }
    }
}
