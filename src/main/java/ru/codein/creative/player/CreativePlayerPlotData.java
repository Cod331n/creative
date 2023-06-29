package ru.codein.creative.player;

import com.intellectualcrafters.plot.object.PlotId;
import lombok.Value;
import ru.codein.creative.criteria.PlotCriteria;

import java.util.List;

@Value
public class CreativePlayerPlotData {
    String uuid;
    PlotId plotId;
    String formattedDate;
    List<PlotCriteria> plotCriteriaList;
}
