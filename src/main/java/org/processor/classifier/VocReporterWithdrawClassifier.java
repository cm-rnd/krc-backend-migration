package org.processor.classifier;

import org.processor.ProcessedDataWrapper;
import org.reader.OriginVOC;
import org.springframework.batch.item.ItemProcessor;

/* 민원인 취하 상태 */
public class VocReporterWithdrawClassifier implements ItemProcessor<OriginVOC, ProcessedDataWrapper> {
    @Override
    public ProcessedDataWrapper process(OriginVOC item) throws Exception {
        return null;
    }
}
