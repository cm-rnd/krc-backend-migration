package org.processor.classifier;

import org.processor.ProcessedDataWrapper;
import org.springframework.batch.item.ItemProcessor;

/* 민원인 취하 상태 */
public class VocReporterWithdrawClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(FilteredVOC item) throws Exception {
        return null;
    }
}
