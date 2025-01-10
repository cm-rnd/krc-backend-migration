package org.processor.classifier;

import org.processor.ProcessedDataWrapper;
import org.reader.OriginVOC;
import org.springframework.batch.item.ItemProcessor;

/* 배정중 상태 */
public class VocManagerAssignClassifier implements ItemProcessor<OriginVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(OriginVOC item) throws Exception {
        return null;
    }
}
