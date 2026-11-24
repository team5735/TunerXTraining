// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.HootAutoReplay;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.fasterxml.jackson.databind.Module.SetupContext;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.generated.TunerConstants;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric();
    private final RobotContainer m_robotContainer;
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
        private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); 
    /* log and replay timestamp and joystick data */
    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();
    public final CommandXboxController driveController = new CommandXboxController(TunerConstants.DRIVE_CONTROLLER_PORT);
    public Robot() {
        TunerConstants constants;
        m_robotContainer = new RobotContainer();
        setUpDriverBindings();
    }
    
    public void setUpDriverBindings(){
        Trigger rotateLeft = driveController.leftStick().and(driveController.rightStick().negate());
        Trigger rotateRight = driveController.rightStick().and(driveController.leftStick().negate());
        Trigger straight = driveController.rightStick().and(driveController.leftStick());
        driveController.x().whileTrue(drivetrain.runOnce(()-> drive.withVelocityX(7)));
        rotateLeft.whileTrue(drivetrain.applyRequest(()->
                drive.withVelocityX(0)
                     .withVelocityY(0)
                     .withRotationalRate(MaxAngularRate)
            ));
        rotateLeft.whileFalse(drivetrain.applyRequest(()->
                drive.withVelocityX(0)
                     .withVelocityY(0)
                     .withRotationalRate(0)
        ));
        rotateRight.whileTrue(drivetrain.applyRequest(()->
                 drive.withVelocityX(0)
                      .withVelocityY(0)
                      .withRotationalRate(-MaxAngularRate)
        ));
        rotateRight.whileFalse(drivetrain.applyRequest(()->
                drive.withVelocityX(0)
                     .withVelocityY(0)
                     .withRotationalRate(0)
        ));
            straight.whileTrue(drivetrain.applyRequest(()->
                drive.withVelocityX(2)
                     .withVelocityY(2)
                     .withRotationalRate(0)
        ));
            straight.whileFalse(drivetrain.applyRequest(()->
                drive.withVelocityX(0)
                     .withVelocityY(0)
                     .withRotationalRate(0)    
            ));
            }
   public void distanceforTest(){
        if(driveController.x() != null){
            System.out.println();
        }

   }
    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run(); 
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(m_autonomousCommand);
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

  

    @Override
    public void simulationPeriodic() {}
}
