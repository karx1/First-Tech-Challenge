package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class AutoProbablyVeryBroken extends LinearOpMode {
    private API.Motor fl = API.Motor.M1;
    private API.Motor fr = API.Motor.M2;
    private API.Motor bl = API.Motor.M0;
    private API.Motor br = API.Motor.M3;
    private double speed = 0.6;
    private int stone;
    
    @Override
    public void runOpMode() {
        API.init(this);
        br.setDirection(API.Direction.REVERSE);
        br.stop();
        waitForStart();
        
        stone = posSkystone()-360;
        
        //goForwards(-150);
        //goForwards(150);
        
        API.print(String.valueOf(stone));
        
        goToStone(stone);
        API.pause(3);
        goForwards(3000);
        waitForMotors();
        API.print("done");
    }
    
    private void waitForMotors() {
        while (fl.isBusy() || fr.isBusy() || bl.isBusy() || br.isBusy()) idle();
    }
    
    private void goToStone(int pos) {
        pos*=4;
        fl.resetEncoder();
        fr.resetEncoder();
        bl.resetEncoder();
        br.resetEncoder();
        
        fl.setPosition(pos, speed);
        fr.setPosition(-pos, speed);
        bl.setPosition((int) (-pos*0.7), speed);
        br.setPosition((int) (pos*0.7), speed);
    }
    
    private void goForwards(int ticks) {
        fl.resetEncoder();
        fr.resetEncoder();
        bl.resetEncoder();
        br.resetEncoder();
        
        fl.setPosition((int) (ticks*1.0), 1);
        fr.setPosition(ticks, speed);
        bl.setPosition((int) (ticks*1.0), 1);
        br.setPosition(ticks, speed);
    }
    private int posSkystone() {
        API.Tensorflow.DetectedObject[] detectedObjects = API.Tensorflow.getDetections();
        //API.print("test12341");
        if (detectedObjects != null) for (API.Tensorflow.DetectedObject detectedObject : detectedObjects) if ("Skystone".equals(detectedObject.getLabel())) return (detectedObject.getX1() + detectedObject.getX2())/2;
        return -1;
    }
}
