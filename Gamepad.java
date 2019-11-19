package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name="Gamepad", group="Linear OpMode")

public class Gamepad extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    ColorSensor csensor;
    API.Motor leftMotor;
    API.Motor rightMotor;
    
    @Override
    public void runOpMode() throws InterruptedException {
        API.init(this);
        API.print("Initialized");
        leftMotor = API.Motor.M3;
        rightMotor = API.Motor.M2;
        leftMotor.setDirection(API.Direction.REVERSE);
        
        
        waitForStart();
        runtime.reset();
        
        while (opModeIsActive()) {

         

         leftMotor.start(-gamepad1.left_stick_y);
         rightMotor.start(-gamepad1.right_stick_y);
        }
    }
}
