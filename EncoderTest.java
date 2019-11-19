package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class EncoderTest extends LinearOpMode {
    
    private API.Motor fl = API.Motor.M1;
    private API.Motor fr = API.Motor.M2;
    private API.Motor bl = API.Motor.M0;
    private API.Motor br = API.Motor.M3;
    private API.Servo intake = API.Servo.S0;
    private double speed = 1;
    @Override
    public void runOpMode() {
        API.init(this);
        
        fl.resetEncoder(false);
        fr.resetEncoder(false);
        bl.resetEncoder(false);
        br.resetEncoder(false);

        waitForStart();
        
        while (opModeIsActive()) {
            String s = "Front Left: " + fl.getPosition() + System.lineSeparator() +
            "Front Right: " + fr.getPosition() + System.lineSeparator() +
            "Back Left: " + bl.getPosition() + System.lineSeparator() +
            "Back Right: " + br.getPosition();
            API.print(s);
        }
    }
}
