package simpleclient.adapter;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ItemAdapter {
    public static net.minecraft.world.item.ItemStack adapt(simpleclient.item.ItemStack stack) {
        net.minecraft.world.item.Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(stack.getItem().getId()));
        net.minecraft.world.item.ItemStack adapted = new net.minecraft.world.item.ItemStack(item);
        adapted.setCount(stack.getCount());
        adapted.setDamageValue(stack.getDamage());
        return adapted;
    }

    public static simpleclient.item.ItemStack adapt(net.minecraft.world.item.ItemStack stack) {
        simpleclient.item.Item item = new simpleclient.item.Item(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
        simpleclient.item.ItemStack adapted = new simpleclient.item.ItemStack(item);
        adapted.setCount(stack.getCount());
        adapted.setDamage(stack.getDamageValue());
        return adapted;
    }

    public static net.minecraft.world.entity.EquipmentSlot adapt(simpleclient.item.EquipmentSlot slot) {
        if (slot == simpleclient.item.EquipmentSlot.MAINHAND) return net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        if (slot == simpleclient.item.EquipmentSlot.OFFHAND) return net.minecraft.world.entity.EquipmentSlot.OFFHAND;
        if (slot == simpleclient.item.EquipmentSlot.FEET) return net.minecraft.world.entity.EquipmentSlot.FEET;
        if (slot == simpleclient.item.EquipmentSlot.LEGS) return net.minecraft.world.entity.EquipmentSlot.LEGS;
        if (slot == simpleclient.item.EquipmentSlot.CHEST) return net.minecraft.world.entity.EquipmentSlot.CHEST;
        if (slot == simpleclient.item.EquipmentSlot.HEAD) return net.minecraft.world.entity.EquipmentSlot.HEAD;
        return null;
    }
}