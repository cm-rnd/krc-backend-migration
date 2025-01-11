package org.job.processor.classifier;

import org.job.processor.ProcessedDataWrapper;
import org.springframework.batch.item.ItemProcessor;

/* 답변완료 상태 */
public class VocAnswerCompletedClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(FilteredVOC item) throws Exception {
        return null;
    }
}
