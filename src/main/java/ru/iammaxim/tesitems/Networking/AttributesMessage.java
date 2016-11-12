package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ru.iammaxim.tesitems.TESItems;

import java.util.HashMap;

/**
 * Created by Maxim on 11.06.2016.
 */
public class AttributesMessage implements IMessage {
    private HashMap<String, Float> attributes;

    public AttributesMessage() {
        attributes = new HashMap<>();
    }

    public AttributesMessage(HashMap<String, Float> attrs) {
        attributes = attrs;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        System.out.println("received message size: " + buf.capacity() + " " + buf.maxCapacity());
        for (String s : TESItems.ATTRIBUTES) {
            attributes.put(s, buf.readFloat());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        for (String s : TESItems.ATTRIBUTES) {
            buf.writeFloat(getAttribute(s));
        }
    }

    public float getAttribute(String s) {
        Float value = attributes.get(s);
        if (value == null) return 0;
        return value;
    }
}
