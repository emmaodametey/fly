package org.example.services;

import org.example.vm.VirtualMachine;

public class BuildNewMachine implements SystemBuildService{
    @Override
    public String createNewMachine(VirtualMachine machine) {
        return machine.getHostName();
    }
}
