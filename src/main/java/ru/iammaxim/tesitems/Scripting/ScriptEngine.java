package ru.iammaxim.tesitems.Scripting;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionPrint;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.VariableStorage;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 11/5/16 at 6:58 PM.
 */
public class ScriptEngine {
    public static Runtime runtime;

    static {
        runtime = new Runtime();
        runtime.variableStorage.setField("print", new FunctionPrint());
    }

    public static void processScript(NPC npc, EntityPlayer caller, String script, VariableStorage variableStorage, QuestInstance questInst) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
