package fr.zunf1x.ztronics.energy;

import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.api.IEnergyStorage;
import cofh.redstoneflux.impl.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public abstract class TileEnergized extends TileEntityLockable implements IEnergyHandler {

    public EnergyStorage storage;

    public TileEnergized(EnergyStorage storage) {
        this.storage = storage;
    }

    // NBT

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);

        return nbt;
    }

    // energy transfert

    public void outputEnergy() {
        for (EnumFacing f : EnumFacing.VALUES) {
            TileEntity te = getWorld().getTileEntity(new BlockPos(this.getPos().getX() + f.getFrontOffsetX(), this.getPos().getY() + f.getFrontOffsetY(), this.getPos().getZ() + f.getFrontOffsetZ()));
            if (te instanceof IEnergyReceiver)
                extractEnergy(((IEnergyReceiver) te).receiveEnergy(f.getOpposite(), extractEnergy(this.storage.getMaxExtract(), true), false), false);
            else if (te instanceof IEnergyStorage)
                extractEnergy(((IEnergyStorage) te).receiveEnergy(extractEnergy(this.storage.getMaxExtract(), true), false), false);
        }
    }

    public void inputEnergy() {
        for (EnumFacing f : EnumFacing.VALUES) {
            TileEntity te = getWorld().getTileEntity(new BlockPos(this.getPos().getX() + f.getFrontOffsetX(), this.getPos().getY() + f.getFrontOffsetY(), this.getPos().getZ() + f.getFrontOffsetZ()));
            if (te instanceof IEnergyProvider)
                receiveEnergy(((IEnergyProvider) te).extractEnergy(f.getOpposite(), receiveEnergy(this.storage.getMaxReceive(), true), false), false);
            else if (te instanceof IEnergyStorage)
                receiveEnergy(((IEnergyStorage) te).extractEnergy(receiveEnergy(this.storage.getMaxReceive(), true), false), false);
        }
    }

    // energy

    public int receiveEnergy(int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        return storage.extractEnergy(Math.min(maxExtract, storage.getMaxExtract()), simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing enumFacing) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing enumFacing) {
        return storage.getMaxEnergyStored();
    }
}
