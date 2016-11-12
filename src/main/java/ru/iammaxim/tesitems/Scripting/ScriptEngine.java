package ru.iammaxim.tesitems.Scripting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.TESItems;

import java.util.Scanner;

/**
 * Created by maxim on 11/5/16 at 6:58 PM.
 */
public class ScriptEngine {
    public static void processScript(EntityNPC npc, EntityPlayer caller, String script) {
        try {
            IPlayerAttributesCapability cap = TESItems.getCapability(caller);
            Scanner scanner = new Scanner(script);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                if (tokens.length > 0) {
                    switch (tokens[0]) {
                        case "setQuestStage":
                            QuestInstance inst = cap.getQuest(Integer.parseInt(tokens[1]));
                            inst.stage = Integer.parseInt(tokens[2]);
                            break;
                        case "give":
                            cap.getInventory().addItem(new ItemStack(Item.getByNameOrId(tokens[1]), Integer.parseInt(tokens[2])));
                            break;
                        case "remove":
                            for (int i = Integer.parseInt(tokens[2]); i >= 0; --i) {
                                cap.getInventory().removeItem(Item.getByNameOrId(tokens[1]));
                            }
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
