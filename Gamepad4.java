package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp

public class Gamepad4 extends LinearOpMode {
    private API.Motor fl = API.Motor.M1;
    private API.Motor fr = API.Motor.M2;
    private API.Motor bl = API.Motor.M0;
    private API.Motor br = API.Motor.M3;
    //private API.Servo intake = API.Servo.S0;
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
        
        //intake.setPosition(0);
        
        while (opModeIsActive()) {
/*            if (gamepad1.a) {
                intake.setPosition(0);
            } else if (gamepad1.b) {
                intake.setPosition(1);
            }
*/
            
            double ffl = (-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*speed;
            double fbl = (-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*speed;
            double ffr = (-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*speed;
            double fbr = (-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*speed;
            
            fl.start(ffl);
            bl.start(fbl);
            fr.start(ffr);
            br.start(fbr);
            

            if (gamepad1.right_bumper) {
                speed = Math.min(speed+0.005, 1);
            } else if (gamepad1.left_bumper) {
                speed = Math.max(speed-0.005, 0.2);
            } else if (gamepad1.x) {
                speed = 0.35;
            }
            
            String s = "Speed: " + speed + System.lineSeparator() +
            "Front Left: " + ffl + System.lineSeparator() +
            "Back Left: " + fbl + System.lineSeparator() +
            "Front Right: " + ffr + System.lineSeparator() +
            "Back Right: " + fbr;
            API.print(s);
        }
    }
}
