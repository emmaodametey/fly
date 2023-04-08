package org.example.vm;

import java.time.LocalDate;

public abstract class VirtualMachine {
    private String hostName;
    private String requester;
    private int numberOfCpu;
    private int sizeOfRam;
    private int sizeOfHardDisk;
    private LocalDate date;

    public VirtualMachine(String hostName, String requester,
                          int numberOfCpu, int sizeOfRam, int sizeOfHardDisk) {
        this.hostName = hostName;
        this.requester = requester;
        this.numberOfCpu = numberOfCpu;
        this.sizeOfRam = sizeOfRam;
        this.sizeOfHardDisk = sizeOfHardDisk;
        date = LocalDate.now();
    }

    public String getHostName() {
        return hostName;
    }

    public String getRequester() {
        return requester;
    }

    public int getNumberOfCpu() {
        return numberOfCpu;
    }

    public int getSizeOfRam() {
        return sizeOfRam;
    }

    public int getSizeOfHardDisk() {
        return sizeOfHardDisk;
    }

    public void setHostName(String requestNumber) {
        String date = this.date.toString();
        this.hostName = "host"+ date + requestNumber;
    }
}
