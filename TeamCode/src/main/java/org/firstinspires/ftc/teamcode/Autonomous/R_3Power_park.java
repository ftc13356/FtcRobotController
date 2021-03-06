package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.Accesories.WobbleGoal;
import org.firstinspires.ftc.teamcode.Components.BasicChassis;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name= "R_3Power_park")
public class R_3Power_park extends LinearOpMode {
    @Override
    public void runOpMode(){
        Robot robot = new Robot(this, BasicChassis.ChassisType.ODOMETRY, true, false);
        ElapsedTime runtime = new ElapsedTime();

        int rings = robot.getRingsAndWaitForStart();

//        waitForStart();
//        rings = robot.tensorFlow.getNumberOfRings();
        robot.stopRingDetection();
        robot.moveWobbleGoalToPosition(WobbleGoal.Position.RUN);
        //robot.navigate();
        //robot.setPosition(60.25,-32.25, 0);//high goal -12,22
        if(rings!=1&&rings!=4) {
            robot.goToPosition( -64,-1,0, 1);//-25,60
            robot.openWobbleGoalClaw();
            sleep(250);
            robot.goToPosition(-60.5,29.8,-2,0.8);//37,4
        }
        else if(rings==1) {
            robot.goToPosition(-98,12,0,1);
            robot.openWobbleGoalClaw();
            sleep(250);
            robot.goToPosition(-60.5,21,-2.5,0.7);
        }
        else if(rings==4) {
//            robot.moveAngle(-2.5, -113,1.0);
            robot.goToPosition( -120,-1,0, 1);//-25,60
            robot.openWobbleGoalClaw();
            sleep(250);
//            robot.moveAngle(34,51.5,1.0);
            robot.goToPosition(-59.5,30,-2,0.8);

        }
        robot.shootThreePowerShot();
        robot.turnInPlace(0,1.0);
        robot.moveWobbleGoalToPosition(WobbleGoal.Position.GRAB);
        if(rings!=4) {

            if (rings!=1&&rings!=4) {
                robot.turnInPlace(0, 1.0);
                //robot.moveAngle(6.5, 49.5, 0.8);
                robot.goToPosition(-53.5+43.5,32+3,0,0.8);
                robot.turnInPlace(0,0.6);
                //robot.moveAngle(-4.0,0,0.8);
                robot.goToPosition(-53.5+43.5,32+3-6.9,0,0.8);
                robot.closeWobbleGoalClaw();
                sleep(600);
                robot.moveWobbleGoalToPosition(WobbleGoal.Position.RUN);
                //robot.moveAngle(-23,-59,0.7);
                robot.goToPosition(-53.5+43.5-56.5,32+3-6.5-28,0,0.8);
                robot.openWobbleGoalClaw();
                sleep(250);
                //robot.moveAngle(10,-2,0.7);
                robot.goToPosition(-53.5+43.5-59-2,32+6.5-4-23+10,0,0.8);
                robot.moveWobbleGoalToPosition(WobbleGoal.Position.REST);
                sleep(1000);

            }
            if (rings == 1) {
                robot.turnInPlace(0, 1.0);
//                robot.moveAngle(4.25, 47.5, 0.8);
                robot.goToPosition(-53.5+43.5,26,0,0.8);
                robot.turnInPlace(0,1.0);
////                robot.moveAngle(-4.0,0,0.8);
                robot.goToPosition(-53.5+43.5,19,0,0.8);
                sleep(250);
                robot.closeWobbleGoalClaw();
                sleep(600);
                robot.moveWobbleGoalToPosition(WobbleGoal.Position.RUN);
                robot.goToPosition(-88,12,0,0.8);
//                robot.moveAngle(-0.5,-80,0.8);
                robot.openWobbleGoalClaw();
                sleep(250);
//                robot.moveAngle(5,15,0.8);
                robot.goToPosition(-72,17,0,1);
                robot.moveWobbleGoalToPosition(WobbleGoal.Position.REST);
                sleep(1000);
            }
        }
        else{
            robot.turnInPlace(0, 1.0);
            robot.moveAngle(1, 49.2, 1.0);
            robot.turnInPlace(0,1.0);
            robot.moveAngle(-3,0,0.8);
            robot.closeWobbleGoalClaw();
            sleep(250);
            robot.moveWobbleGoalToPosition(WobbleGoal.Position.RUN);
            robot.moveAngle(-27,-100,1.0);
            robot.openWobbleGoalClaw();
            robot.moveAngle(15,40,1.0);
        }
        sleep(1000);
        stop();
    }



}
