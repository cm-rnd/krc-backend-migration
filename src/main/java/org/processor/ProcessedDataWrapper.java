package org.processor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.domain.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedDataWrapper {
    private Voc voc;
    private Answer answer;
    private VocKrccAntiCorruption vocKrccAntiCorruption;
    private VocKrccGeneral vocKrccGeneral;
    private VocProcess vocProcess;
    private VocProgressNotiTypeRelation vocProgressNotiTypeRelation;
    private VocResultNotiTypeRelation vocResultNotiTypeRelation;
}
