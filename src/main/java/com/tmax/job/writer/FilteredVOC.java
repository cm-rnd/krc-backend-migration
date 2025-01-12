package com.tmax.job.writer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilteredVOC {


    @Id
    @GeneratedValue
    private Long id;

    private String vocDvn;           // VOC_DVN
    private String receNo;           // RECE_NO
    private String dealStat;         // DEAL_STAT
    private String dealDtlStat;      // DEAL_DTL_STAT
    private String cstNo;            // CST_NO
    private String cstNm;            // CST_NM
    private String cstRcgnNo;        // CST_RCGN_NO
    private String email;            // EMAIL
    private String hp;               // HP
    private String telNo;            // TEL_NO
    private String faxNo;            // FAX_NO
    private String postNo;           // POST_NO
    private String addrBase;         // ADDR_BASE
    private String addrDtl;          // ADDR_DTL
    private String openYn;           // OPEN_YN
    private String vocTit;           // VOC_TIT
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String vocCntn;          // VOC_CNTN
    @Lob
    @Column(columnDefinition = "TEXT")
    private String vocNotes;         // VOC_NOTES
    @Lob
    @Column(columnDefinition = "TEXT")
    private String imprDire;         // IMPR_DIRE
    @Lob
    @Column(columnDefinition = "TEXT")
    private String hopeEfct;         // HOPE_EFCT
    private String persCnt;         // PERS_CNT
    private String infoPuseAgrYn;    // INFO_PUSE_AGR_YN
    private String infoPuseAgrDd;    // INFO_PUSE_AGR_DD
    private String receChnl;         // RECE_CHNL
    private String receDvn;          // RECE_DVN
    private String receYmd;          // RECE_YMD
    private String receUser;         // RECE_USER
    private String receUserNm;       // RECE_USER_NM
    private String receDepCd;        // RECE_DEP_CD
    private String receDepNm;        // RECE_DEP_NM
    private String vocType;          // VOC_TYPE
    private String vocFld;           // VOC_FLD
    private String vocKd;            // VOC_KD
    private String dealDday;         // DEAL_DDAY
    private String asgnVocYn;        // ASGN_VOC_YN
    private String getMn;            // GET_MN
    @Lob
    @Column(columnDefinition = "TEXT")
    private String transMtr;         // TRANS_MTR
    private String dealDepCd;        // DEAL_DEP_CD
    private String dealDepNm;        // DEAL_DEP_NM
    private String dealDepSubCd;     // DEAL_DEP_SUB_CD
    private String dealDepSubNm;     // DEAL_DEP_SUB_NM
    private String dealYmd;          // DEAL_YMD
    private String dealUser;         // DEAL_USER
    private String answNotes;        // ANSW_NOTES
    @Lob
    @Column(columnDefinition = "TEXT")
    private String answCntn;         // ANSW_CNTN
    private String docNo;            // DOC_NO
    private String solvDvn;          // SOLV_DVN
    private String rprtMeth;         // RPRT_METH
    private String dealType;         // DEAL_TYPE
    private String relapYn;          // RELAP_YN
    private String sameVocYn;        // SAME_VOC_YN
    private String dealDtCnt;       // DEAL_DTCNT
    private String dealApvUser;      // DEAL_APV_USER
    private String vocApvUser;       // VOC_APV_USER
    private String cancelYn;         // CANCEL_YN
    private String cancelResn;       // CANCEL_RESN
    private String cancelDttm;       // CANCEL_DTTM
    private String epoDvn;           // EPO_DVN
    private String delYn;            // DEL_YN
    private String regUser;          // REG_USER
    private String regDd;            // REG_DD
    private String updtUser;         // UPDT_USER
    private String updtDd;           // UPDT_DD
    private String dealDepUser;      // DEAL_DEP_USER
    private String dealDepbNm;       // DEAL_DEPB_NM
    private String dealDepcNm;       // DEAL_DEPC_NM

}
