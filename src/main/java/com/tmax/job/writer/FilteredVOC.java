package com.tmax.job.writer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "filteredvoc") // 테이블 이름
public class FilteredVOC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voc_dvn")
    private String vocDvn;

    @Column(name = "rece_no")
    private String receNo;

    @Column(name = "deal_stat")
    private String dealStat;

    @Column(name = "deal_dtl_stat")
    private String dealDtlStat;

    @Column(name = "cst_no")
    private String cstNo;

    @Column(name = "cst_nm")
    private String cstNm;

    @Column(name = "cst_rcgn_no")
    private String cstRcgnNo;

    @Column(name = "email")
    private String email;

    @Column(name = "hp")
    private String hp;

    @Column(name = "tel_no")
    private String telNo;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "post_no")
    private String postNo;

    @Column(name = "addr_base")
    private String addrBase;

    @Column(name = "addr_dtl")
    private String addrDtl;

    @Column(name = "open_yn")
    private String openYn;

    @Column(name = "voc_tit")
    private String vocTit;

    @Lob
    @Column(name = "voc_cntn", columnDefinition = "LONGTEXT")
    private String vocCntn;

    @Lob
    @Column(name = "voc_notes", columnDefinition = "TEXT")
    private String vocNotes;

    @Lob
    @Column(name = "impr_dire", columnDefinition = "TEXT")
    private String imprDire;

    @Lob
    @Column(name = "hope_efct", columnDefinition = "TEXT")
    private String hopeEfct;

    @Column(name = "pers_cnt")
    private String persCnt;

    @Column(name = "info_puse_agr_yn")
    private String infoPuseAgrYn;

    @Column(name = "info_puse_agr_dd")
    private String infoPuseAgrDd;

    @Column(name = "rece_chnl")
    private String receChnl;

    @Column(name = "rece_dvn")
    private String receDvn;

    @Column(name = "rece_ymd")
    private String receYmd;

    @Column(name = "rece_user")
    private String receUser;

    @Column(name = "rece_user_nm")
    private String receUserNm;

    @Column(name = "rece_dep_cd")
    private String receDepCd;

    @Column(name = "rece_dep_nm")
    private String receDepNm;

    @Column(name = "voc_type")
    private String vocType;

    @Column(name = "voc_fld")
    private String vocFld;

    @Column(name = "voc_kd")
    private String vocKd;

    @Column(name = "deal_dday")
    private String dealDday;

    @Column(name = "asgn_voc_yn")
    private String asgnVocYn;

    @Column(name = "get_mn")
    private String getMn;

    @Lob
    @Column(name = "trans_mtr", columnDefinition = "TEXT")
    private String transMtr;

    @Column(name = "deal_dep_cd")
    private String dealDepCd;

    @Column(name = "deal_dep_nm")
    private String dealDepNm;

    @Column(name = "deal_dep_sub_cd")
    private String dealDepSubCd;

    @Column(name = "deal_dep_sub_nm")
    private String dealDepSubNm;

    @Column(name = "deal_ymd")
    private String dealYmd;

    @Column(name = "deal_user")
    private String dealUser;

    @Column(name = "answ_notes")
    private String answNotes;

    @Lob
    @Column(name = "answ_cntn", columnDefinition = "TEXT")
    private String answCntn;

    @Column(name = "doc_no")
    private String docNo;

    @Column(name = "solv_dvn")
    private String solvDvn;

    @Column(name = "rprt_meth")
    private String rprtMeth;

    @Column(name = "deal_type")
    private String dealType;

    @Column(name = "relap_yn")
    private String relapYn;

    @Column(name = "same_voc_yn")
    private String sameVocYn;

    @Column(name = "deal_dt_cnt")
    private String dealDtCnt;

    @Column(name = "deal_apv_user")
    private String dealApvUser;

    @Column(name = "voc_apv_user")
    private String vocApvUser;

    @Column(name = "cancel_yn")
    private String cancelYn;

    @Column(name = "cancel_resn")
    private String cancelResn;

    @Column(name = "cancel_dttm")
    private String cancelDttm;

    @Column(name = "epo_dvn")
    private String epoDvn;

    @Column(name = "del_yn")
    private String delYn;

    @Column(name = "reg_user")
    private String regUser;

    @Column(name = "reg_dd")
    private String regDd;

    @Column(name = "updt_user")
    private String updtUser;

    @Column(name = "updt_dd")
    private String updtDd;

    @Column(name = "deal_dep_user")
    private String dealDepUser;

    @Column(name = "deal_depb_nm")
    private String dealDepbNm;

    @Column(name = "deal_depc_nm")
    private String dealDepcNm;

}
