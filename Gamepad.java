package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
    DcMotor leftMotor;
    DcMotor rightMotor;
    
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        leftMotor = hardwareMap.dcMotor.get("m3");
        rightMotor = hardwareMap.dcMotor.get("m2");
        csensor = hardwareMap.colorSensor.get("color");
        csensor.enableLed(false);
        
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        
        
        waitForStart();
        runtime.reset();
        
        while (opModeIsActive()) {
         int ci = csensor.argb();
         String c = Integer.toString(ci);
         
         
         //String c = "Red:"+csensor.red()+" Green:"+csensor.green()+" Blue:"+csensor.blue();
         telemetry.addData("Status", "Color detected:" + c);
         if (ci==33554432) {
             telemetry.addData("Satus", "STONE DETECTED");
         }
         telemetry.update();
         
         leftMotor.setPower(-gamepad1.left_stick_y);
         rightMotor.setPower(-gamepad1.right_stick_y);
        }
    }
}
