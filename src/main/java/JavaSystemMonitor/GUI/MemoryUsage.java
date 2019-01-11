package JavaSystemMonitor.GUI;

import JavaSystemMonitor.Constants;
import com.sun.management.OperatingSystemMXBean;
import java.awt.BorderLayout;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;

public class MemoryUsage extends javax.swing.JPanel
{

    private static final String FREE_MEMORY = "Free Memory";

    private static final String USED_MEMORY = "Used Memory";

    private final PieChart memoryPieChart;

    public MemoryUsage()
    {
        initComponents();

        memoryPieChart = new PieChart(500, 500);

        memoryPieChart.getStyler().setAnnotationType(PieStyler.AnnotationType.Percentage);

        memoryPieChart.addSeries(FREE_MEMORY, 0);
        memoryPieChart.addSeries(USED_MEMORY, 0);

        setLayout(new BorderLayout());

        final XChartPanel chartPanel = new XChartPanel(memoryPieChart);

        add(chartPanel, BorderLayout.CENTER);

        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                final OperatingSystemMXBean mbean
                        = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

                final double totalMemory = mbean.getTotalPhysicalMemorySize() / Constants.BYTES_TO_GIGABYTES;

                final double freeMemory = mbean.getFreePhysicalMemorySize() / Constants.BYTES_TO_GIGABYTES;

                final double usedMemory = totalMemory - freeMemory;

                memoryPieChart.updatePieSeries(USED_MEMORY, usedMemory);
                memoryPieChart.updatePieSeries(FREE_MEMORY, freeMemory);

                repaint();
            }

        }, 0, Constants.UPDATE_RATE_MS);

        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
