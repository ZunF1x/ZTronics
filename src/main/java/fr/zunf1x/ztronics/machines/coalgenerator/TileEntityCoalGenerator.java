package fr.zunf1x.ztronics.machines.coalgenerator;

import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.api.IEnergyStorage;
import cofh.redstoneflux.impl.EnergyStorage;
import fr.zunf1x.ztronics.blocks.BlockCoalGenerator;
import fr.zunf1x.ztronics.energy.TileEnergized;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityCoalGenerator extends TileEnergized implements ITickable, IEnergyReceiver, IEnergyStorage {

    private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
    private String customName;
    private int	burningTimeLeft	= 0;

    public TileEntityCoalGenerator() {
        super(new EnergyStorage(20000, 1000));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.stacks);

        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }
        this.burningTimeLeft = compound.getInteger("burningTimeLeft");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.stacks);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }

        compound.setInteger("burningTimeLeft", this.burningTimeLeft);

        return compound;
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    @Override
    public String getName() {
        return hasCustomName() ? this.customName : "tile.coal_generator";
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.burningTimeLeft;
            case 1:
                return this.storage.getEnergyStored();
            case 2:
                return this.storage.getMaxEnergyStored();
        }
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.burningTimeLeft = value;
                break;
            case 1:
                this.storage.setEnergyStored(value);
                break;
            case 2:
                this.storage.setCapacity(value);
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.stacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.stacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(stacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < this.stacks.size(); i++) {
            this.stacks.set(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public int getFullBurnTime() {
        return 300;
    }

    public boolean isBurning() {
        return burningTimeLeft > 0;
    }

    @Override
    public void update() {
        boolean flag = this.isBurning();

        outputEnergy();

        if (this.isBurning()) {
            this.burningTimeLeft--;
        }

        if (!this.world.isRemote) {
            if (!this.isBurning() && !this.getStackInSlot(0).isEmpty() && this.getField(1) < this.getField(2)) {
                this.burningTimeLeft = this.getFullBurnTime();
                this.decrStackSize(0, 1);
            }

            if (isBurning()) {
                storage.receiveEnergy(50, false);
            }

            if (flag != this.isBurning()) {
                BlockCoalGenerator.setState(this.isBurning(), this.world, this.pos);
            }
        }

        this.markDirty();
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored() {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return true;
    }
}
