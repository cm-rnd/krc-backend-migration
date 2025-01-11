package com.tmax.job.processor.classifier;

import com.tmax.job.writer.FilteredVOC;
import com.tmax.job.processor.ProcessedDataWrapper;
import org.springframework.batch.item.ItemProcessor;

/* 접수중 상태 */
public class VocAcceptProgressClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {

    @Override
    public ProcessedDataWrapper process(FilteredVOC originVOC) throws Exception {
        return null;
    }
}
