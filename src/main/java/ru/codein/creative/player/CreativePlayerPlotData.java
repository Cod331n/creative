package ru.codein.creative.player;

import com.intellectualcrafters.plot.object.PlotId;
import lombok.Value;
import ru.codein.creative.criteria.PlotCriteria;

import java.util.List;

@Value
public class CreativePlayerPlotData {
    String uuid;
    String worldName;
    PlotId plotId;
    String formattedDate;
    List<PlotCriteria> plotCriteriaList;

    public void addCriteria(PlotCriteria plotCriteria) {
        plotCriteriaList.add(plotCriteria);
    }
}
