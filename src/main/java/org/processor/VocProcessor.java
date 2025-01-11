package org.processor;

import org.job.reader.OriginVOC;
import org.springframework.batch.item.ItemProcessor;

import static org.processor.VocProcessHelper.*;

public class VocProcessor implements ItemProcessor<OriginVOC, ProcessedDataWrapper> {

    @Override
    public ProcessedDataWrapper process(OriginVOC originVOC) throws Exception {
        ProcessedDataWrapper dataWrapper = new ProcessedDataWrapper();

        processVoc(originVOC, dataWrapper);
        processKrccVoc(originVOC, dataWrapper);
        processVocProcess(originVOC, dataWrapper);

        processVocResultNotiTypeRelation(originVOC, dataWrapper);
        processVocProgressNotiTypeRelation(originVOC, dataWrapper);
        processAnswer(originVOC, dataWrapper);

        return null;
    }


}
