package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.List;

public class API {
    private static OpMode opmode;
    
    public static void init(OpMode mode) {
        opmode = mode;
        try {
        Tensorflow.init(mode);
        } catch (Exception e) {}
        HardwareMap map = mode.hardwareMap;
        for (Motor m : Motor.values()) {
            m.init(map);
        }
        for (Servo s : Servo.values()) {
            s.init(map);
        }
        //Sensors.Color.sensor = map.colorSensor.get("color");
        
    }
    
    public static void uninit() {
        Tensorflow.uninit();
    }
    
    public static void print(String s) {
        opmode.telemetry.addLine(s);
        opmode.telemetry.update();
    }
    
    public static void print(String s1, String s2) {
        opmode.telemetry.addData(s1, s2);
        opmode.telemetry.update();
    }
    
    public static void pause(double seconds) {
        try{
            Thread.sleep((long) (seconds*1000));
        } catch (Exception e) {}
    }
    
    
    public static class Sensors {
        public static class Color {
            static ColorSensor sensor;
            public static int getColor() {
                return sensor.argb();
            }
            public static int red() {
                return sensor.red();
            }
            public static int green() {
                return sensor.green();
            }
            public static int blue() {
                return sensor.blue();
            }
        }
    }
    
    public static enum Motor {
        M0("m0"), M1("m1"), M2("m2"), M3("m3");
        private final String name;
        private DcMotor motor;
        private Direction direction = Direction.FORWARD;
        private double power = 0;
        Motor(String name) {
            this.name = name;
        }
        
        void init(HardwareMap map) {
            motor = map.get(DcMotor.class, name);
            motor.setPower(0);

        }
        
        public void start(double power) {
            try{
                motor.setPower(power*direction.i);
                this.power = power;
            } catch (Exception e) {}    
        }
        
        public void stop() {
            start(0);
        }
        
        public void setDirection(Direction direction) {
            this.direction = direction;
            start(power);
        }
        
        public void resetEncoder(boolean enable) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            enableEncoder(enable);
        }
        
        public void enableEncoder(boolean enable) {
            if (enable) {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            } else {
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
        }
        
        public double getPosition() {
            return motor.getCurrentPosition();
        }
    }
    
    public static enum Servo {
        S0("s0"), S1("s1"), S2("s2"), S3("s3"), S4("s4"), S5("s5");
        private final String name;
        private com.qualcomm.robotcore.hardware.Servo servo;
        private Direction direction = Direction.FORWARD;
        private double power = 0;
        Servo(String name) {
            this.name = name;
        }
        
        void init(HardwareMap map) {
            servo = map.get(com.qualcomm.robotcore.hardware.Servo.class, name);
        }
        
        public void setPosition(double degrees) {
            servo.setPosition(degrees);
        }
        
        public void start(double power) {
            this.power = power;
            power*=direction.i;
            servo.setPosition(power/2+0.5);
        }
        
        public void stop() {
            start(0);
        }
        
        public void setDirection(Direction direction) {
            this.direction = direction;
            start(power);
        }
    }
    
    public static enum Direction {
        FORWARD(1), REVERSE(-1);
        private final int i;
        Direction(int i) {
            this.i = i;
        }
    }
    
    public static class Tensorflow {
        private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
        private static final String LABEL_FIRST_ELEMENT = "Stone";
        private static final String LABEL_SECOND_ELEMENT = "Skystone";
        private static final String VUFORIA_KEY = "AbZqUBf/////AAABmdauzrAsq0byktn+MGoDv6AVtY6HqhUiQ14aBYEpGOmGfnbbFdmgwa6/htC9DfZ4KUZvlUNw/tPqUrJ+JNRNXGBM+BEirc0I5e4AU+m88k6XnAu644uhF0c/3azsz7wE58spWZ8slYwFjqhnbvEvlHK1Ozrlq4G8ppOjVoyHzq8wceqcI4sPYQ9no+um2B6pDBe9iSiqVCPP+Dam0FHyW3IBHRz1tS7yJCZsbKcsq3CGsRuikUXOeeO4a+eN6h2x27l3mMO3QoWjteWOUGv/g1XhKCOEklgBxDn64+vS57Vz1cKQiHgdNPnvAcRXv3z8KWrsNJXpCIVsAvilg/e7XhZP9Wp4MwSmjv7cfIXcUdUr";

        private static VuforiaLocalizer vuforia;
        private static TFObjectDetector tfod;
        
        private static void init(OpMode opmode) {
            HardwareMap hardwareMap = opmode.hardwareMap;
            
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
                TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
                tfodParameters.minimumConfidence = 0.3;
                tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
                tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
            } else {
                print("Sorry! This device is not compatible with TFOD");
            }
            if (tfod != null) {
                tfod.activate();
            }
        }
        
        private static void uninit() {
            if (tfod != null) {
                tfod.shutdown();
            }
        }
        
        public static DetectedObject[] getDetections() {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions==null) return null;
            DetectedObject[] objects = new DetectedObject[updatedRecognitions.size()];
            for (int i = 0; i < objects.length; i++) objects[i] = new DetectedObject(updatedRecognitions.get(i));
            return objects;
        }
        
        public static class DetectedObject {
            private final int x1, x2, y1, y2;
            private final float confidence;
            private final String label;
            private DetectedObject(Recognition recognition) {
                label = recognition.getLabel();
                x1 = (int) recognition.getLeft();
                x2 = (int) recognition.getRight();
                y1 = (int) recognition.getBottom();
                y2 = (int) recognition.getTop();
                confidence = recognition.getConfidence();
            }
            
            public String getLabel() {
                return label;
            }
            
            public int getX1() {
                return x1;
            }
            
            public int getX2() {
                return x2;
            }
            
            public int getY1() {
                return y1;
            }
            
            public int getY2() {
                return y2;
            }
            
            public float getConfidence() {
                return confidence;
            }
        }
    }
}
