package com.tmax.job.processor.classifier;

import com.tmax.job.writer.FilteredVOC;
import com.tmax.job.processor.ProcessedDataWrapper;
import org.springframework.batch.item.ItemProcessor;


/* 답변중 상태 */
public class VocAnswerProgressClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(FilteredVOC item) throws Exception {
        return null;
    }
}
