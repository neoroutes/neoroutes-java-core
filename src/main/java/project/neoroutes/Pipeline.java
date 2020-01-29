package project.neoroutes;

import java.util.ArrayList;
import java.util.List;

public class Pipeline<I, O> {
    private List<Step<I, O>> steps = new ArrayList<>();

    public void addStep(Step<I, O> step){
        steps.add(step);
    }

    public void removeStep(Step<I, O> step){
        steps.remove(step);
    }

    public void run(I in, O out){
        for (Step<I, O> step : steps) {
            if(!step.process(in, out))
                return;
        }
    }

    public interface Step<I, O> {
        boolean process(I in, O out);
    }
}
