package org.processor.classifier;

import org.processor.ProcessedDataWrapper;
import org.reader.OriginVOC;
import org.springframework.batch.item.ItemProcessor;

/* 접수중 상태 */
public class VocAcceptProgressClassifier implements ItemProcessor<OriginVOC, ProcessedDataWrapper> {

    @Override
    public ProcessedDataWrapper process(OriginVOC originVOC) throws Exception {
        return null;
    }
}
