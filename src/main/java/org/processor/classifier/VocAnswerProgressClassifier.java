package org.processor.classifier;

import org.processor.ProcessedDataWrapper;
import org.job.reader.OriginVOC;
import org.springframework.batch.item.ItemProcessor;


/* 답변중 상태 */
public class VocAnswerProgressClassifier implements ItemProcessor<OriginVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(OriginVOC item) throws Exception {
        return null;
    }
}
