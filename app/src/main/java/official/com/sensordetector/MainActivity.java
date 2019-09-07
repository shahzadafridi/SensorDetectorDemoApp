package official.com.sensordetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LineChart lineChartPower, lineChartCurrent, lineChartVoltage, lineChartFrequency, lineChartPhase;
    Spinner channels;
    int frequency[] = {R.color.frequency};
    int current[] = {R.color.current};
    int phase[] = {R.color.phase};
    int power[] = {R.color.power};
    int voltage[] = {R.color.voltage};
    LinearLayout linearLayout;
    TextView dataLoading, currentLabel, powerLabel, frequencyLabel, phaseLabel, voltageLabel;
    public static final String[] channelsArray = new String[]{"Select Channel", "1", "2",
            "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22",
            "23", "24"};
    ArrayAdapter<String> channelAdapter;
    ArrayList<ILineDataSet> frequencyDataSets = new ArrayList<>();
    ArrayList<ILineDataSet> powerDataSets = new ArrayList<>();
    ArrayList<ILineDataSet> currentDataSets = new ArrayList<>();
    ArrayList<ILineDataSet> voltageDataSets = new ArrayList<>();
    ArrayList<ILineDataSet> phaseDataSets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitUI();
        channelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, channelsArray);
        channels.setAdapter(channelAdapter);
        channels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String getChannel = channelsArray[i];
                linearLayout.setVisibility(View.VISIBLE);
                dataLoading.setVisibility(View.VISIBLE);

                lineChartVoltage.setVisibility(View.VISIBLE);
                lineChartPower.setVisibility(View.VISIBLE);
                lineChartPhase.setVisibility(View.VISIBLE);
                lineChartFrequency.setVisibility(View.VISIBLE);
                voltageLabel.setVisibility(View.VISIBLE);
                powerLabel.setVisibility(View.VISIBLE);
                phaseLabel.setVisibility(View.VISIBLE);
                frequencyLabel.setVisibility(View.VISIBLE);
                currentLabel.setVisibility(View.VISIBLE);
                lineChartCurrent.setVisibility(View.VISIBLE);

                frequencyDataSets.clear();
                powerDataSets.clear();
                currentDataSets.clear();
                voltageDataSets.clear();
                phaseDataSets.clear();
                loadAllData(getChannel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void InitUI() {
        currentLabel = (TextView) findViewById(R.id.current_label);
        powerLabel = (TextView) findViewById(R.id.power_label);
        frequencyLabel = (TextView) findViewById(R.id.Frequency_label);
        phaseLabel = (TextView) findViewById(R.id.phase_label);
        voltageLabel = (TextView) findViewById(R.id.voltage_label);
        lineChartCurrent = (LineChart) findViewById(R.id.lineChart_current);
        lineChartPower = (LineChart) findViewById(R.id.lineChart_power);
        lineChartVoltage = (LineChart) findViewById(R.id.lineChart_voltage);
        lineChartFrequency = (LineChart) findViewById(R.id.lineChart_frequency);
        lineChartPhase = (LineChart) findViewById(R.id.lineChart_phase);
        channels = (Spinner) findViewById(R.id.channels);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        dataLoading = (TextView) findViewById(R.id.dataloading);
    }

    private void loadAllData(String getChannel) {
        FirebaseDatabase.getInstance().getReference("data").child(getChannel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.e("Firebase", snapshot.getKey());
                            String key = snapshot.getKey();
                            if (key.contentEquals("current")) {
                                DataModel model = snapshot.getValue(DataModel.class);
                                ArrayList<Entry> values = new ArrayList<>();
                                values.add(new Entry(0, model.getV1()));
                                values.add(new Entry(15, model.getV2()));
                                values.add(new Entry(25, model.getV3()));
                                values.add(new Entry(35, model.getV4()));
                                values.add(new Entry(45, model.getV5()));
                                Data.setCurrent(values);

                            } else if (key.contentEquals("frequency")) {
                                DataModel model = snapshot.getValue(DataModel.class);
                                ArrayList<Entry> values = new ArrayList<>();
                                values.add(new Entry(0, model.getV1()));
                                values.add(new Entry(15, model.getV2()));
                                values.add(new Entry(25, model.getV3()));
                                values.add(new Entry(35, model.getV4()));
                                values.add(new Entry(45, model.getV5()));
                                Data.setFrequency(values);
                            } else if (key.contentEquals("phase")) {
                                DataModel model = snapshot.getValue(DataModel.class);
                                ArrayList<Entry> values = new ArrayList<>();
                                values.add(new Entry(0, model.getV1()));
                                values.add(new Entry(15, model.getV2()));
                                values.add(new Entry(25, model.getV3()));
                                values.add(new Entry(35, model.getV4()));
                                values.add(new Entry(45, model.getV5()));
                                Data.setPhase(values);
                            } else if (key.contentEquals("power")) {
                                DataModel model = snapshot.getValue(DataModel.class);
                                ArrayList<Entry> values = new ArrayList<>();
                                values.add(new Entry(0, model.getV1()));
                                values.add(new Entry(15, model.getV2()));
                                values.add(new Entry(25, model.getV3()));
                                values.add(new Entry(35, model.getV4()));
                                values.add(new Entry(45, model.getV5()));
                                Data.setPower(values);
                            } else if (key.contentEquals("voltage")) {
                                DataModel model = snapshot.getValue(DataModel.class);
                                ArrayList<Entry> values = new ArrayList<>();
                                values.add(new Entry(0, model.getV1()));
                                values.add(new Entry(15, model.getV2()));
                                values.add(new Entry(25, model.getV3()));
                                values.add(new Entry(35, model.getV4()));
                                values.add(new Entry(45, model.getV5()));
                                Data.setVoltage(values);
                            }
                        }
                        dataLoading.setVisibility(View.GONE);
                        drawCurrent();
                        drawPhase();
                        drawVoltage();
                        drawFrequency();
                        drawPower();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void current(View view) {
        lineChartVoltage.setVisibility(View.GONE);
        lineChartPower.setVisibility(View.GONE);
        lineChartPhase.setVisibility(View.GONE);
        lineChartFrequency.setVisibility(View.GONE);
        voltageLabel.setVisibility(View.GONE);
        powerLabel.setVisibility(View.GONE);
        phaseLabel.setVisibility(View.GONE);
        frequencyLabel.setVisibility(View.GONE);
        drawCurrent();
        currentLabel.setVisibility(View.VISIBLE);
        lineChartCurrent.setVisibility(View.VISIBLE);
    }

    public void phase(View view) {
        lineChartVoltage.setVisibility(View.GONE);
        lineChartPower.setVisibility(View.GONE);
        lineChartFrequency.setVisibility(View.GONE);
        currentLabel.setVisibility(View.GONE);
        lineChartCurrent.setVisibility(View.GONE);
        voltageLabel.setVisibility(View.GONE);
        powerLabel.setVisibility(View.GONE);
        frequencyLabel.setVisibility(View.GONE);
        drawPhase();
        lineChartPhase.setVisibility(View.VISIBLE);
        phaseLabel.setVisibility(View.VISIBLE);
    }

    public void Frequency(View view) {
        lineChartVoltage.setVisibility(View.GONE);
        lineChartPower.setVisibility(View.GONE);
        lineChartPhase.setVisibility(View.GONE);
        phaseLabel.setVisibility(View.GONE);
        currentLabel.setVisibility(View.GONE);
        lineChartCurrent.setVisibility(View.GONE);
        voltageLabel.setVisibility(View.GONE);
        powerLabel.setVisibility(View.GONE);
        drawFrequency();
        lineChartFrequency.setVisibility(View.VISIBLE);
        frequencyLabel.setVisibility(View.VISIBLE);
    }

    public void powerFactor(View view) {
        lineChartVoltage.setVisibility(View.GONE);
        lineChartPhase.setVisibility(View.GONE);
        lineChartFrequency.setVisibility(View.GONE);
        frequencyLabel.setVisibility(View.GONE);
        phaseLabel.setVisibility(View.GONE);
        currentLabel.setVisibility(View.GONE);
        lineChartCurrent.setVisibility(View.GONE);
        voltageLabel.setVisibility(View.GONE);
        drawPower();
        lineChartPower.setVisibility(View.VISIBLE);
        powerLabel.setVisibility(View.VISIBLE);
    }

    public void voltage(View view) {
        lineChartPhase.setVisibility(View.GONE);
        lineChartFrequency.setVisibility(View.GONE);
        lineChartPower.setVisibility(View.GONE);
        powerLabel.setVisibility(View.GONE);
        frequencyLabel.setVisibility(View.GONE);
        phaseLabel.setVisibility(View.GONE);
        currentLabel.setVisibility(View.GONE);
        lineChartCurrent.setVisibility(View.GONE);
        drawVoltage();
        lineChartVoltage.setVisibility(View.VISIBLE);
        voltageLabel.setVisibility(View.VISIBLE);
    }

    public void drawPower() {
        powerLabel.setTextColor(getResources().getColor(R.color.power));
        LineDataSet frequencyDataSet;
        if (Data.getPower() == null) {
            frequencyDataSet = new LineDataSet(Data.getPowerPoints(), "");
        } else {
            frequencyDataSet = new LineDataSet(Data.getPower(), "");
        }
        frequencyDataSet.setColors(power, MainActivity.this);
        frequencyDataSet.setLineWidth(3);
        frequencyDataSet.setValueTextSize(12);
        powerDataSets.add(frequencyDataSet);
        LineData powerLineData = new LineData(powerDataSets);
        lineChartPower.setData(powerLineData);
        lineChartPower.invalidate();
    }

    public void drawPhase() {
        phaseLabel.setTextColor(getResources().getColor(R.color.phase));
        LineDataSet frequencyDataSet;
        if (Data.getPhase() == null) {
            frequencyDataSet = new LineDataSet(Data.getPhasePoints(), "");
        } else {
            frequencyDataSet = new LineDataSet(Data.getPhase(), "");
        }
        frequencyDataSet.setColors(phase, MainActivity.this);
        frequencyDataSet.setLineWidth(3);
        frequencyDataSet.setValueTextSize(12);
        phaseDataSets.add(frequencyDataSet);
        LineData phaseLineData = new LineData(phaseDataSets);
        lineChartPhase.setData(phaseLineData);
        lineChartPhase.invalidate();
    }

    public void drawFrequency() {
        frequencyLabel.setTextColor(getResources().getColor(R.color.frequency));
        LineDataSet frequencyDataSet;
        if (Data.getPhase() == null) {
            frequencyDataSet = new LineDataSet(Data.getFrequencyPoints(), "");
        } else {
            frequencyDataSet = new LineDataSet(Data.getFrequency(), "");
        }
        frequencyDataSet.setColors(frequency, MainActivity.this);
        frequencyDataSet.setLineWidth(3);
        frequencyDataSet.setValueTextSize(12);
        frequencyDataSets.add(frequencyDataSet);
        LineData frequencyLineData = new LineData(frequencyDataSets);
        lineChartFrequency.setData(frequencyLineData);
        lineChartFrequency.invalidate();
    }

    public void drawCurrent() {
        currentLabel.setTextColor(getResources().getColor(R.color.current));
        LineDataSet frequencyDataSet;
        if (Data.getPhase() == null) {
            frequencyDataSet = new LineDataSet(Data.getCurrentPoints(), "");
        } else {
            frequencyDataSet = new LineDataSet(Data.getCurrent(), "");
        }
        frequencyDataSet.setColors(current, MainActivity.this);
        frequencyDataSet.setLineWidth(3);
        frequencyDataSet.setValueTextSize(12);
        currentDataSets.add(frequencyDataSet);
        LineData currentLineData = new LineData(currentDataSets);
        lineChartCurrent.setData(currentLineData);
        lineChartCurrent.invalidate();
    }

    public void drawVoltage() {
        voltageLabel.setTextColor(getResources().getColor(R.color.voltage));
        LineDataSet frequencyDataSet;
        if (Data.getPhase() == null) {
            frequencyDataSet = new LineDataSet(Data.getVoltagePoints(), "");
        } else {
            frequencyDataSet = new LineDataSet(Data.getVoltage(), "");
        }
        frequencyDataSet.setColors(voltage, MainActivity.this);
        frequencyDataSet.setLineWidth(3);
        frequencyDataSet.setValueTextSize(12);
        voltageDataSets.add(frequencyDataSet);
        LineData voltageLineData = new LineData(voltageDataSets);
        lineChartVoltage.setData(voltageLineData);
        lineChartVoltage.invalidate();
    }
}
