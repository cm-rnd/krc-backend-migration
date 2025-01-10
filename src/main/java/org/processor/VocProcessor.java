package org.processor;

import org.reader.OriginVOC;
import org.springframework.batch.item.ItemProcessor;

public class VocProcessor implements ItemProcessor<OriginVOC, ProcessedDataWrapper> {

    @Override
    public ProcessedDataWrapper process(OriginVOC item) throws Exception {
        return null;
    }
}
