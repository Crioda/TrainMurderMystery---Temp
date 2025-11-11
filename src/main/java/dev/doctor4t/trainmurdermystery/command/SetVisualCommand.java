package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import dev.doctor4t.trainmurdermystery.cca.TrainWorldComponent;
import dev.doctor4t.trainmurdermystery.command.argument.TimeOfDayArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SetVisualCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tmm:setVisual")
                        .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("snow")
                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> executeBoolean(context.getSource(), TrainWorldComponent::setSnow, BoolArgumentType.getBool(context, "enabled")))))
                .then(CommandManager.literal("fog")
                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> executeBoolean(context.getSource(), TrainWorldComponent::setFog, BoolArgumentType.getBool(context, "enabled")))))
                .then(CommandManager.literal("screenshake")
                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> executeBoolean(context.getSource(), TrainWorldComponent::setScreenshake, BoolArgumentType.getBool(context, "enabled")))))
                .then(CommandManager.literal("time")
                        .then(CommandManager.argument("timeOfDay", TimeOfDayArgumentType.timeofday())
                                .executes(context -> executeTimeOfDay(context.getSource(), TimeOfDayArgumentType.getTimeofday(context, "timeOfDay")))))
        );
    }

    private static int  executeBoolean(ServerCommandSource source, BiConsumer<TrainWorldComponent, Boolean> consumer, boolean enabled) {
        consumer.accept(TrainWorldComponent.KEY.get(source.getWorld()), enabled);
        return 1;
    }

    private static int executeTimeOfDay(ServerCommandSource source, TrainWorldComponent.TimeOfDay timeOfDay) {
        TrainWorldComponent trainWorldComponent = TrainWorldComponent.KEY.get(source.getWorld());
        trainWorldComponent.setTimeOfDay(timeOfDay);
        return 1;
    }

}
