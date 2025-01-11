package org.job.processor.classifier;

import org.job.processor.ProcessedDataWrapper;
import org.job.writer.FilteredVOC;
import org.springframework.batch.item.ItemProcessor;

/* 민원인 취하 상태 */
public class VocReporterWithdrawClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(FilteredVOC item) throws Exception {
        return null;
    }
}
