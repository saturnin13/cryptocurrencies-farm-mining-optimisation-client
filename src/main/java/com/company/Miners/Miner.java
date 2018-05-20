package com.company.Miners;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.ClientConfiguration;
import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OSType;
import com.company.Client.JsonFormat.General.GPU.GPU;
import com.company.Client.JsonFormat.General.MinedCurrencyShortName;
import com.company.CommandsExecutor.CommandExecutor;
import com.company.CommandsExecutor.CommandOutputMonitoring.CommandOutputMonitor;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.company.Client.JsonFormat.ClientJson.MiningConfiguration.ClientConfiguration.OS.OSType.*;
import static com.company.Variables.HOME_FOLDER;
import static com.company.Variables.LOCATION_MAIN_FOLDER;

public abstract class Miner {

    private final static Logger logger = Logger.getLogger(Miner.class);

    // TODO: refactor this pooladdress thing
    protected Protocol protocol;
    protected String poolAddressProtocol1;
    protected int poolPortProtocol1;
    protected String poolAddressProtocol2;
    protected int poolPortProtocol2;

    protected MinedCurrencyShortName minedCurrencyShortName;
    protected String processName;
    protected GPU gpu; // TODO use this in all the call
    protected List<String> downloadLinksWindows;
    protected List<String> downloadLinksLinux;
    protected List<String> downloadLinksMac;

    private CommandExecutor miningThread;

    /***************************************************************************************************************/
    /**************************************************** MINING ***************************************************/
    /***************************************************************************************************************/

    public void startMining(OSType currentOsType, Protocol protocol, GPU gpu) {
        logger.info("Starting to mine " + minedCurrencyShortName + " miner");
        this.gpu = gpu;
        this.protocol = protocol;
        if(currentOsType == MAC) {
            miningThread = getMiningThreadMac();
        } else if(currentOsType == LINUX) {
            miningThread = getMiningThreadLinux();
        } else if(currentOsType == WINDOWS) {
            miningThread = getMiningThreadWindows();
        } else {
            logger.info("Could not determine the os type for starting to mine: " + minedCurrencyShortName);
        }
        miningThread.start();
        logger.info("Mining thread launched for " + minedCurrencyShortName);
    }

    public void stopMining() {
        if(miningThread != null && miningThread.isAlive()) {
            miningThread.exit();
        }
    }

    /***************************************************************************************************************/
    /*************************************************** WINDOWS ***************************************************/
    /***************************************************************************************************************/

    private CommandExecutor getMiningThreadWindows() {
        miningSetUpActionsWindows();
        return CommandExecutor.builder()
                .commands(getMiningCommandsWindows())
                .cleanUpCommands(getMiningCleanUpCommandsWindows())
                .outputMonitor(getOutputMonitoring())
                .verbose(true)
                .build();
    }

    private List<String> getMiningCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + processName)
                .addAll(getGpuInitialiseCommandsWindows())
                .addAll(getExecuteMiningCommandsWindows())
                .build();
    }

    protected void miningSetUpActionsWindows()  {}

    private List<String> getGpuInitialiseCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("setx GPU_FORCE_64BIT_PTR 0")
                .add("setx GPU_MAX_HEAP_SIZE 100")
                .add("setx GPU_USE_SYNC_OBJECTS 1")
                .add("setx GPU_MAX_ALLOC_PERCENT 100")
                .add("setx GPU_SINGLE_ALLOC_PERCENT 100")
                .build();
    }

    protected abstract List<String> getExecuteMiningCommandsWindows();

    private List<String> getMiningCleanUpCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("Stop-Process -Name " + processName)
                .build();
    }

    /***************************************************************************************************************/
    /**************************************************** LINUX ****************************************************/
    /***************************************************************************************************************/

    private CommandExecutor getMiningThreadLinux() {
        miningSetUpActionsLinux();
        // TODO: check that bash works on LINUX
        return CommandExecutor.builder()
                .commands(getMiningCommandsLinux())
                .cleanUpCommands(getMiningCleanUpCommandsLinux())
                .outputMonitor(getOutputMonitoring())
                .verbose(true)
                .build();
    }

    private List<String> getMiningCommandsLinux() {
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + processName)
                .addAll(getGpuInitialiseCommandsLinux())
                .addAll(getExecuteMiningCommandsLinux())
                .build();
    }

    protected void miningSetUpActionsLinux() {}

    private List<String> getGpuInitialiseCommandsLinux() {
        return new LinkedList<>();
    }

    protected abstract List<String> getExecuteMiningCommandsLinux();

    private List<String> getMiningCleanUpCommandsLinux() {
        return new LinkedList<>();
    }

    /***************************************************************************************************************/
    /***************************************************** MAC *****************************************************/
    /***************************************************************************************************************/


    private CommandExecutor getMiningThreadMac() {
        miningSetUpActionsMac();
        return CommandExecutor.builder()
                .commands(getMiningCommandsMac())
                .cleanUpCommands(getMiningCleanUpCommandsMac())
                .outputMonitor(getOutputMonitoring())
                .verbose(true)
                .build();
    }

    private List<String> getMiningCommandsMac() {
        return new ImmutableList.Builder<String>()
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + processName)
                .addAll(getGpuInitialiseCommandsMac())
                .addAll(getExecuteMiningCommandsMac())
                .build();
    }

    protected void miningSetUpActionsMac()  {}

    private List<String> getGpuInitialiseCommandsMac() {
        return new ImmutableList.Builder<String>()
                .add("export GPU_FORCE_64BIT_PTR=0")
                .add("export GPU_MAX_HEAP_SIZE=100")
                .add("export GPU_USE_SYNC_OBJECTS=1")
                .add("export GPU_MAX_ALLOC_PERCENT=100")
                .add("export GPU_SINGLE_ALLOC_PERCENT=100")
                .build();
    }

    protected abstract List<String> getExecuteMiningCommandsMac();

    private List<String> getMiningCleanUpCommandsMac() {
        return new ImmutableList.Builder<String>()
                //TODO: test this line on MAC
                .add("pkill -f " + processName)
                .build();
    }

    /***************************************************************************************************************/
    /************************************************ INSTALLATION *************************************************/
    /***************************************************************************************************************/

    public boolean install(OSType currentOsType) {
        logger.info("installing " + minedCurrencyShortName + " miner: " + processName);
        if(currentOsType == MAC) {
            installMac();
        } else if(currentOsType == LINUX) {
            installLinux();
        } else if(currentOsType == WINDOWS) {
            installWindows();
        } else {
            logger.info("Could not determine the os type for installation: " + minedCurrencyShortName);
            return false;
        }
        logger.info("Installation completed successfully for " + minedCurrencyShortName + " miner");
        return true;
    }

    /***************************************************************************************************************/
    /*************************************************** WINDOWS ***************************************************/
    /***************************************************************************************************************/

    private void installWindows() {
        CommandExecutor commandExecutor = CommandExecutor.builder()
                .commands(getInstallCommandsWindows())
                .verbose(true)
                .build();
        commandExecutor.run();
    }

    protected List<String> getInstallCommandsWindows() {
        // TODO add a folder creation step before download
        return new ImmutableList.Builder<String>()
                .addAll(getDownloadCommandsWindows())
                .add("cd ~/mining-optimisation/" + processName)
                .addAll(getInstallConfigCommandsWindows())
                .build();
    }

    private List<String> getDownloadCommandsWindows() {
        return new ImmutableList.Builder<String>()
                .add("cd ~/Downloads/")
                .add("[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12") // by default TLS 1.0 is used, 1.2 is set
                .addAll(downloadLinksWindows.stream()
                        .map(link -> "Invoke-WebRequest -Uri \"" + link + "\" -OutFile " + link.substring(link.lastIndexOf('/') + 1, link.length()))
                        .collect(Collectors.toList()))
                .add("mkdir -Force " + LOCATION_MAIN_FOLDER + "/" + processName)
                .add("cd " + LOCATION_MAIN_FOLDER + "/" + processName)
                .addAll(downloadLinksWindows.stream()
                        .map(link -> {
                            String extract = (link.substring(link.lastIndexOf('.') + 1, link.length())).equals("7z") ? "7z e" : "Expand-Archive -Force";
                            return extract + " " + HOME_FOLDER + "/Downloads/" + link.substring(link.lastIndexOf('/') + 1, link.length());
                        })
                        .collect(Collectors.toList()))
                .addAll(downloadLinksWindows.stream()
                        .map(link -> "rm ~/Downloads/" + link.substring(link.lastIndexOf('/') + 1, link.length()))
                        .collect(Collectors.toList()))
                .build();
    }

    protected List<String> getInstallConfigCommandsWindows() {
        return new LinkedList<>();
    }

    /***************************************************************************************************************/
    /**************************************************** LINUX ****************************************************/
    /***************************************************************************************************************/

    private void installLinux() {
        // TODO: check that bash works on LINUX
        CommandExecutor commandExecutor = CommandExecutor.builder()
                .commands(getInstallCommandsLinux())
                .verbose(true)
                .build();
        commandExecutor.run();
    }

    protected List<String> getInstallCommandsLinux() {
        return new ImmutableList.Builder<String>()
                .addAll(getDownloadCommandsLinux())
                .add("cd ~/mining-optimisation/" + processName)
                .addAll(getInstallConfigCommandsLinux())
                .build();
    }

    private List<String> getDownloadCommandsLinux() {
        return new LinkedList<>();
    }

    protected List<String> getInstallConfigCommandsLinux() {
        return new LinkedList<>();
    }

    /***************************************************************************************************************/
    /***************************************************** MAC *****************************************************/
    /***************************************************************************************************************/

    private void installMac() {
        CommandExecutor commandExecutor = CommandExecutor.builder()
                .commands(getInstallCommandsMac())
                .verbose(true)
                .build();
        commandExecutor.run();
    }

    protected List<String> getInstallCommandsMac() {
        return new ImmutableList.Builder<String>()
                .addAll(getDownloadCommandsMac())
                .add("cd ~/mining-optimisation/" + processName)
                .addAll(getInstallConfigCommandsMac())
                .build();
    }

    private List<String> getDownloadCommandsMac() {
        return new ImmutableList.Builder<String>()
                .add("cd ~/Downloads/")
                .addAll(downloadLinksWindows.stream()
                        .map(link -> "curl -L -O " + link)
                        .collect(Collectors.toList()))
                .add("cd ~/")
                .add("mkdir -p " + LOCATION_MAIN_FOLDER)
                .add("cd mining-optimisation")
                .add("mkdir -p " + processName)
                .add("cd " + processName)
                .addAll(downloadLinksWindows.stream()
                        .map(link -> {
                            String extract = (link.substring(link.lastIndexOf('.') + 1, link.length())).equals("7z") ? null : "gunzip -c"; // TODO 7zip case
                            return extract + " ~/Downloads/" + link.substring(link.lastIndexOf('/') + 1, link.length()) + " | tar xopf -";
                        })
                        .collect(Collectors.toList()))
                .addAll(downloadLinksWindows.stream()
                        .map(link -> "rm ~/Downloads/" + link.substring(link.lastIndexOf('/') + 1, link.length()))
                        .collect(Collectors.toList()))
                .build();
    }

    protected List<String> getInstallConfigCommandsMac() {
        return new LinkedList<>();
    }

    /***************************************************************************************************************/
    /**************************************************** OTHER ****************************************************/
    /***************************************************************************************************************/

    protected abstract CommandOutputMonitor getOutputMonitoring();

    public abstract boolean canMineOnMachine(ClientConfiguration clientConfiguration);

    public boolean isInstalled() {
        return new File(LOCATION_MAIN_FOLDER + "/" + processName).exists();
    }

}
