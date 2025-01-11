package com.tmax.job.processor.classifier;

import com.tmax.job.writer.FilteredVOC;
import com.tmax.job.processor.ProcessedDataWrapper;
import org.springframework.batch.item.ItemProcessor;

/* 민원인 취하 상태 */
public class VocReporterWithdrawClassifier implements ItemProcessor<FilteredVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(FilteredVOC item) throws Exception {
        return null;
    }
}
