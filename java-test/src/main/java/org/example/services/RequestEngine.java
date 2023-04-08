package org.example.services;

import org.example.vm.VirtualMachine;

import java.util.HashMap;
import java.util.Map;

public class RequestEngine implements VirtualMachineRequestor{
    private String userLogonName;
    private AuthorisingService authorisingService;
    private SystemBuildService systemBuildService;
    private Map<String, Map<String, Integer>> successfulBuilds;
    private Map<String, Integer>  failedbuilds;


    public RequestEngine() {
        successfulBuilds = new HashMap<>();
        failedbuilds = new HashMap<>();
    }

    public void setUserLogonName(String userLogonName) {
        this.userLogonName = userLogonName;
    }

    @Override
    public void createNewRequest(VirtualMachine machine) throws IllegalArgumentException, UserNotEntitledException, MachineNotCreatedException {
        if(!authorisingService.isAuthorised(this.userLogonName)){
           throw new UserNotEntitledException("User not entitled");
        }
        if(systemBuildService.createNewMachine(machine) == null){
            addUserToFailedBuilds(this.userLogonName);
            throw new MachineNotCreatedException("Cannot create machine");
        }else {
            addUserToSuccessfulBuilds(this.userLogonName, systemBuildService.createNewMachine(machine));
        }


    }

    public String getUserLogonName() {
        return userLogonName;
    }

    @Override
    public Map<String, Map<String, Integer>> totalBuildsByUserForDay() {
        return successfulBuilds;
    }

    @Override
    public int totalFailedBuildsForDay() {
        return  failedbuilds.size();
    }

    private void addUserToFailedBuilds(String user){
        if(failedbuilds.containsKey(user)){
            failedbuilds.put(user, failedbuilds.get(user) + 1);
        }else{
            failedbuilds.put(user, 1);
        }

    }
    private void addUserToSuccessfulBuilds(String user, String machineName){
        if(successfulBuilds.containsKey(user)){
            Map<String , Integer> userMachines = successfulBuilds.get(user);
            if(userMachines.containsKey(machineName)){
                userMachines.put(machineName, userMachines.get(machineName) + 1);
            }
            userMachines.putIfAbsent(machineName, 1);
        }
        else {
            Map<String , Integer> newUserMachines = new HashMap<>();
            successfulBuilds.put(user, newUserMachines);
            newUserMachines.put(machineName, 1);
        }
    }

    public int numberOfMachinesForUserPerMachine(String user, String machine){
        return successfulBuilds.get(user).get(machine);
    }

}
