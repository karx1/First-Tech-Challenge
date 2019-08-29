package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class API {
  static void init(HardwareMap map) {
    M0("m0");
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
      motor.setPower(power)
    }
