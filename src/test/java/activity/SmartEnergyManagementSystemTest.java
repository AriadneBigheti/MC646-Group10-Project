package activity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import activity.SmartEnergyManagementSystem.DeviceSchedule;
import activity.SmartEnergyManagementSystem.EnergyManagementResult;

public class SmartEnergyManagementSystemTest {

    private SmartEnergyManagementSystem smartSystem;

    @BeforeEach
    public void setUp() {
        smartSystem = new SmartEnergyManagementSystem();
    }
    
    @Test
    public void TC1() {

        double currentPrice = 0.75;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 1);
        dPriorities.put("Appliances", 3);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 14, 30);
        double currentTemperature = 21.5;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 25;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        assertTrue(result.deviceStatus.getOrDefault("Lights", false));
        assertTrue(result.energySavingMode);
        assertFalse(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }

    @Test
    public void TC2() {
        double currentPrice = 0.45;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 1);
        dPriorities.put("Appliances", 3);
        dPriorities.put("Security", 4);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 2, 30);
        double currentTemperature = 21.5;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 25;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        assertTrue(result.deviceStatus.getOrDefault("Security", false));
        assertFalse(result.energySavingMode);
        assertFalse(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }

    @Test
    public void TC3() {
        double currentPrice = 0.45;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 1);
        dPriorities.put("Appliances", 3);
        dPriorities.put("Security", 4);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 8, 30);
        double currentTemperature = 18;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 25;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        assertTrue(result.deviceStatus.getOrDefault("Heating", false));
        assertFalse(result.energySavingMode);
        assertTrue(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }

    @Test
    public void TC4() {
        double currentPrice = 0.45;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 1);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 8, 30);
        double currentTemperature = 21.5;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 29;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        assertFalse(result.deviceStatus.getOrDefault("Heating", true));
        assertTrue(result.deviceStatus.getOrDefault("Lights", false));
        assertFalse(result.energySavingMode);
        assertFalse(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }

    @Test
    public void TC5() {
        double currentPrice = 0.75;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 3);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 8, 30);
        double currentTemperature = 21.5;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 29;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();
        DeviceSchedule deviceSchedule = new DeviceSchedule("Heating", LocalDateTime.of(2023, 10, 5, 8, 30));
        scheduledDevices.add(deviceSchedule);

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        System.out.println(result.deviceStatus);
        assertTrue(result.deviceStatus.getOrDefault("Heating", false));
        assertFalse(result.deviceStatus.getOrDefault("Lights", true));
        assertTrue(result.energySavingMode);
        assertFalse(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }

    @Test
    public void TC6() {
        double currentPrice = 0.45;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 1);
        dPriorities.put("Appliances", 3);
        dPriorities.put("Security", 4);
        dPriorities.put("Refrigerator", 1);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 23, 0);
        double currentTemperature = 21.5;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 25;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        assertTrue(result.deviceStatus.getOrDefault("Security", false));
        assertTrue(result.deviceStatus.getOrDefault("Refrigerator", false));
        assertFalse(result.energySavingMode);
        assertFalse(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }

    @Test
    public void TC7() {
        double currentPrice = 0.45;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 1);
        dPriorities.put("Appliances", 3);
        dPriorities.put("Security", 4);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 8, 30);
        double currentTemperature = 25;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 25;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        assertTrue(result.deviceStatus.getOrDefault("Heating", false));
        assertFalse(result.energySavingMode);
        assertTrue(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }

    @Test
    public void TC8() {
        double currentPrice = 0.75;
        double priceThreshold = 0.5;
        Map<String, Integer> dPriorities = new HashMap<>();
        dPriorities.put("Heating", 3);
        dPriorities.put("Lights", 1);
        dPriorities.put("Appliances", 3);
        dPriorities.put("Security", 4);
        LocalDateTime currentTime = LocalDateTime.of(2023, 10, 5, 8, 30);
        double currentTemperature = 21;
        double[] tempRange = new double[]{20.0, 24.0};
        double energyUsageLimit = 30;
        double totalEnergyUsedToday = 25;
        List<DeviceSchedule> scheduledDevices = new ArrayList<>();
        DeviceSchedule deviceSchedule = new DeviceSchedule("Security", LocalDateTime.of(2023, 10, 5, 8, 40));
        scheduledDevices.add(deviceSchedule);

        EnergyManagementResult result = smartSystem.manageEnergy(currentPrice,
            priceThreshold, dPriorities, currentTime, currentTemperature,
            tempRange, energyUsageLimit, totalEnergyUsedToday, scheduledDevices);

        assertFalse(result.deviceStatus.getOrDefault("Security", true));
        assertTrue(result.energySavingMode);
        assertFalse(result.temperatureRegulationActive);
        assertEquals(result.totalEnergyUsed, totalEnergyUsedToday);
    }
}
