package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class API {
    
    static void init(HardwareMap map) {
        for (Motor m : Motor.values()) {
            m.init(map);
        }
        Sensors.Color.sensor = map.colorSensor.get("color");
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
        Motor(String name) {
            this.name = name;
        }
        
        void init(HardwareMap map) {
            motor = map.get(DcMotor.class, name);
            motor.setPower(0);

        }
        
        public void start(double power) {
            try{
                motor.setPower(power);
            } catch (Exception e) {}    
        }
        
        public void stop() {
            motor.setPower(0);
        }
        
        public void setDirection(DcMotorSimple.Direction direction) {
            motor.setDirection(direction);
            
        }
    }
}
