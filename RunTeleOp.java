package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class RunTeleOp extends LinearOpMode {
    
    @Override
    public void runOpMode() {
        API.init(this);
        API.Motor.M2.start(0.5);
        API.Motor.M3.start(-0.5);
        API.pause(5);
        API.Motor.M2.stop();
        API.Motor.M3.stop();
    }
    
    private void pause(double seconds) {
        try{
        sleep((long) ((seconds-1)*1000));
        } catch (Exception e) {}
    }
}
