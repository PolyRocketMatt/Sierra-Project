package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.factories.InterfaceFactory;
import com.github.itheos.sierra.handlers.handles.Handle;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * Handler that processes GUI requests.
 */

public class GuiHandler extends Handler {

    public enum GUI {
        DEBUG
    }

    private HandlerState state;

    public GuiHandler() {
        super();

        this.state = HandlerState.RUNNING;
    }

    /**
     * Get a specific GUI.
     *
     * @param gui the GUI
     * @return the ChestGui interface
     */
    public ChestGui getInterface(Player player, GUI gui) {
        ChestGui chestGui = null;

        switch (gui) {
            default:
            case DEBUG:
                chestGui = InterfaceFactory.buildDebugGui();

                break;
        }



        return chestGui;
    }

    @Nullable
    @Override
    public Handle getHandle() {
        return null;
    }

    @NotNull
    @Override
    public HandlerState getState() {
        return state;
    }

    @NotNull
    @Override
    public String getName() {
        return "GuiHandler";
    }

    @Override
    public void suspend() {
        state = HandlerState.SUSPENDED;
    }
}
