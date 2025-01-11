package org.processor.classifier;

import org.processor.ProcessedDataWrapper;
import org.springframework.batch.item.ItemProcessor;

/* 접수중 상태 */
public class VocAcceptProgressClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {

    @Override
    public ProcessedDataWrapper process(FilteredVOC originVOC) throws Exception {
        return null;
    }
}
