package org.job.processor.classifier;

import org.job.processor.ProcessedDataWrapper;
import org.job.writer.FilteredVOC;
import org.springframework.batch.item.ItemProcessor;

/* 배정중 상태 */
public class VocManagerAssignClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(FilteredVOC item) throws Exception {
        return null;
    }
}
