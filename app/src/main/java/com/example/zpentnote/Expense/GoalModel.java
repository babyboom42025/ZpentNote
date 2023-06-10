package com.example.zpentnote.Expense;

import java.io.Serializable;

public class GoalModel implements Serializable {

    private Long type1,type2,type3,type4,type5,type6,type7,type8,type9,type10,type11,type12,type13,type14;
    private String gid;
    private String uid;
    public GoalModel(Long type1, Long type2, Long type3, Long type4, Long type5, Long type6, Long type7, Long type8, Long type9,Long type10,Long type11,Long type12,Long type13,Long type14, String uid,String gid) {
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.type4 = type4;
        this.type5 = type5;
        this.type6 = type6;
        this.type7 = type7;
        this.type8 = type8;
        this.type9 = type9;
        this.type10 = type10;
        this.type11 = type11;
        this.type12 = type12;
        this.type13 = type13;
        this.type14 = type14;
        this.gid = gid;
        this.uid = uid;
    }

    public Long getType10() {
        return type10;
    }

    public void setType10(Long type10) {
        this.type10 = type10;
    }

    public Long getType11() {
        return type11;
    }

    public void setType11(Long type11) {
        this.type11 = type11;
    }

    public Long getType12() {
        return type12;
    }

    public void setType12(Long type12) {
        this.type12 = type12;
    }

    public Long getType13() {
        return type13;
    }

    public void setType13(Long type13) {
        this.type13 = type13;
    }

    public Long getType14() {
        return type14;
    }

    public void setType14(Long type14) {
        this.type14 = type14;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Long getType1() {
        return type1;
    }

    public void setType1(Long type1) {
        this.type1 = type1;
    }

    public Long getType2() {
        return type2;
    }

    public void setType2(Long type2) {
        this.type2 = type2;
    }

    public Long getType3() {
        return type3;
    }

    public void setType3(Long type3) {
        this.type3 = type3;
    }

    public Long getType4() {
        return type4;
    }

    public void setType4(Long type4) {
        this.type4 = type4;
    }

    public Long getType5() {
        return type5;
    }

    public void setType5(Long type5) {
        this.type5 = type5;
    }

    public Long getType6() {
        return type6;
    }

    public void setType6(Long type6) {
        this.type6 = type6;
    }

    public Long getType7() {
        return type7;
    }

    public void setType7(Long type7) {
        this.type7 = type7;
    }

    public Long getType8() {
        return type8;
    }

    public void setType8(Long type8) {
        this.type8 = type8;
    }

    public Long getType9() {
        return type9;
    }

    public void setType9(Long type9) {
        this.type9 = type9;
    }
}
