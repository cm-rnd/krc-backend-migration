package org.reader;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class OriginVOCFieldSetMapper implements FieldSetMapper<OriginVOC> {
    @Override
    public OriginVOC mapFieldSet(FieldSet fieldSet) throws BindException {

        if(fieldSet == null) {
            return null;
        }

        OriginVOC vo = new OriginVOC();

        vo.setVocDvn(fieldSet.readString("VOC_DVN"));
        vo.setReceNo(fieldSet.readString("RECE_NO"));
        vo.setDealStat(fieldSet.readString("DEAL_STAT"));
        vo.setDealDtlStat(fieldSet.readString("DEAL_DTL_STAT"));
        vo.setCstNo(fieldSet.readString("CST_NO"));
        vo.setCstNm(fieldSet.readString("CST_NM"));
        vo.setCstRcgnNo(fieldSet.readString("CST_RCGN_NO"));
        vo.setEmail(fieldSet.readString("EMAIL"));
        vo.setHp(fieldSet.readString("HP"));
        vo.setTelNo(fieldSet.readString("TEL_NO"));
        vo.setFaxNo(fieldSet.readString("FAX_NO"));
        vo.setPostNo(fieldSet.readString("POST_NO"));
        vo.setAddrBase(fieldSet.readString("ADDR_BASE"));
        vo.setAddrDtl(fieldSet.readString("ADDR_DTL"));
        vo.setOpenYn(fieldSet.readString("OPEN_YN"));
        vo.setVocTit(fieldSet.readString("VOC_TIT"));
        vo.setVocCntn(fieldSet.readString("VOC_CNTN"));
        vo.setVocNotes(fieldSet.readString("VOC_NOTES"));
        vo.setImprDire(fieldSet.readString("IMPR_DIRE"));
        vo.setHopeEfct(fieldSet.readString("HOPE_EFCT"));
        vo.setPersCnt(fieldSet.readInt("PERS_CNT"));
        vo.setInfoPuseAgrYn(fieldSet.readString("INFO_PUSE_AGR_YN"));
        vo.setInfoPuseAgrDd(fieldSet.readString("INFO_PUSE_AGR_DD"));
        vo.setReceChnl(fieldSet.readString("RECE_CHNL"));
        vo.setReceDvn(fieldSet.readString("RECE_DVN"));
        vo.setReceYmd(fieldSet.readString("RECE_YMD"));
        vo.setReceUser(fieldSet.readString("RECE_USER"));
        vo.setReceUserNm(fieldSet.readString("RECE_USER_NM"));
        vo.setReceDepCd(fieldSet.readString("RECE_DEP_CD"));
        vo.setReceDepNm(fieldSet.readString("RECE_DEP_NM"));
        vo.setVocType(fieldSet.readString("VOC_TYPE"));
        vo.setVocFld(fieldSet.readString("VOC_FLD"));
        vo.setVocKd(fieldSet.readString("VOC_KD"));
        vo.setDealDday(fieldSet.readString("DEAL_DDAY"));
        vo.setAsgnVocYn(fieldSet.readString("ASGN_VOC_YN"));
        vo.setGetMn(fieldSet.readString("GET_MN"));
        vo.setTransMtr(fieldSet.readString("TRANS_MTR"));
        vo.setDealDepCd(fieldSet.readString("DEAL_DEP_CD"));
        vo.setDealDepNm(fieldSet.readString("DEAL_DEP_NM"));
        vo.setDealDepSubCd(fieldSet.readString("DEAL_DEP_SUB_CD"));
        vo.setDealDepSubNm(fieldSet.readString("DEAL_DEP_SUB_NM"));
        vo.setDealYmd(fieldSet.readString("DEAL_YMD"));
        vo.setDealUser(fieldSet.readString("DEAL_USER"));
        vo.setAnswNotes(fieldSet.readString("ANSW_NOTES"));
        vo.setAnswCntn(fieldSet.readString("ANSW_CNTN"));
        vo.setDocNo(fieldSet.readString("DOC_NO"));
        vo.setSolvDvn(fieldSet.readString("SOLV_DVN"));
        vo.setRprtMeth(fieldSet.readString("RPRT_METH"));
        vo.setDealType(fieldSet.readString("DEAL_TYPE"));
        vo.setRelapYn(fieldSet.readString("RELAP_YN"));
        vo.setSameVocYn(fieldSet.readString("SAME_VOC_YN"));
        vo.setDealDtCnt(fieldSet.readInt("DEAL_DTCNT"));
        vo.setDealApvUser(fieldSet.readString("DEAL_APV_USER"));
        vo.setVocApvUser(fieldSet.readString("VOC_APV_USER"));
        vo.setCancelYn(fieldSet.readString("CANCEL_YN"));
        vo.setCancelResn(fieldSet.readString("CANCEL_RESN"));
        vo.setCancelDttm(fieldSet.readString("CANCEL_DTTM"));
        vo.setEpoDvn(fieldSet.readString("EPO_DVN"));
        vo.setDelYn(fieldSet.readString("DEL_YN"));
        vo.setRegUser(fieldSet.readString("REG_USER"));
        vo.setRegDd(fieldSet.readString("REG_DD"));
        vo.setUpdtUser(fieldSet.readString("UPDT_USER"));
        vo.setUpdtDd(fieldSet.readString("UPDT_DD"));
        vo.setDealDepUser(fieldSet.readString("DEAL_DEP_USER"));
        vo.setDealDepbNm(fieldSet.readString("DEAL_DEPB_NM"));
        vo.setDealDepcNm(fieldSet.readString("DEAL_DEPC_NM"));

        return vo;
    }
}
