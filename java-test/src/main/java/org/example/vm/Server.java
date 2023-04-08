package org.example.vm;

import org.example.employees.Employees;

import java.util.Map;
import java.util.Set;

public class Server extends VirtualMachine{
    private String linuxDistributionName;
    private int majorNumberOfDistribution;
    private  String kernelVersion;
    Set<String> serverManagers;

    public Server(String hostName, String requester, int numberOfCpu,
                  int sizeOfRam, int sizeOfHardDisk, String linuxDistributionName,
                   int majorNumberOfDistribution, String kernelVersion, Set<String> serverManagers) {
        super(hostName, requester, numberOfCpu, sizeOfRam, sizeOfHardDisk);
        this.linuxDistributionName = linuxDistributionName;
        this.majorNumberOfDistribution = majorNumberOfDistribution;
        this.kernelVersion = kernelVersion;
        this.serverManagers = serverManagers;
    }
}
