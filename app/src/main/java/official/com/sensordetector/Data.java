package official.com.sensordetector;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class Data {

    public static ArrayList<Entry> current,frequency,power,phase,voltage;

    public static ArrayList<Entry> getCurrent() {
        return current;
    }

    public static void setCurrent(ArrayList<Entry> current) {
        Data.current = current;
    }

    public static ArrayList<Entry> getFrequency() {
        return frequency;
    }

    public static void setFrequency(ArrayList<Entry> frequency) {
        Data.frequency = frequency;
    }

    public static ArrayList<Entry> getPower() {
        return power;
    }

    public static void setPower(ArrayList<Entry> power) {
        Data.power = power;
    }

    public static ArrayList<Entry> getPhase() {
        return phase;
    }

    public static void setPhase(ArrayList<Entry> phase) {
        Data.phase = phase;
    }

    public static ArrayList<Entry> getVoltage() {
        return voltage;
    }

    public static void setVoltage(ArrayList<Entry> voltage) {
        Data.voltage = voltage;
    }

    public static ArrayList<Entry> getCurrentPoints(){
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,20));
        values.add(new Entry(15,40));
        values.add(new Entry(25,30));
        values.add(new Entry(35,88));
        values.add(new Entry(45,120));
        return values;
    }

    public static ArrayList<Entry> getFrequencyPoints(){
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,20));
        values.add(new Entry(15,40));
        values.add(new Entry(25,66));
        values.add(new Entry(35,96));
        values.add(new Entry(45,120));
        return values;
    }

    public static ArrayList<Entry> getPowerPoints(){
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,20));
        values.add(new Entry(15,40));
        values.add(new Entry(25,34));
        values.add(new Entry(35,88));
        values.add(new Entry(45,120));
        return values;
    }

    public static ArrayList<Entry> getPhasePoints(){
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,20));
        values.add(new Entry(15,40));
        values.add(new Entry(25,77));
        values.add(new Entry(35,88));
        values.add(new Entry(45,55));
        return values;
    }

    public static ArrayList<Entry> getVoltagePoints(){
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0,20));
        values.add(new Entry(15,15));
        values.add(new Entry(25,60));
        values.add(new Entry(35,96));
        values.add(new Entry(45,120));
        return values;
    }
}
