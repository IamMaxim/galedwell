package ru.iammaxim.tesitems.Player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 06.06.2016.
 */
public class PlayerAttributesCapabilityStorage implements Capability.IStorage<IPlayerAttributesCapability> {
    @Override
    public NBTBase writeNBT(Capability<IPlayerAttributesCapability> capability, IPlayerAttributesCapability cap, EnumFacing enumFacing) {
        NBTTagCompound nbttag = new NBTTagCompound();
        for (String s : TESItems.ATTRIBUTES) {
            nbttag.setFloat(s, cap.getAttribute(s));
        }
        nbttag.setString("password", cap.getPassword());
        nbttag.setTag("spellbook", cap.saveSpellbook(true));
        nbttag.setTag("inventory", cap.getInventory().writeToNBT());
        nbttag.setTag("quests", cap.saveQuests());
        nbttag.setString("journal", cap.getJournal());
        nbttag.setTag("variableStorage", cap.getVariableStorage().writeToNBT());
        nbttag.setInteger("gold", cap.getGold());
        return nbttag;
    }

    @Override
    public void readNBT(Capability<IPlayerAttributesCapability> capability, IPlayerAttributesCapability cap, EnumFacing enumFacing, NBTBase nbtBase) {
        NBTTagCompound tag = (NBTTagCompound) nbtBase;
        for (String s : TESItems.ATTRIBUTES) {
            cap.setAttribute(s, tag.getFloat(s));
        }
        cap.loadSpellbook(tag.getCompoundTag("spellbook"));
        cap.getInventory().loadFromNBT(tag.getCompoundTag("inventory"));
        cap.loadQuests(tag.getCompoundTag("quests"));
        cap.setJournal(tag.getString("journal"));
        cap.setVariableStorage(ValueObject.loadValueFromNBT(tag.getCompoundTag("variableStorage")));
        cap.setPassword(tag.getString("password"));
        cap.setGold(tag.getInteger("gold"));
    }
}
