package frc.robot.Commands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class DriveController extends Command {

    private SwerveRequest.FieldCentric drive;
    private CommandSwerveDrivetrain drivetrain;

    private int counter;

    public DriveController(SwerveRequest.FieldCentric drive, CommandSwerveDrivetrain drivetrain) {
        this.drive = drive;
        this.drivetrain = drivetrain;
        this.addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        System.out.println("Initializing");
        counter = 0;
    }

    @Override
    public void execute() {
        this.drive = drive.withVelocityX(1);
        this.drivetrain.setControl(drive); 

        counter++;

        System.out.println("Moving Forwards");
    }

    @Override
    public void end(boolean interrupted) {
        this.drive = drive.withVelocityX(0);
        this.drivetrain.setControl(drive); 

        System.out.println("Stopping with flag: " + interrupted);
    }

    @Override
    public boolean isFinished() {
        return counter>100;
    }
}