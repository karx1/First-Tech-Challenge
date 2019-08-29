package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class Test2 extends LinearOpMode {
  private Gyroscope imu;
  private DcMotor m1;
  
  @Override
  public void runOpMode() {
    m1 = hardwareMan.get(DcMotor.class, "m1");
    m1.setPower(0.5);
    sleep(10000L);
  }
}
