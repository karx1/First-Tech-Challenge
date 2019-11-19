package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp

public class TensorTest2 extends LinearOpMode{

    @Override
    public void runOpMode() {
        API.init(this);
        waitForStart();
        long last = System.currentTimeMillis();
        while (opModeIsActive()) {
            API.Tensorflow.DetectedObject[] list = API.Tensorflow.getDetections();
            if (list!=null) {
                StringBuilder builder = new StringBuilder(list.length); 
                for (API.Tensorflow.DetectedObject obj : list) {
                    if (obj.getConfidence()>0.5) {
                        builder.append(System.lineSeparator()).append(obj.getLabel()).append(':');
                        builder.append(System.lineSeparator()).append("  Bottom-Left: ").append('(').append(obj.getX1()).append(", ").append(obj.getY1()).append(')');
                        builder.append(System.lineSeparator()).append("  Top-Right: ").append('(').append(obj.getX2()).append(", ").append(obj.getY2()).append(')');
                        builder.append(System.lineSeparator()).append("  Confidence: ").append(obj.getConfidence()*100).append('%');
                        API.print(builder.toString());
                        last = System.currentTimeMillis();
                    }
                }
            }
            if (System.currentTimeMillis()-last>1000) API.print("");
        }
        API.uninit();
    }
}
