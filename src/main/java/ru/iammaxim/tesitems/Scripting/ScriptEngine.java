package ru.iammaxim.tesitems.Scripting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Questing.QuestManager;
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
                    if (tokens[0].equals("setQuestStage")) {
                        if (tokens.length > 2) {
                            QuestInstance inst = cap.getQuest(Integer.parseInt(tokens[1]));
                            inst.stage = Integer.parseInt(tokens[2]);
                        }
                    } else if (tokens[0].equals("give")) {
                        if (tokens.length > 2) {
                            cap.getInventory().addItem(new ItemStack(Item.getByNameOrId(tokens[1]), Integer.parseInt(tokens[2])));
                        }
                    } else if (tokens[0].equals("remove")) {
                        if (tokens.length > 2) {
                            for (int i = Integer.parseInt(tokens[2]); i >= 0; --i) {
                                cap.getInventory().removeItem(Item.getByNameOrId(tokens[1]));
                            }
                        }
                    }
/*                    if (tokens[0].equals("call")) {
                        if (tokens.length > 2) {
                            Object target;
                            String targetStr = tokens[1];
                            if (targetStr.equals("npc")) {
                                target = npc;
                            } else if (targetStr.equals("player")) {
                                target = caller;
                            } else if (targetStr.equals("cap")) {
                                target = cap;
                            }
                            String functionName = tokens[2];
                            target.getClass().getMethod()
                        }
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
