package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Camera.SwitchPipelineToTargeting;
import frc.robot.commands.Flywheel.SpinUpFromTable;
import frc.robot.commands.Indexer.MoveIndexerFor;
import frc.robot.commands.Storage.MoveStorageFor;
import frc.robot.commands.Storage.StorageIn;
import frc.robot.enums.StorageStage;
import frc.robot.commands.Micellaneous.Delay;

public class FeedShot extends SequentialCommandGroup {
	public FeedShot() {
        addCommands(
                sequence(
                    parallel(
                        new SpinUpFromTable(),
                        sequence(
                            new Delay(1500),
                            // new MoveIndexerFor(3000),
                            parallel(
                                new MoveIndexerFor(0),
                                new StorageIn()
                            )
                        )
                    )
                )
        );
	}
}

// public class FeedShot extends SequentialCommandGroup {
// 	public FeedShot() {
//         addCommands(
//             parallel(
//                                 new MoveIndexerFor(0),
//                                 new MoveStorageFor(0.7, StorageStage.BOTH, 0)
//             )
//             // parallel(
//             //     sequence(
//             //         new Delay(1000),
//             //         parallel(
//             //             new SpinUpFromTable(),
//             //             sequence(
//             //                 new MoveIndexerFor(3000),
//             //                 parallel(
//                 //                     new MoveIndexerFor(0),
//                 //                     new MoveStorageFor(0.7, StorageStage.BOTH, 0)
//                 //                 )
//                 //             )
//                 //         )
//             //     )
//             // )
//         );
// 	}
// }