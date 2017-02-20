package ru.iammaxim.tesitems.Scripting;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionDumpVariableStorage;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionPrint;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.NPC.ValueNPC;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player.ValuePlayer;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Quest.ValueQuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.VariableStorage;

/**
 * Created by maxim on 11/5/16 at 6:58 PM.
 */
public class ScriptEngine {
    public static Runtime runtime;

    static {
        runtime = new Runtime();
        runtime.variableStorage.setField("print", new FunctionPrint());
        runtime.variableStorage.setField("dumpVarStorage", new FunctionDumpVariableStorage());
    }

    public static void processScript(NPC npc, EntityPlayer caller, ValueObject object, VariableStorage variableStorage, QuestInstance questInst) {
        try {
            ValueFunction onTopicClick = (ValueFunction) object.getField("onTopicClick");
            if (onTopicClick != null) {
                onTopicClick.call(runtime, new ValueNPC(npc), new ValuePlayer(caller), object, variableStorage, new ValueQuestInstance(questInst));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
