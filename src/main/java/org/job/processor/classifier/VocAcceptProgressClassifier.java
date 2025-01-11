package org.job.processor.classifier;

import org.job.processor.ProcessedDataWrapper;
import org.job.writer.FilteredVOC;
import org.springframework.batch.item.ItemProcessor;

/* 접수중 상태 */
public class VocAcceptProgressClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {

    @Override
    public ProcessedDataWrapper process(FilteredVOC originVOC) throws Exception {
        return null;
    }
}
