package ru.iammaxim.tesitems.Networking;

import net.minecraftforge.fml.relauncher.Side;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/24/17 at 2:48 PM.
 */
public class NetworkUtils {
    public static void registerMessages() {
        TESItems.networkWrapper.registerMessage(MessageOpenGui.Handler.class, MessageOpenGui.class, 0, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageAttributes.Handler.class, MessageAttributes.class, 1, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageCastSpell.Handler.class, MessageCastSpell.class, 2, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageSpellbook.Handler.class, MessageSpellbook.class, 3, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageInventoryUpdate.ClientHandler.class, MessageInventoryUpdate.class, 4, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageInventoryUpdate.ServerHandler.class, MessageInventoryUpdate.class, 5, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageInventory.Handler.class, MessageInventory.class, 6, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageEquip.ServerHandler.class, MessageEquip.class, 7, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageEquip.ClientHandler.class, MessageEquip.class, 8, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageItemDrop.ServerHandler.class, MessageItemDrop.class, 9, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageJournal.Handler.class, MessageJournal.class, 10, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageJournalAppend.Handler.class, MessageJournalAppend.class, 11, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageNPCUpdate.ServerHandler.class, MessageNPCUpdate.class, 12, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageNPCUpdate.ClientHandler.class, MessageNPCUpdate.class, 13, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageDialog.Handler.class, MessageDialog.class, 14, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageFactionList.Handler.class, MessageFactionList.class, 15, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageFaction.ServerHandler.class, MessageFaction.class, 16, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageFaction.ClientHandler.class, MessageFaction.class, 17, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageFactionRemove.ClientHandler.class, MessageFactionRemove.class, 18, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageFactionRemove.ServerHandler.class, MessageFactionRemove.class, 19, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageDialogSelectTopic.ServerHandler.class, MessageDialogSelectTopic.class, 20, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageShowNotification.Handler.class, MessageShowNotification.class, 21, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageQuestList.Handler.class, MessageQuestList.class, 22, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageOpenEditFaction.ServerHandler.class, MessageOpenEditFaction.class, 23, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageQuest.ServerHandler.class, MessageQuest.class, 24, Side.SERVER);
        TESItems.networkWrapper.registerMessage(MessageQuest.ClientHandler.class, MessageQuest.class, 25, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageLatestContainer.ClientHandler.class, MessageLatestContainer.class, 26, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageUpdateLatestContainer.ClientHandler.class, MessageUpdateLatestContainer.class, 27, Side.CLIENT);
        TESItems.networkWrapper.registerMessage(MessageUpdateLatestContainer.ServerHandler.class, MessageUpdateLatestContainer.class, 28, Side.SERVER);
    }
}
