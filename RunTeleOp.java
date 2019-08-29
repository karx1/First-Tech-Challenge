package org.firstinspires.ftc.teamcode;

/*import all the modules*/
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class RunTeleOp extends LinearOpMode {
  private Gyroscope imu;
  private DcMotor m1;
  
  /*main OpMode function*/
  @Override
  public void runOpMode() {
    API.init(hardwareMap);
    API.Motor.M0.start(1.0);
    pause(4.0);
    API.Motor.M0.stop();
  }
  private void pause(double seconds) {
    sleep((long) ((seconds-1)*1000));
    
  }
}
